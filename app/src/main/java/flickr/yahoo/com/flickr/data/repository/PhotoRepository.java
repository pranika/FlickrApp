package flickr.yahoo.com.flickr.data.repository;

import android.annotation.SuppressLint;

import java.util.List;

import flickr.yahoo.com.flickr.data.datasource.PhotoContract;

import flickr.yahoo.com.flickr.data.datastore.PhotoDataStore;
import flickr.yahoo.com.flickr.data.model.Photo;
import flickr.yahoo.com.flickr.data.model.PhotoResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class PhotoRepository implements PhotoRepositoryContract {
    PhotoContract photoContract;
    PhotoDataStore photoDataStore;

    private boolean hasNext = true;
    private boolean firstRun = true;
    private int currentPage = 0;

    public PhotoRepository(PhotoDataStore photoDataStore, PhotoContract photoContract) {
        this.photoContract = photoContract;
        this.photoDataStore = photoDataStore;
    }

    public boolean hasNext() {
        return hasNext;
    }

    @SuppressLint("CheckResult")
    public Observable<List<Photo>> getNext() {
        return Observable.create((ObservableEmitter<List<Photo>> emitter) -> {
            if (!hasNext) {
                emitter.onError(new Exception("Photo Repository Does Not Have More Data"));
                return;
            }

            if (firstRun) {
                firstRun = false;
                List cachedPhotos = photoDataStore.retrievePhotos();
                if (cachedPhotos.size() > 0) emitter.onNext(cachedPhotos);
            }

            photoContract.getRecentPhotos(currentPage + 1).subscribe(new SingleObserver<PhotoResponse>(){
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(PhotoResponse photosResponse) {
                    photoDataStore.savePhotos(photosResponse.photosPage.photos);
                    Integer totalPages = Integer.parseInt(photosResponse.photosPage.totalPages);
                    currentPage = photosResponse.photosPage.currentPage;
                    hasNext = currentPage < totalPages;
                    emitter.onNext(photosResponse.photosPage.photos);
                    emitter.onComplete();

                }

                @Override
                public void onError(Throwable e) {
                    emitter.onError(e);
                }

            });


        });

    }


}
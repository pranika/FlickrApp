package flickr.yahoo.com.flickr.views.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import flickr.yahoo.com.flickr.FlickrApplication;
import flickr.yahoo.com.flickr.data.model.Photo;
import flickr.yahoo.com.flickr.data.repository.PhotoRepositoryContract;
import flickr.yahoo.com.flickr.di.components.DaggerAppComponent;
import flickr.yahoo.com.flickr.disposeLeaks.DisposableManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhotoViewModel extends AndroidViewModel {

    @Inject
    PhotoRepositoryContract photoRepository;

    private MutableLiveData<List<Photo>> allPhotos;
    private MutableLiveData<Boolean> isLoading ;
    private MutableLiveData<Boolean> hasMoreData ;

    public PhotoViewModel(Application application) {
        super(application);
        ((FlickrApplication) application).mDaggerComponent.inject(this);
        allPhotos = new MutableLiveData<>();
        allPhotos.setValue(new ArrayList<>());
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
        hasMoreData = new MutableLiveData<>();
        hasMoreData.setValue(false);
    }

    public LiveData<Boolean> isLoading(){
        return isLoading;
    }

    public LiveData<Boolean> hasMoreDataValues(){
        return hasMoreData;
    }


    public LiveData<List<Photo>> getAllPhotos() {
        return allPhotos;
    }


    int onNextCount = 0;

    public void loadMorePhotos() {
        if(!photoRepository.hasNext()) {
            return;
        }

        isLoading.setValue(true);

        photoRepository.getNext()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable s) {
                        DisposableManager.add(s);
                        onNextCount = 0;
                    }

                    @Override
                    public void onNext(List<Photo> photoList) {
                        List<Photo> newPhotoList = allPhotos.getValue();
                        if (++onNextCount == 1) newPhotoList.addAll(photoList);
                        else newPhotoList = photoList;
                        allPhotos.setValue(newPhotoList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        isLoading.setValue(false);
                        hasMoreData.setValue(photoRepository.hasNext());
                    }
                });
    }



}



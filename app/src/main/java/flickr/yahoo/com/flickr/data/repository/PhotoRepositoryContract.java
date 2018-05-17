package flickr.yahoo.com.flickr.data.repository;

import java.util.List;

import flickr.yahoo.com.flickr.data.model.Photo;
import io.reactivex.Observable;

public interface PhotoRepositoryContract {

    boolean hasNext();
    Observable<List<Photo>> getNext();
}

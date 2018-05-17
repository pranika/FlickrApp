
// Contract to persist data
package flickr.yahoo.com.flickr.data.datastore;

import java.util.List;

import flickr.yahoo.com.flickr.data.model.Photo;

public interface PhotoDataStore {

    boolean savePhotos(List<Photo> photos);
    List<Photo> retrievePhotos();

}

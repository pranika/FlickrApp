//Data Contract to fetch data over Network.
package flickr.yahoo.com.flickr.data.datasource;

import flickr.yahoo.com.flickr.data.model.PhotoResponse;
import io.reactivex.Single;

public interface PhotoContract {


    Single<PhotoResponse> getRecentPhotos(Integer pageNo);

}

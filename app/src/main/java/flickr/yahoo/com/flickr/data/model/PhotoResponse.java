
package flickr.yahoo.com.flickr.data.model;

import com.squareup.moshi.Json;

public class PhotoResponse {

    @Json(name = "photos")
    public PhotosPage photosPage;
    @Json(name = "stat")
    public String stat;

}

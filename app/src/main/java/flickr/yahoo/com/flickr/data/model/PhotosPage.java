
package flickr.yahoo.com.flickr.data.model;

import java.util.List;
import com.squareup.moshi.Json;

public class PhotosPage {

    @Json(name = "page")
    public Integer currentPage;
    @Json(name = "pages")
    public String totalPages;
    @Json(name = "perpage")
    public Integer perPage;
    @Json(name = "total")
    public String totalPhotos;
    @Json(name = "photo")
    public List<Photo> photos = null;

}

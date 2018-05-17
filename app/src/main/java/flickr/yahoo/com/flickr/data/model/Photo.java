
package flickr.yahoo.com.flickr.data.model;

import com.squareup.moshi.Json;

public class Photo {

    public static final String PHOTO_THUMB = "_t";
    public static final String PHOTO_MEDIUM = "_m";

    @Json(name = "id")
    public String id;
    @Json(name = "owner")
    public String owner;
    @Json(name = "secret")
    public String secret;
    @Json(name = "server")
    public String server;
    @Json(name = "farm")
    public Integer farm;
    @Json(name = "title")
    public String title;
    @Json(name = "ispublic")
    public Integer ispublic;
    @Json(name = "isfriend")
    public Integer isfriend;
    @Json(name = "isfamily")
    public Integer isfamily;

    public String getPhotoUrl(String photoType) {
        StringBuilder sb = new StringBuilder();
        return sb.append("https://farm").append(farm.toString()).append(".staticflickr.com/")
        .append(server).append("/").append(id).append('_').append(secret).append(photoType).append(".jpg")
        .toString();
    }

}

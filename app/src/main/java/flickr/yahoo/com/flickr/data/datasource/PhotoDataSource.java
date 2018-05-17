// PhotoDataSource is used to fetch data over the network api.

package flickr.yahoo.com.flickr.data.datasource;


import android.net.Uri;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import flickr.yahoo.com.flickr.data.model.PhotoResponse;
import flickr.yahoo.com.flickr.data.model.Photo;
import io.reactivex.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class PhotoDataSource implements PhotoContract {

    OkHttpClient client;

    String apiKey;
    JsonAdapter<PhotoResponse> photoResponseAdapter;


    public PhotoDataSource(String apiKey, OkHttpClient client) {
        this.apiKey = apiKey;
        this.client = client;
        Moshi moshi = new Moshi.Builder().build();
        photoResponseAdapter = moshi.adapter(PhotoResponse.class);
    }

    @Override
    public Single<PhotoResponse> getRecentPhotos(Integer pageNo) {

        String url = Uri.parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("method", "flickr.photos.getRecent")
            .appendQueryParameter("api_key", apiKey)
            .appendQueryParameter("page", pageNo.toString())
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("per_page", "100")
            .build().toString();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        return Single.create(emitter -> {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("response", e.toString());
                    e.printStackTrace();
                    if(e != null) emitter.onError(e);
                }

                @Override
                public void onResponse(Call call, final okhttp3.Response response) {
                    try {
                        if (response != null && response.isSuccessful()) {
                            emitter.onSuccess(
                                    photoResponseAdapter.fromJson(response.body().string())
                            );
                        } else {
                            emitter.onError(new IllegalStateException("${response?.code()}"));
                        }
                    } catch(IOException e) {
                        emitter.onError(e);
                    }
                }
            });
        });
    }


}

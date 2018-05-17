//Sharepreferences is used to persist data.
// It is used as a cache to persist data where most recent posts loaded over  network are being persisted.


package flickr.yahoo.com.flickr.data.datastore;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import flickr.yahoo.com.flickr.data.model.Photo;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesDataStore implements PhotoDataStore {

    private static final String PHOTOS_KEY = "photos";
    private static final String PREFERENCE_NAME = "DataStore";

    Moshi moshi;
    Context context;

    JsonAdapter<List<Photo>> adapter;

    public SharedPreferencesDataStore(Moshi moshi,Context context) {
        this.moshi=moshi;
        this.context=context;
        Type type = Types.newParameterizedType(List.class, Photo.class);
        this.adapter = moshi.adapter(type);
    }

    @Override
    public boolean savePhotos(List<Photo> photos) {
        String photosJson = adapter.toJson(photos);
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PHOTOS_KEY, photosJson);
        return editor.commit();
    }

    @Override
    public List<Photo> retrievePhotos() {
        try {
            SharedPreferences pref = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
            String photosJson = pref.getString(PHOTOS_KEY, "[]");
            return adapter.fromJson(photosJson);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Photo>();
        }
    }
}

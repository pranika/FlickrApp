package flickr.yahoo.com.flickr.di.modules;



import android.content.Context;

import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import flickr.yahoo.com.flickr.data.datasource.PhotoContract;
import flickr.yahoo.com.flickr.data.datasource.PhotoDataSourceImpl;
import flickr.yahoo.com.flickr.data.datastore.PhotoDataStore;
import flickr.yahoo.com.flickr.data.datastore.PhotoDataStoreSharedPreferencesImpl;
import flickr.yahoo.com.flickr.data.repository.PhotoRepository;
import flickr.yahoo.com.flickr.data.repository.PhotoRepositoryContract;
import flickr.yahoo.com.flickr.di.scopes.ApplicationScope;
import okhttp3.OkHttpClient;

@Module
public class ServiceModule {

    @ApplicationScope
    @Provides

    public PhotoContract photoDataSourceManager(OkHttpClient client) {
        return new PhotoDataSourceImpl("7b85e389607020e3b5a12c5a40e260db", client);
    }

    @ApplicationScope
    @Provides

    public PhotoDataStore photoDataStoreManager(Moshi moshi,Context context) {
        return new PhotoDataStoreSharedPreferencesImpl(moshi, context);
    }

  @ApplicationScope
  @Provides

  public PhotoRepositoryContract photoRepository(PhotoDataStore photoDataStore, PhotoContract photoContract) {
        return new PhotoRepository(photoDataStore, photoContract);
    }

}
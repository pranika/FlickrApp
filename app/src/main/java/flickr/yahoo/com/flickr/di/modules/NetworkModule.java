package flickr.yahoo.com.flickr.di.modules;

import android.content.Context;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import flickr.yahoo.com.flickr.di.scopes.ApplicationScope;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import timber.log.Timber;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @ApplicationScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @ApplicationScope
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB Cache
    }

    @Provides
    @ApplicationScope
    public File cacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }


    @Provides
    @ApplicationScope
    public Moshi moshi() {
        return new Moshi.Builder().build();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient(Context context, HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new ChuckInterceptor(context))
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache)
                .build();
    }
    @Provides
    @ApplicationScope
    public Picasso picassoWithCache(OkHttpClient client,Context context){
        return new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
    }



}
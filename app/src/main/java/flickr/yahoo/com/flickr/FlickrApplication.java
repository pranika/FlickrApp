package flickr.yahoo.com.flickr;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import flickr.yahoo.com.flickr.di.components.AppComponent;
import flickr.yahoo.com.flickr.di.components.DaggerAppComponent;
import flickr.yahoo.com.flickr.di.modules.ContextModule;
import timber.log.Timber;

public class FlickrApplication  extends Application {

    public AppComponent mDaggerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        if(!LeakCanary.isInAnalyzerProcess(this)) {
            refWatcher = LeakCanary.install(this);
        }
        Stetho.initializeWithDefaults(this);

        mDaggerComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();



    }

    public static RefWatcher getRefWatcher(Context context) {
        FlickrApplication application = (FlickrApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;
}

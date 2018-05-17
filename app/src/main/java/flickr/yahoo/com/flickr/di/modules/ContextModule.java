package flickr.yahoo.com.flickr.di.modules;


import android.content.Context;



import dagger.Module;
import dagger.Provides;
import flickr.yahoo.com.flickr.di.scopes.ApplicationScope;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    public Context context() {
        return context;
    }
}
package flickr.yahoo.com.flickr.di.modules;

import android.content.Context;
import android.content.SharedPreferences;



import dagger.Module;
import dagger.Provides;
import flickr.yahoo.com.flickr.di.scopes.ApplicationScope;

/**
 * Created by hack on 31/10/17.
 */

@Module
public class SharedPreferencesModule {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Provides
    @ApplicationScope
    SharedPreferences getInstance(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFS_NAME ,Context.MODE_PRIVATE);
        return sharedPref;
    }
}

package flickr.yahoo.com.flickr.di.components;



import dagger.Component;
import flickr.yahoo.com.flickr.di.modules.NetworkModule;
import flickr.yahoo.com.flickr.di.modules.ServiceModule;
import flickr.yahoo.com.flickr.di.modules.SharedPreferencesModule;
import flickr.yahoo.com.flickr.di.scopes.ApplicationScope;
import flickr.yahoo.com.flickr.views.MainActivity;
import flickr.yahoo.com.flickr.views.PhotoFragment;
import flickr.yahoo.com.flickr.views.ViewModels.PhotoViewModel;


@ApplicationScope
@Component(modules = {
        NetworkModule.class,
        SharedPreferencesModule.class,
        ServiceModule.class
})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(PhotoFragment photoFragment);
    void inject(PhotoViewModel viewModel);



}
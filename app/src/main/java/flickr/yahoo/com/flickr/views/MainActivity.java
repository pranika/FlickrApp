// Start Activity which loads fragment
package flickr.yahoo.com.flickr.views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flickr.yahoo.com.flickr.FlickrApplication;
import flickr.yahoo.com.flickr.R;
import flickr.yahoo.com.flickr.data.model.Photo;
import flickr.yahoo.com.flickr.data.repository.PhotoRepository;
import flickr.yahoo.com.flickr.data.repository.PhotoRepositoryContract;
import flickr.yahoo.com.flickr.views.ViewModels.PhotoViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    PhotoViewModel photoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((FlickrApplication) getApplication()).mDaggerComponent.inject(this);
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        PhotoFragment photoFragment=new PhotoFragment();
        setFragment(photoFragment);
    }




    private void setFragment(Fragment mStateFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, mStateFragment).commit();
    }



}

// Start Activity which loads fragment
package flickr.yahoo.com.flickr.views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import flickr.yahoo.com.flickr.FlickrApplication;
import flickr.yahoo.com.flickr.R;

import flickr.yahoo.com.flickr.views.ViewModels.PhotoViewModel;

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

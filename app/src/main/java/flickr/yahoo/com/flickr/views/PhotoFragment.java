package flickr.yahoo.com.flickr.views;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import flickr.yahoo.com.flickr.FlickrApplication;
import flickr.yahoo.com.flickr.R;
import flickr.yahoo.com.flickr.data.model.Photo;
import flickr.yahoo.com.flickr.data.repository.PhotoRepositoryContract;
import flickr.yahoo.com.flickr.disposeLeaks.DisposableManager;
import flickr.yahoo.com.flickr.views.ViewModels.PhotoViewModel;


public class PhotoFragment extends Fragment  {
    @Inject
    PhotoRepositoryContract photoRepository;
    Unbinder unbinder;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Inject
    Picasso picasso;


    PhotoViewModel photoViewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    PhotoAdapter photoAdapter;



    public void bindButterKnife(View vi) {
        unbinder = ButterKnife.bind(this, vi);
    }

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FlickrApplication) getActivity().getApplication()).mDaggerComponent.inject(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_photo, container, false);
        bindButterKnife(rootview);
        networkError();

        photoViewModel = ViewModelProviders.of(getActivity()).get(PhotoViewModel.class);

            List<Photo> initialPhotos = photoViewModel.getAllPhotos().getValue();

            configurePhotosList(initialPhotos);
            bindObservers();

            if (initialPhotos.size() == 0)
                loadMorePhotos();

            photoAdapter.setOnTouchEventListner(new PhotoAdapter.OnTouchEventListner() {
                @Override
                public void onTouchEvent() {

                    networkError();

                    loadMorePhotos();

                }
            });


        return rootview;
    }

    void networkError(){

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected)
        {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Turn On Internet", Snackbar.LENGTH_LONG);

            snackbar.show();


        }

    }

    void setSwipeColourScheme(){

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);


    }
    private void configurePhotosList(List<Photo> photoList) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        photoAdapter = new PhotoAdapter(picasso,photoList);
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {

        unbinder.unbind();
        PhotoAdapter.OnTouchEventListner onTouchEventListner=photoAdapter.returnTouchListner();
        onTouchEventListner=null;
        DisposableManager.dispose();
        super.onDestroyView();

    }

    private void bindObservers() {
        photoViewModel.getAllPhotos().observe(this, photos -> {
            networkError();
            photoAdapter.updateImages(photos);
        });

        photoViewModel.isLoading().observe(this, isLoading -> {
            networkError();
            if (isLoading) {
                setSwipeColourScheme();
                swipeRefreshLayout.setRefreshing(true);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        photoViewModel.hasMoreDataValues().observe(this, hasMore -> {

            networkError();

            photoAdapter.setHasMoreData(hasMore);
        });

    }

    public void loadMorePhotos() {
        photoViewModel.loadMorePhotos();;
    }



}

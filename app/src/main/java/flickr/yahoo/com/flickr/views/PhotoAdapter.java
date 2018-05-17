// Adapter to bind pictures to fragment
package flickr.yahoo.com.flickr.views;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import flickr.yahoo.com.flickr.R;
import flickr.yahoo.com.flickr.data.model.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    List<Photo> photoList;
    OnTouchEventListner onTouchEventListner;
    Picasso picasso;
    boolean hasMoreData;

    public void setHasMoreData(boolean hasMore) {
        hasMoreData = hasMore;
    }



    public PhotoAdapter(Picasso picasso,List<Photo> photoList) {
        this.picasso=picasso;
        this.photoList = photoList;
    }

    interface OnTouchEventListner{

        void onTouchEvent();

    }

    void setOnTouchEventListner(OnTouchEventListner onTouchEventListner){

        this.onTouchEventListner=onTouchEventListner;

    }
    OnTouchEventListner returnTouchListner(){
        return onTouchEventListner;
    }



    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photolist, parent, false);
        PhotoAdapter.ViewHolder viewHolder = new PhotoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        picasso.load(holder.photo.getPhotoUrl(Photo.PHOTO_THUMB)).into(holder.imageView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.title.setText(photo.title);
        int visibility = (hasMoreData && position==photoList.size()-1)?View.VISIBLE:View.GONE;
        holder.loadMore.setVisibility(visibility);
        holder.photo = photo;
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.load_more)
        Button loadMore;

        Photo photo;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            loadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTouchEventListner.onTouchEvent();
                    view.setVisibility(View.GONE);
                }
            });
        }

    }
      void updateImages(List<Photo> newPhotos) {

        photoList=newPhotos;
        notifyDataSetChanged();


    }

}
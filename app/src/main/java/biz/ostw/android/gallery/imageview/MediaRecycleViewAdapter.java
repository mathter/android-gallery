package biz.ostw.android.gallery.imageview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.io.FileNotFoundException;

import biz.ostw.android.gallery.R;
import biz.ostw.android.gallery.media.Media;

public class MediaRecycleViewAdapter extends PagedListAdapter<Media, ImageItemViewHolder> {

    private static final DiffUtil.ItemCallback<Media> DIFFCALLBACK = new DiffUtil.ItemCallback<Media>() {
        @Override
        public boolean areItemsTheSame(@NonNull Media oldItem, @NonNull Media newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Media oldItem, @NonNull Media newItem) {
            return oldItem.getUri() == newItem.getUri()
                    || (oldItem.getUri() != null ? oldItem.getUri().equals(newItem.getUri()) : false);
        }
    };

    private final Context context;

    protected MediaRecycleViewAdapter(Context context) {
        super(DIFFCALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_view, null);
        final ImageItemViewHolder holder = new ImageItemViewHolder(layoutView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemViewHolder holder, int position) {
        Media media = this.getItem(position);
        try {
            if (media.getPreviewUri() != null) {
                Drawable drawable = Drawable.createFromStream(this.context.getContentResolver().openInputStream(media.getPreviewUri()), "d");//new MovieDrawable(this.context.getContentResolver().openInputStream(media.getPreviewUri()));
                holder.imageView.setImageDrawable(drawable);
            }
            holder.textView.setText(media.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

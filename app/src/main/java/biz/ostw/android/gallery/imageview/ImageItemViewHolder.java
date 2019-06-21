package biz.ostw.android.gallery.imageview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import biz.ostw.android.gallery.R;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    TextView textView;

    public ImageItemViewHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView = itemView.findViewById(R.id.imageItemView_imageView);
        this.textView = itemView.findViewById(R.id.imageItemView_name);
    }
}

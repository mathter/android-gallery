package biz.ostw.android.gallery.imageview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import biz.ostw.android.gallery.R;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<ImageItemViewHolder> {

    @NonNull
    @Override
    public ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_view, null);
        final ImageItemViewHolder holder = new ImageItemViewHolder(layoutView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        holder.textView.setText("This is a text " + position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}

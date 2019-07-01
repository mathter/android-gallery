package biz.ostw.android.gallery.media.ds;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import biz.ostw.android.gallery.media.Media;

public class MediaDataSourceFactory extends DataSource.Factory<Integer, Media> {

    private final Context context;

    public MediaDataSourceFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new MediaDataSource(this.context);
    }
}

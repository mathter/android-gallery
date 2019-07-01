package biz.ostw.android.gallery.media.ds;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

import biz.ostw.android.gallery.media.Media;
import biz.ostw.android.gallery.media.db.MediaDatabase;

class MediaDataSource extends PositionalDataSource<Media> {

    private final MediaDatabase mdb;

    private final List<? extends Media> data;

    private final int size;

    public MediaDataSource(Context context) {
        this.mdb = MediaDatabase.getInstance(context);
        this.data = this.mdb.get();
        this.size = this.data.size();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Media> callback) {
        final List<Media> result;

        if (params.requestedStartPosition < this.size) {

            int stop = this.size - (params.requestedStartPosition + params.pageSize);
            stop = stop > 0 ? params.pageSize : this.size - 1;

            result = (List<Media>) this.data.subList(params.requestedStartPosition, stop);
        } else {
            result = new ArrayList<>();
        }

        if (params.placeholdersEnabled) {
            callback.onResult(result, params.requestedStartPosition, result.size());
        } else {
            callback.onResult(result, params.requestedStartPosition);
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Media> callback) {
        final List<Media> result;
        int stop = this.size - (params.startPosition + params.loadSize);
        stop = stop > 0 ? params.startPosition + params.loadSize : this.size - 1;

        if (stop > 0) {
            result = (List<Media>) this.data.subList(params.startPosition, stop);
        } else {
            result = new ArrayList<>();
        }

        callback.onResult(result);
    }
}

package biz.ostw.android.gallery.imageview;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import biz.ostw.android.gallery.media.Media;
import biz.ostw.android.gallery.media.ds.MediaDataSourceFactory;

public class MediaViewModel extends ViewModel {

    private LiveData<Uri> uriParam;

    private LiveData<PagedList<Media>> data;

    public MediaViewModel(Context context) {
        final MediaDataSourceFactory factory = new MediaDataSourceFactory(context);

        this.data = new LivePagedListBuilder(factory, this.getConfig()).build();
    }

    public LiveData<PagedList<Media>> getData() {
        return this.data;
    }

    public LiveData<Uri> getUriParam() {
        return uriParam;
    }

    public void setUriParam(LiveData<Uri> uriParam) {
        this.uriParam = uriParam;
    }

    private PagedList.Config getConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();
    }
}

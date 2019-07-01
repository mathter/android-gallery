package biz.ostw.android.gallery.imageview;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import biz.ostw.android.gallery.media.Media;
import biz.ostw.android.gallery.media.ds.MediaDataSourceFactory;

public class MediaViewModel extends ViewModel {

    public final MutableLiveData<Uri> uriParam;

    public final LiveData<PagedList<Media>> data;

    public MediaViewModel(Context context) {
        final MediaDataSourceFactory factory = new MediaDataSourceFactory(context);

        this.uriParam = new MutableLiveData<>();
        this.data = new LivePagedListBuilder(factory, this.getConfig()).build();
    }

    private PagedList.Config getConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(3)
                .setPageSize(3)
                .build();
    }
}

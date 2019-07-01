package biz.ostw.android.gallery.media;

import android.net.Uri;

public interface Media {

    public Uri getUri();

    public Uri getPreviewUri();

    public String getName();

    public long getWeighting();

    public void setWeighting(long value);
}

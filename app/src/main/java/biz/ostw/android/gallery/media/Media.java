package biz.ostw.android.gallery.media;

import android.net.Uri;

public interface Media {

    public Uri getRootUri();

    public void setRootUri(Uri value);

    public Uri getMediaUri();

    public String getName();

    public void setName(String value);

    public long getWeighting();

    public void setWeighting(long value);
}

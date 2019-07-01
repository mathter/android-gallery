package biz.ostw.android.gallery.media;

import android.net.Uri;

import java.util.List;

public interface FileService {

    public void update();

    public List<? extends Media> flat();
}

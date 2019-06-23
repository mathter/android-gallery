package biz.ostw.android.gallery.media.local;

import android.net.Uri;

import java.util.Collection;

import biz.ostw.android.gallery.media.MediaService;

public class LocalMediaService extends MediaService {

    private static final String SCHEME_MEDIA = "media";

    @Override
    protected Collection<Uri> listInternal(Uri uri) {
        final Collection<Uri> result;

        if (SCHEME_MEDIA.equals(uri.getScheme())) {
            String path = uri.getPath();

            switch (path) {
                case "/":
                    result = listRoot(uri);
                    break;

                default:
                    result = null;
            }
        } else {
            result = null;
        }

        return result;
    }

    private Collection<Uri> listRoot(Uri uri) {
        return null;
    }
}
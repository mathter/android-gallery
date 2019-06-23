package biz.ostw.android.gallery.media;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class MediaService {

    public static Collection<Uri> list(Uri uri) {
        Collection<Uri> result = new ArrayList<>();

        for (Iterator<MediaService> it = listServices(); it.hasNext(); ) {
            final MediaService service = it.next();
            final Collection<Uri> partial = service.listInternal(uri);

            if (partial != null) {
                result.addAll(partial);
            }
        }

        return result;
    }

    protected static final Iterator<MediaService> listServices() {
        return ServiceLoader.load(MediaService.class).iterator();
    }

    protected abstract Collection<Uri> listInternal(Uri uri);
}

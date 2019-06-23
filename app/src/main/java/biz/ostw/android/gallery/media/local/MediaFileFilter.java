package biz.ostw.android.gallery.media.local;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

class MediaFileFilter implements java.io.FileFilter {

    private static final MimeTypeMap MAP = MimeTypeMap.getSingleton();

    private static final Set<String> MIME_TYPE;

    @Override
    public boolean accept(File pathname) {
        final String extension = this.getExtension(pathname);
        final String mimeType = extension != null ? MAP.getMimeTypeFromExtension(extension) : null;

        return mimeType != null && MIME_TYPE.contains(this.getPrimary(mimeType));
    }

    private String getPrimary(String mimeType) {
        final int index = mimeType.indexOf('/');

        return mimeType.substring(0, index);
    }

    private String getExtension(File file) {
        final String name = file.getName();
        final int index = name.lastIndexOf('.');

        if (index > 0) {
            return name.substring(index + 1);
        } else {
            return null;
        }
    }

    static {
        Set<String> tmp = new TreeSet<String>();
        tmp.add("image");
        tmp.add("video");

        MIME_TYPE = tmp;
    }
}

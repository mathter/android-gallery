package biz.ostw.android.gallery.media.local;

import java.io.File;
import java.io.FileFilter;

class FileDirectoryWithMediaFileFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        final File[] files = pathname.listFiles(new MediaFileFilter());

        return files != null && files.length > 0;
    }
}

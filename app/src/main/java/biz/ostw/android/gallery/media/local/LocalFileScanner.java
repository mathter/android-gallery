package biz.ostw.android.gallery.media.local;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class LocalFileScanner {

    private static final File[] ROOT_CHILDREN;

    private static final FileFilter DIRECTORY_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };

    public Collection<File> listFolderWithMedia() {
        Collection<File> result = new ArrayList<>(5000);

        this.list(new File("/"), result, new FileDirectoryWithMediaFileFilter());

        return result;
    }

    public Collection<File> list(File file, Collection<File> accomulator, FileFilter fileFilter) {

        if (file.isDirectory()) {
            final File[] subdirectories = "/".equals(file.getPath()) ? ROOT_CHILDREN : file.listFiles(DIRECTORY_FILTER);

            if (subdirectories != null) {
                for (File f : subdirectories) {
                    list(f, accomulator, fileFilter);
                }
            }

            final File[] files = fileFilter != null ? file.listFiles(fileFilter) : file.listFiles();

            if (files != null) {
                for (File f : files) {
                    accomulator.add(f);
                }
            }
        } else {
            if (fileFilter != null || fileFilter.accept(file)) {
                accomulator.add(file);
            }
        }

        return accomulator;
    }

    static {
        try {
            ROOT_CHILDREN = new File[]{
                    new File(new URI("file:///data")),
                    new File(new URI("file:///etc")),
                    new File(new URI("file:///mnt")),
                    new File(new URI("file:///odm")),
                    new File(new URI("file:///proc")),
                    new File(new URI("file:///product")),
                    new File(new URI("file:///storage")),
                    new File(new URI("file:///storage/0")),
                    new File(new URI("file:///system")),
                    new File(new URI("file:///vendor"))
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

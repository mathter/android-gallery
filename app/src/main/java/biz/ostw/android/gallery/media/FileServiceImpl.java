package biz.ostw.android.gallery.media;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import biz.ostw.android.gallery.ServiceConnection;
import biz.ostw.android.gallery.media.db.MediaDatabase;
import biz.ostw.android.gallery.media.local.LocalFileScanner;

public class FileServiceImpl extends Service implements FileService {
    public static final String ACTION_UPDATE = "biz.ostw.android.gallery.media.local.UPDATE";

    private final IBinder binder = new LocalBinder();

    private final Executor executor = Executors.newFixedThreadPool(1);

    public FileServiceImpl() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case ACTION_UPDATE:
                    this.update();
                    break;

                default:
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void update() {

        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                final LocalFileScanner scanner = new LocalFileScanner();
                final Collection<File> list = scanner.listFolderWithMedia();
                final MediaDatabase mdb = MediaDatabase.getInstance(FileServiceImpl.this);

                Uri rootMediaUri = Uri.parse("media:/");

                for (File file : list) {
                    Uri fileUri = Uri.parse(file.toURI().toString());
                    mdb.insert(rootMediaUri, file.getName(), fileUri);
                }
            }
        });
    }

    @Override
    public List<Media> list(Uri rootMediaUri, int start, int count) {
        final MediaDatabase mdb = MediaDatabase.getInstance(this);
        mdb.getChild(rootMediaUri);
        return null;
    }

    public class LocalBinder extends Binder implements ServiceConnection.Binder<FileService> {
        @Override
        public FileService getService() {
            return FileServiceImpl.this;
        }
    }
}

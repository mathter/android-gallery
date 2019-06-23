package biz.ostw.android.gallery.media;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import java.io.File;
import java.util.Collection;

import biz.ostw.android.gallery.media.local.LocalFileScanner;

public class ScanService extends Service {
    public static final String ACTION_UPDATE = "biz.ostw.android.gallery.media.local.UPDATE";

    public ScanService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

    private void update() {
        final LocalFileScanner scanner = new LocalFileScanner();
        final Collection<File> list = scanner.listFolderWithMedia();
        final MediaDatabase mdb = MediaDatabase.getInstance(this);

        Uri rootMediaUri = Uri.parse("media:/");

        for (File file : list) {
            Uri fileUri = Uri.parse(file.toURI().toString());
            mdb.insert(rootMediaUri, file.getName(), fileUri);
        }
    }
}

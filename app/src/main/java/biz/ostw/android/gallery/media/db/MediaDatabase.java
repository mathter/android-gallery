package biz.ostw.android.gallery.media.db;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import biz.ostw.android.gallery.media.Media;
import biz.ostw.android.gallery.media.TypeConverter;

@Database(entities = {FlatMediaRecord.class}, version = 1, exportSchema = true)
@TypeConverters({TypeConverter.class})
public abstract class MediaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "media-db";

    private static MediaDatabase INSTANCE = null;

    private static ReadWriteLock LOCK = new ReentrantReadWriteLock();

    protected abstract MediaDao dao();

    public static MediaDatabase getInstance(Context context) {
        LOCK.readLock().lock();

        try {
            if (INSTANCE == null) {
                LOCK.readLock().unlock();
                LOCK.writeLock().lock();

                try {
                    INSTANCE = Room
                            .databaseBuilder(context, MediaDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })
                            .fallbackToDestructiveMigrationOnDowngrade()
                            .allowMainThreadQueries()
                            .build();

                } finally {
                    LOCK.readLock().lock();
                    LOCK.writeLock().unlock();
                }
            }
        } finally {
            LOCK.readLock().unlock();
        }

        return INSTANCE;
    }

    @Override
    public void clearAllTables() {

    }

    public Media insert(Uri uri, Uri previewUri) {
        final long id;
        final FlatMediaRecord r = new FlatMediaRecord();

        r.setUri(uri);
        r.setPreviewUri(previewUri);
        r.setLastScannedTime(new Date());
        r.setWeighting(0L);

        id = this.dao().insert(Collections.singletonList(r))[0];

        if (id > 0) {
            r.setId(id);
            return r;
        } else {
            return null;
        }
    }

    public Media get(Uri uri) {
        return this.dao().get(uri);
    }

    public List<? extends Media> get() {
        return this.dao().get();
    }

    public void update(Media record) {
        if (record instanceof FlatMediaRecord) {
            this.dao().update((FlatMediaRecord) record);
        } else {
            throw new IllegalArgumentException(record.getClass() + " is not instance of " + FlatMediaRecord.class);
        }
    }

    public void delete(Media media) {
        if (media instanceof FlatMediaRecord) {
            this.dao().delete((FlatMediaRecord) media);
        } else {
            throw new IllegalArgumentException(media.getClass() + " is not instance of " + FlatMediaRecord.class);
        }
    }

    public void delete(Uri uri) {
        this.dao().delete(uri);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}

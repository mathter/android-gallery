package biz.ostw.android.gallery.media;

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
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Database(entities = {MediaRecord.class}, version = 1, exportSchema = true)
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

    public MediaRecord insert(Uri mediaRootUri, String name, Uri fileUri) {
        final long id;
        final MediaRecord r = new MediaRecord();

        r.setRootUri(mediaRootUri);
        r.setName(name);
        r.setFileUri(fileUri);
        r.setLastScannedTime(new Date());

        id = this.dao().insert(Collections.singletonList(r))[0];

        if (id > 0) {
            r.setId(id);
            return r;
        } else {
            return null;
        }
    }

    public void update(MediaRecord record) {
        this.dao().update(record);
    }

    public MediaRecord get(long id) {
        return this.dao().get(id);
    }

    public List<MediaRecord> get(Uri mediaRootUri) {
        return this.dao().get(mediaRootUri);
    }

    public void delete(MediaRecord record) {
        this.dao().delete(record);
    }

    public MediaRecord delete(final long id) {
        return this.runInTransaction(new Callable<MediaRecord>() {
            @Override
            public MediaRecord call() throws Exception {
                final MediaRecord record = MediaDatabase.this.dao().get(id);

                if (record != null) {
                    MediaDatabase.this.delete(record);
                }

                return record;
            }
        });
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}

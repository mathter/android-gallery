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
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import biz.ostw.android.gallery.media.Media;
import biz.ostw.android.gallery.media.TypeConverter;

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

    public Media insert(Uri mediaRootUri, String name, Uri fileUri) {
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

    public void update(Media record) {
        if (record instanceof MediaRecord) {
            this.dao().update((MediaRecord) record);
        } else {
            throw new IllegalArgumentException(record.getClass() + " is not instance of " + MediaRecord.class);
        }
    }

    public List<? extends Media> getChild(Uri mediaRootUri) {
        return this.dao().getChild(mediaRootUri);
    }

    public Media get(Uri mediaUri) {
        return this.dao().get(this.parseId(mediaUri));
    }

    public void delete(Media media) {
        if (media instanceof MediaRecord) {
            this.dao().delete((MediaRecord) media);
        } else {
            throw new IllegalArgumentException(media.getClass() + " is not instance of " + MediaRecord.class);
        }
    }

    public void delete(Uri mediaUri) {
        this.dao().delete(this.parseId(mediaUri));
    }

    private long parseId(Uri mediaUri) {
        final List<String> pathSegments;
        final int size;
        final String idSegment;
        final long id;

        try {
            if ((pathSegments = mediaUri.getPathSegments()) != null &&
                    (size = pathSegments.size()) >= 2 &&
                    (idSegment = pathSegments.get(size - 2)) != null) {
                id = Long.parseLong(idSegment);
            } else {
                throw new RuntimeException(mediaUri + " is not valid uri!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("There is not media for uri '" + mediaUri + "'!", e);
        }

        return id;
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}

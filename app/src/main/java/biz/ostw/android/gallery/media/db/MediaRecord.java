package biz.ostw.android.gallery.media.db;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import biz.ostw.android.gallery.media.Media;

@Entity(
        tableName = "media_record",
        indices = {
                @Index(name = "idx_media_uri", value = "media_root_uri", unique = false)
        })
public class MediaRecord implements Media {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "media_root_uri")
    private Uri rootUri;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "file_uri")
    private Uri fileUri;

    @ColumnInfo(name = "last_scanned_time")
    private Date lastScannedTime;

    @ColumnInfo(name = "weighting")
    private long weighting;

    @Override
    public Uri getMediaUri() {
        return Uri.withAppendedPath(Uri.withAppendedPath(this.rootUri, String.valueOf(this.id)), this.name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Uri getRootUri() {
        return rootUri;
    }

    @Override
    public void setRootUri(Uri rootUri) {
        this.rootUri = rootUri;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public Date getLastScannedTime() {
        return lastScannedTime;
    }

    public void setLastScannedTime(Date lastScannedTime) {
        this.lastScannedTime = lastScannedTime;
    }

    @Override
    public long getWeighting() {
        return weighting;
    }

    @Override
    public void setWeighting(long weighting) {
        this.weighting = weighting;
    }
}

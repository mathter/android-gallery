package biz.ostw.android.gallery.media;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "media_record",
        indices = {
                @Index(name = "idx_media_uri", value = "media_root_uri", unique = false)
        })
public class MediaRecord {

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

    public Uri getMediaUri() {
        return Uri.withAppendedPath(this.rootUri, this.id + this.name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getRootUri() {
        return rootUri;
    }

    public void setRootUri(Uri rootUri) {
        this.rootUri = rootUri;
    }

    public String getName() {
        return name;
    }

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

    public long getWeighting() {
        return weighting;
    }

    public void setWeighting(long weighting) {
        this.weighting = weighting;
    }
}

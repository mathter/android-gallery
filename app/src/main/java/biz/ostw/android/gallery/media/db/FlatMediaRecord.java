package biz.ostw.android.gallery.media.db;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import biz.ostw.android.gallery.media.Media;

@Entity(
        tableName = "flat_media_record",
        indices = {
                @Index(name = "idx_uri", value = "uri", unique = true)
        })
public class FlatMediaRecord implements Media {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "uri")
    private Uri uri;

    @ColumnInfo(name = "preview_uri")
    private Uri previewUri;

    @ColumnInfo(name = "last_scanned_time")
    private Date lastScannedTime;

    @ColumnInfo(name = "weighting")
    private long weighting;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public Uri getPreviewUri() {
        return previewUri;
    }

    public void setPreviewUri(Uri previewUri) {
        this.previewUri = previewUri;
    }

    @Override
    public String getName() {
        return this.uri != null ? this.uri.getLastPathSegment() : null;
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

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof FlatMediaRecord) {
            return this.id == ((FlatMediaRecord) obj).id;
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FlatMediaRecord[");

        sb.append("id=").append(this.id);
        sb.append("; uri='").append(this.uri);
        sb.append("'; lastScannedTime=").append(this.lastScannedTime);
        sb.append("; weighting=").append(this.weighting);
        sb.append("]");

        return sb.toString();
    }
}

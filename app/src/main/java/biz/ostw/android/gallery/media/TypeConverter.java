package biz.ostw.android.gallery.media;

import android.net.Uri;

import java.util.Date;

public class TypeConverter {

    @androidx.room.TypeConverter
    public Long fromDate(Date value) {
        return value != null ? value.getTime() : null;
    }

    @androidx.room.TypeConverter
    public Date toDate(Long value) {
        return value != null ? new Date(value) : null;
    }

    @androidx.room.TypeConverter
    public String fromUri(Uri value) {
        return value != null ? value.toString() : null;
    }

    @androidx.room.TypeConverter
    public Uri toUri(String value) {
        return value != null ? Uri.parse(value) : null;
    }
}

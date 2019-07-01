package biz.ostw.android.gallery.media.db;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Collection;
import java.util.List;

@Dao
public interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insert(Collection<FlatMediaRecord> records);

    @Query("select * from flat_media_record where id = :id")
    public FlatMediaRecord get(long id);

    @Query("select * from flat_media_record where uri = :uri")
    public FlatMediaRecord get(Uri uri);

    @Query("select * from flat_media_record order by weighting desc")
    public List<FlatMediaRecord> get();

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void update(FlatMediaRecord... records);

    @Delete
    public void delete(FlatMediaRecord record);

    @Query("delete from flat_media_record where id = :id")
    public void delete(long id);

    @Query("delete from flat_media_record where uri = :uri")
    public void delete(Uri uri);

    @Delete
    public void delete(Collection<FlatMediaRecord> records);
}

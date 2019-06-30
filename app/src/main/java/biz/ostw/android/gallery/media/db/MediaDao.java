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
    public long[] insert(Collection<MediaRecord> records);

    @Delete
    public void delete(Collection<MediaRecord> records);

    @Query("select * from media_record order by weighting")
    public List<MediaRecord> getChild();

    @Query("select * from media_record where media_root_uri = :mediaRootUri order by weighting")
    public List<MediaRecord> getChild(Uri mediaRootUri);

    @Query("select * from media_record where id = :id")
    public MediaRecord get(long id);

    @Update(onConflict = OnConflictStrategy.ABORT)
    public void update(MediaRecord... records);

    @Delete
    public void delete(MediaRecord record);

    @Query("delete from media_record where id = :id")
    public void delete(long id);
}

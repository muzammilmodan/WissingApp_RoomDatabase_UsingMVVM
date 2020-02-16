package com.wission.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.wission.Model.UserDetails_Room;
import java.util.List;

@Dao
public interface User_roomDao {

    @Query("SELECT * FROM userdetails")
    List<UserDetails_Room> getAll();

    @Insert
    void insert(UserDetails_Room task);

    @Delete
    void delete(UserDetails_Room task);

    @Update
    void update(UserDetails_Room task);
}

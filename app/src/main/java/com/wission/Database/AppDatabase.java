package com.wission.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wission.Dao.User_roomDao;
import com.wission.Model.UserDetails_Room;

@Database(entities = {UserDetails_Room.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {
    public abstract User_roomDao taskDao();
}

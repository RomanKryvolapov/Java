package com.company;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public abstract class LogDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void update(LogExample log);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LogExample log);

    @Query("SELECT * FROM LogExample WHERE sendStatus = :isSend")
    public abstract List <LogExample> getListForSend(Boolean isSend);
}

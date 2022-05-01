package com.ak.spaceshooter.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LevelDAO {
    @Delete
    void delete(Level level);

    @Query("SELECT * FROM level")
    LiveData<List<Level>> getAll();

    @Query("SELECT * FROM level WHERE level_number = :levelNumber")
    Level getById(String levelNumber);

    @Insert
    void insert(Level... levels);

    @Update
    void updateLevels(Level... levels);

    @Delete
    void deleteLevels(Level... levels);
}

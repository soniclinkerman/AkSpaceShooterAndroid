package com.ak.spaceshooter.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE userid = :userId")
    User getById(int userId);

    @Insert
    void insert(User... users);

    @Update
    void updateUsers(User... users);

    @Delete
    void deleteUsers(User... users);


}
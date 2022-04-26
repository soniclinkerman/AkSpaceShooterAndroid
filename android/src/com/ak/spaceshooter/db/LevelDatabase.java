package com.ak.spaceshooter.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Database(entities = {Level.class}, version = 1, exportSchema = false)
public abstract class LevelDatabase extends RoomDatabase {
    public interface LevelListener{
        void onLevelReturned(Level level);
    }

    public abstract LevelDAO levelDAO();

    private static LevelDatabase INSTANCE;

    private static RoomDatabase.Callback createLevelDatabaseCallback =
            new RoomDatabase.Callback() {
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    insert("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");

                }

            };

    public static LevelDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LevelDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LevelDatabase.class, "level_database")
                            .addCallback(createLevelDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void insert(Level... levels) {
        (new Thread(()->INSTANCE.levelDAO().insert(levels))).start();
    }
    public static void insert(String... levelNames) {
        Level[] levels = new Level[levelNames.length];
        for(int i = 0;i< levelNames.length;i++){
            levels[i] = new Level(levelNames[i], false, 0);
        }
        (new Thread(()->INSTANCE.levelDAO().insert(levels))).start();
    }

    public static void updateLevel(Level... levels) {
        (new Thread(()->INSTANCE.levelDAO().updateLevels(levels))).start();
    }






}
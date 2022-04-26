package com.ak.spaceshooter.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public interface UserListener{
        void onUserReturned(User user);
    }

    public abstract UserDAO userDAO();

    private static UserDatabase INSTANCE;

    private static RoomDatabase.Callback createJokeDatabaseCallback =
            new RoomDatabase.Callback() {
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                        insertUser(new User(1, 0, "default", 1,  "default"));
                    }

            };

    public static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                            .addCallback(createJokeDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void insertUser(User user) {
        (new Thread(()->INSTANCE.userDAO().insert(user))).start();
    }






}
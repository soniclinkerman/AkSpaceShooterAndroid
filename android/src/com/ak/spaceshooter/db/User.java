package com.ak.spaceshooter.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="user")
public class User {

    public User(int id, long high_score, String username, int level, String selectedCharacter) {
        this.id = id;
        this.high_score = high_score;
        this.username=username;
        this.level=level;
        this.selectedCharacter = selectedCharacter;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userid")
    public int id;

    @ColumnInfo(name = "high_score")
    public long high_score;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "selectedCharacter")
    public String selectedCharacter;
}
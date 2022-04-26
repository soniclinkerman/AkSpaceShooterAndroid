package com.ak.spaceshooter.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="level")
public class Level {

    public Level(String id, boolean completed, long high_score){
        this.id=id;
        this.completed=completed;
        this.high_score=high_score;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "level_number")
    public String id;

    @ColumnInfo(name = "username")
    public boolean completed;

    @ColumnInfo(name = "high_score")
    public long high_score;





}

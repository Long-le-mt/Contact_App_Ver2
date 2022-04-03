package com.example.contact_app_v3.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase{
    public abstract ContactDAO contactDAO();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "Contact").allowMainThreadQueries().build();
        }

        return instance;
    }
}

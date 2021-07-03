package com.example.newsapp.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newsapp.operations.Article;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Article.class}, version = 3, exportSchema = false)
abstract public class SavedNewsRoomDatabase extends RoomDatabase {
    public abstract SavedNewsDAO savedNewsDAO();
    private static volatile SavedNewsRoomDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SavedNewsRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (SavedNewsRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), SavedNewsRoomDatabase.class, "saved_articles").build();
                }
            }
        }
        return instance;
    }


    private static RoomDatabase.Callback savedNewsDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriterExecutor.execute(() -> {
                SavedNewsDAO savedNewsDAO = instance.savedNewsDAO();

            });
        }
    };
}

package com.example.mvvmroom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//Marks a class as a RoomDatabase.The class should be an abstract class and extend RoomDatabase.

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //created because we wanted to turn this class into singleton class--cant create multiple instances n can be used every where in the class
    private static NoteDatabase instance;

    //access dao method and
    public abstract NoteDao noteDao();

    //synchronized means only one thread at a time can access notedatabase
    //create a single db instance
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            //With fallbackToDestructiveMigration we can let Room recreate our database if we increase the version number.
            //We create our database in form of a static singleton with the databaseBuilder, where we have to pass our database class and a file name.
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
          return instance;
    }

    //populate some data into notes on first time create so that the table is not empty
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(instance).execute();
        }
    };

    private static class PopulatedbAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
        public PopulatedbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Dscription 1", 1));
            noteDao.insert(new Note("Title 2", "Dscription 2", 2));
            noteDao.insert(new Note("Title 3", "Dscription 3", 3));
            return null;
        }
    }
}

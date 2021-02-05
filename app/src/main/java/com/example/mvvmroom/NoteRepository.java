package com.example.mvvmroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    //member variables
    private NoteDao noteDao;
    LiveData<List<Note>> allNotes;

    /*later on the viewmodel will also be passed in the application and application is a subclass of context so we can useit as a context to create
    our database instance*/
    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    //we will call the methods here
    public void insert(Note note){
      new InsertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

//Insertion
    //creating async task --it is  static so that it doesnot have reference to the repository
    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void>{

        //member variable
        private NoteDao noteDao;

        //class is static so we have to cretae constructor to access it
        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        //doin background is the only mandatory method
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    //Updation
    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        public UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }


    //Deletion
    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        public DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    //Delete All Notes
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
        public DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}

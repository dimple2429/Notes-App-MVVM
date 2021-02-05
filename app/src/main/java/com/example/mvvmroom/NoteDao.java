package com.example.mvvmroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//dao is where you define your database  interactions. They can include a variety of query methods. it should be interface
// or an abstract class. At compile time, Room will generate an implementation of this class when it is referenced by a Database.
@Dao
public interface NoteDao {

    /*Marks a method in a Dao annotated class as an insert method.
    The implementation of the method will insert its parameters into the database.*/
    @Insert
    void insert(Note note);

   /* Marks a method in a Dao annotated class as an update method.
    The implementation of the method will update its parameters in the database if they already exists
            (checked by primary keys).
    If they don't already exists, this option will not change the database.*/
    @Update
    void update(Note note);

    /*Marks a method in a Dao annotated class as a delete method.
    The implementation of the method will delete its parameters from the database*/
    @Delete
    void delete(Note note);

   /* The value of the annotation includes the query that will be run when this method is called.
    used here for delete all the notes from the database*/
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    //getting all the data of the table
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}

package com.example.mvvmroom;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


//entity used here is for mapping of the tables in the database
//it is a room annotation used to create all necessary code in sqlite db
@Entity(tableName = "note_table")

//by default name of the table is note
public class Note {

    //give unique id to each entry so we generate id-- used auto generate for automatically generating id for each entry
    @PrimaryKey(autoGenerate = true)
    private int id;

    //declare member variables - by default room creates a column for it
    private String title;
    private String description;
    private int priority;


    //constructors to create the note objects
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //we only use setter for id coz later on we will get the data from id-- room will set id to note object
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}

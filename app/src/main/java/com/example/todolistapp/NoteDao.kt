package com.example.todolistapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolistapp.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * from notesTable order by id ASC")
    fun getAllNotes() : LiveData<List<Note>>

}
package com.example.todolistapp

import androidx.lifecycle.LiveData
import com.example.todolistapp.Note
import com.example.todolistapp.NoteDao

class NoteRepositry(private val notesDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    fun insert(note: Note){
        notesDao.insert(note)
    }

    fun delete(note: Note){
        notesDao.delete(note)
    }

    fun update(note: Note) {
        notesDao.update(note)
    }
}
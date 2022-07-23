package com.example.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.Note
import com.example.todolistapp.NoteDatabase
import com.example.todolistapp.NoteRepositry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes : LiveData<List<Note>>
    val repositry: NoteRepositry

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repositry = NoteRepositry(dao)
        allNotes = repositry.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repositry.delete(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repositry.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repositry.update(note)
    }
}
package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEdit : EditText
    lateinit var noteDesEdit : EditText
    lateinit var addUpdateBtn : Button
    lateinit var viewModel: NoteViewModel
    var noteId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdit = findViewById(R.id.edit_note_title)
        noteDesEdit = findViewById(R.id.edit_note_des)
        addUpdateBtn = findViewById(R.id.button_add_update)
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val noteType = intent.getStringExtra("noteType")
        if(noteType == "Edit") {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteID",1)
            addUpdateBtn.text = "Update Note"
            noteTitleEdit.setText(noteTitle)
            noteDesEdit.setText(noteDesc)
        }else {
            addUpdateBtn.text = "Save Note"
        }

        addUpdateBtn.setOnClickListener{
            val noteTitle = noteTitleEdit.text.toString()
            val noteDesc = noteDesEdit.text.toString()

            if (noteType == "Edit"){
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate : String = sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDesc,currentDate)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated...",Toast.LENGTH_LONG).show()
                }
            }else{
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate : String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDesc,currentDate))
                    Toast.makeText(this,"Note Added...", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }
    }
}
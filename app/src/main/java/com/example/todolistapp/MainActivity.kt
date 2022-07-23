package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

// VIEW
class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var notesRV:RecyclerView
    lateinit var addFAB: FloatingActionButton
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //notesRV = findViewById(R.id.rv_notes)
        //addFAB = findViewById(R.id.fa_note)

        binding.rvNotes.layoutManager = LinearLayoutManager(this)

        val noteRvAdapter = NoteRVAdapter(this,this)
        binding.rvNotes.adapter = noteRvAdapter
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        viewModel.allNotes.observe(this) { list ->
            list?.let {
                noteRvAdapter.updateList(it)
            }
        }
        binding.faNote.setOnClickListener {
            val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDeleteIconClick(note: Note) {
        MaterialAlertDialogBuilder(this)
            .setMessage("DO YOU WANT TO DELETE")
            .setCancelable(false)
            .setNegativeButton("NO") {_ , _ ->}
            .setPositiveButton("YES") {_ , _ ->
                viewModel.deleteNote(note)
            }
            .show()


        Toast.makeText(this,"${note.noteTitle} Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID", note.id)
        startActivity(intent)
        this.finish()
    }
}
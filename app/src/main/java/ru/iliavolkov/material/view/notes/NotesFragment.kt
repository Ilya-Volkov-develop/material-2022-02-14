package ru.iliavolkov.material.view.notes

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import coil.load
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentNotesBinding
import ru.iliavolkov.material.model.Note
import ru.iliavolkov.material.utils.TYPE_HEADER
import ru.iliavolkov.material.utils.TYPE_NOTE
import ru.iliavolkov.material.view.main.asteroid.ItemTouchHelperCallback

@Suppress("DEPRECATION")
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding get() = _binding!!
    private var listNotes:MutableList<Note> = mutableListOf()
    private lateinit var adapter: NotesRecyclerViewAdapter
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listNotes.add(0, Note("Заголовок", favorite = true, type = TYPE_HEADER))
        val shared = requireActivity().getPreferences(Activity.MODE_PRIVATE)
        var count = 1
        repeat(shared.getInt("countNotes",0)){
            listNotes.add(Note(title = shared.getString("noteTitle$count",""),
                    description = shared.getString("noteDesc$count",""),
                    favorite = shared.getBoolean("noteFav$count",false)))
            count++
        }
        adapter = NotesRecyclerViewAdapter({ itemTouchHelper.startDrag(it) }, requireActivity())
        binding.notesRecyclerView.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerView)

        adapter.setNotes(listNotes)
        binding.fabNotes.setOnClickListener {
            binding.searchView.clearFocus()
            adapter.filter.filter("")
            newNote()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }

    private fun newNote() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater? = LayoutInflater.from(requireContext())
        val dialogNewNote: View = inflater!!.inflate(R.layout.dialog_new_note, null)
        alertDialog.setView(dialogNewNote)
        val dialog: Dialog? = alertDialog.create()
        val save: Button = dialogNewNote.findViewById(R.id.btnSave)
        val cancel:Button = dialogNewNote.findViewById(R.id.btnCancel)
        val editTitle:EditText = dialogNewNote.findViewById(R.id.title)
        val editDesc:EditText = dialogNewNote.findViewById(R.id.description)
        val favoriteIcon:ImageView = dialogNewNote.findViewById(R.id.iconFavorite)
        var isFavorite = false
        favoriteIcon.setOnClickListener {
            if (!isFavorite) favoriteIcon.load(R.drawable.ic_favorite_filled)
            else favoriteIcon.load(R.drawable.ic_favorite_border)
            isFavorite = !isFavorite
        }
        save.setOnClickListener {
            adapter.addNote(Note("${editTitle.text}", "${editDesc.text}", isFavorite, type = TYPE_NOTE))
            binding.notesRecyclerView.smoothScrollToPosition(if (isFavorite) 1 else adapter.itemCount - 1)
            dialog?.dismiss()
        }
        cancel.setOnClickListener { dialog?.dismiss() }
        dialog?.setCancelable(false)
        dialog?.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.filter.filter("")
        var count = 0
        val shared = requireActivity().getPreferences(Activity.MODE_PRIVATE).edit()
        adapter.notesData.forEach {
            if (count != 0) {
                shared.putString("noteTitle$count", it.title).apply()
                shared.putString("noteDesc$count", it.description).apply()
                shared.putBoolean("noteFav$count", it.favorite).apply()
            }
            count++
        }
        shared.putInt("countNotes",adapter.notesData.size-1).apply()
        _binding = null
    }
}



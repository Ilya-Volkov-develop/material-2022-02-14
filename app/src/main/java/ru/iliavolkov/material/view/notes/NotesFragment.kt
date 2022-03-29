package ru.iliavolkov.material.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.iliavolkov.material.databinding.FragmentNotesBinding
import ru.iliavolkov.material.model.*
import ru.iliavolkov.material.utils.ITEM_CLOSE
import ru.iliavolkov.material.utils.TYPE_HEADER
import ru.iliavolkov.material.utils.TYPE_NOTE
import ru.iliavolkov.material.view.main.asteroid.ItemTouchHelperCallback

@Suppress("DEPRECATION")
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding get() = _binding!!
    private var listNotes:MutableList<Pair<Int,Note>> = mutableListOf()
    private lateinit var adapter: NotesRecyclerViewAdapter
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NotesRecyclerViewAdapter( { itemTouchHelper.startDrag(it) },requireActivity())
        binding.notesRecyclerView.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerView)
        listNotes.add(0,Pair(ITEM_CLOSE,Note("Заголовок",favorite = true,type = TYPE_HEADER)))
        listNotes.add(Pair(ITEM_CLOSE,Note("1","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("2","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("3","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("4","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("5","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("6","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("7","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("8","Description",false,type = TYPE_NOTE)))
        listNotes.add(Pair(ITEM_CLOSE,Note("9","Description",false,type = TYPE_NOTE)))
        adapter.setNotes(listNotes)
        binding.fabNotes.setOnClickListener {
            adapter.addNote()
            binding.notesRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
        requireActivity().runOnUiThread {

        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
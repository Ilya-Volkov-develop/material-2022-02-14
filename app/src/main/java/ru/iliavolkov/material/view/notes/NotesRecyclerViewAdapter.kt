package ru.iliavolkov.material.view.notes

import android.annotation.SuppressLint
import android.graphics.Color.LTGRAY
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentNotesHeaderRecyclerViewItemBinding
import ru.iliavolkov.material.databinding.FragmentNotesRecyclerViewItemBinding
import ru.iliavolkov.material.model.Note
import ru.iliavolkov.material.utils.TYPE_HEADER
import ru.iliavolkov.material.utils.TYPE_NOTE
import ru.iliavolkov.material.view.main.asteroid.ItemTouchHelperAdapter
import ru.iliavolkov.material.view.main.asteroid.ItemTouchHelperViewAdapter
import ru.iliavolkov.material.view.main.asteroid.OnStartDragListener
import java.util.*

@Suppress("DEPRECATION")
class NotesRecyclerViewAdapter(
        private val onStartDragListener: OnStartDragListener,
        val requireActivity: FragmentActivity,
        count:Int,
        private val listener: OnItemClickListener
): RecyclerView.Adapter<NotesRecyclerViewAdapter.BaseViewHolder>(), ItemTouchHelperAdapter, Filterable {

    var notesData:MutableList<Note> = mutableListOf()
    var filteredNotesData:MutableList<Note> = mutableListOf()
    var countFavorite = count

    fun setNotes(data: List<Note>){
        this.notesData = data as MutableList<Note>
        this.filteredNotesData = notesData
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int) = notesData[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == TYPE_HEADER){
            val itemBinding: FragmentNotesHeaderRecyclerViewItemBinding =
                    FragmentNotesHeaderRecyclerViewItemBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                    )
            HeaderViewHolder(itemBinding.root)
        } else {
            val itemBinding: FragmentNotesRecyclerViewItemBinding =
                    FragmentNotesRecyclerViewItemBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                    )
            NotesViewHolder(itemBinding.root)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(this.notesData[position])
    }

    override fun getItemCount() = notesData.size

    fun addNote(newNote: Note){
        if (newNote.favorite){
            notesData.add(1, Note(newNote.title, newNote.description, newNote.favorite, type = TYPE_NOTE))
            notifyItemInserted(1)
            countFavorite++
        } else {
            notesData.add(countFavorite,Note(newNote.title, newNote.description, newNote.favorite, type = TYPE_NOTE))
            notifyItemInserted(countFavorite)
        }
    }

    abstract class BaseViewHolder(view: View):RecyclerView.ViewHolder(view){ abstract fun bind(notesData: Note) }

    inner class HeaderViewHolder(view: View):BaseViewHolder(view){ override fun bind(notesData: Note) {}}

    inner class NotesViewHolder(view: View):BaseViewHolder(view), ItemTouchHelperViewAdapter {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Note) {
            FragmentNotesRecyclerViewItemBinding.bind(itemView).run {
                noteTitle.text = data.title
                noteDescription.text = data.description
                iconFavorite.load(if (data.favorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border)
                iconFavorite.setOnClickListener {
                    if (data.favorite) {
                        countFavorite--
                        if (countFavorite<1) countFavorite = 1
                        iconFavorite.load(R.drawable.ic_favorite_border)
                        notesData.removeAt(layoutPosition).apply {
                            notesData.add(countFavorite, Note(data.title, data.description, !data.favorite, data.type))
                            notifyItemMoved(layoutPosition, countFavorite)
                        }
                    } else {
                        countFavorite++
                        if (countFavorite<1) countFavorite = 1
                        iconFavorite.load(R.drawable.ic_favorite_filled)
                        notesData.removeAt(layoutPosition).apply {
                            notesData.add(1, Note(data.title, data.description, !data.favorite, data.type))
                            notifyItemMoved(layoutPosition, 1)
                        }

                    }
                    notifyItemChanged(layoutPosition)
                    Thread {
                        Thread.sleep(300)
                        requireActivity.runOnUiThread {
                            notifyDataSetChanged()
                        }
                    }.start()
                }
                iconRemove.setOnClickListener { removeItem() }
                iconMove.setOnTouchListener { _, event ->
                    if(MotionEventCompat.getActionMasked(event)== MotionEvent.ACTION_DOWN){
                        onStartDragListener.onStartDrag(this@NotesViewHolder)
                    }
                    false
                }
                root.setOnClickListener { listener.onItemClick(data,layoutPosition) }
            }
        }

        private fun removeItem() {
            notesData.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)

        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
            itemView.setBackgroundResource(R.drawable.frame_asteroid_item)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notesData.removeAt(fromPosition).apply {
            notesData.add(toPosition, this)
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        if (notesData[position].favorite) countFavorite--
        notesData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                notesData = if (charSequence == "") {
                    filteredNotesData
                } else {
                    val resultFilteredNotes:MutableList<Note> = mutableListOf()
                    filteredNotesData.forEach { if (it.title!!.toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))) resultFilteredNotes.add(it) }
                    resultFilteredNotes

                }
                val filterResults = FilterResults()
                filterResults.values = notesData
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                notesData = filterResults?.values as MutableList<Note>
                notifyDataSetChanged()
            }

        }
    }
}
package ru.iliavolkov.material.view.notes

import android.graphics.Color.LTGRAY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentNotesHeaderRecyclerViewItemBinding
import ru.iliavolkov.material.databinding.FragmentNotesRecyclerViewItemBinding
import ru.iliavolkov.material.model.Note
import ru.iliavolkov.material.utils.ITEM_CLOSE
import ru.iliavolkov.material.utils.TYPE_HEADER
import ru.iliavolkov.material.utils.TYPE_NOTE
import ru.iliavolkov.material.view.main.asteroid.ItemTouchHelperAdapter
import ru.iliavolkov.material.view.main.asteroid.ItemTouchHelperViewAdapter
import ru.iliavolkov.material.view.main.asteroid.OnStartDragListener

@Suppress("DEPRECATION")
class NotesRecyclerViewAdapter(
        private val onStartDragListener: OnStartDragListener
): RecyclerView.Adapter<NotesRecyclerViewAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    var notesData:MutableList<Pair<Int,Note>> = mutableListOf()

    fun setNotes(data:MutableList<Pair<Int,Note>>){
        this.notesData = data
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int) = notesData[position].second.type


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
        holder.bind(this.notesData[position].second)
    }

    override fun getItemCount() = notesData.size

    fun addNote(){
        notesData.add(Pair(ITEM_CLOSE,Note("First","Description",false,type = TYPE_NOTE)))
        notifyItemInserted(itemCount - 1)
    }

    abstract class BaseViewHolder(view:View):RecyclerView.ViewHolder(view){ abstract fun bind(notesData:Note) }

    inner class HeaderViewHolder(view: View):BaseViewHolder(view){ override fun bind(notesData:Note) {}}

    inner class NotesViewHolder(view:View):BaseViewHolder(view), ItemTouchHelperViewAdapter {
        override fun bind(notesData: Note) {
            FragmentNotesRecyclerViewItemBinding.bind(itemView).run{
                noteTitle.text = notesData.title
                noteDescription.text = notesData.description
                iconFavorite.load(if (notesData.favorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border)
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
            notesData.add(toPosition,this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        notesData.removeAt(position)
        notifyItemRemoved(position)
    }
}
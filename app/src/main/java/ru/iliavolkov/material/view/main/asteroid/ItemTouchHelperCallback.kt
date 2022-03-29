package ru.iliavolkov.material.view.main.asteroid

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.iliavolkov.material.view.notes.NotesRecyclerViewAdapter

class ItemTouchHelperCallback(private val adapter: Any) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if (viewHolder !is NotesRecyclerViewAdapter.HeaderViewHolder){
            val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipe = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(drag,swipe)
        }
        return makeMovementFlags(0,0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (adapter is AsteroidRecyclerViewAdapter) adapter.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
        if (adapter is NotesRecyclerViewAdapter
                && target !is NotesRecyclerViewAdapter.HeaderViewHolder) adapter.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (adapter is AsteroidRecyclerViewAdapter) adapter.onItemDismiss(viewHolder.adapterPosition)
        if (adapter is NotesRecyclerViewAdapter) adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled() = true

    override fun isItemViewSwipeEnabled() = true

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
                if (viewHolder !is NotesRecyclerViewAdapter.HeaderViewHolder)
                        (viewHolder as ItemTouchHelperViewAdapter).onItemSelected()
        }
        return super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder !is NotesRecyclerViewAdapter.HeaderViewHolder)
                (viewHolder as ItemTouchHelperViewAdapter).onItemClear()
        super.clearView(recyclerView, viewHolder)
    }
}



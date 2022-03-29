package ru.iliavolkov.material.view.main.asteroid

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(private val adapter: AsteroidRecyclerViewAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipe = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(drag,swipe)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled() = true

    override fun isItemViewSwipeEnabled() = true

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            (viewHolder as ItemTouchHelperViewAdapter).onItemSelected()
        }
        return super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as ItemTouchHelperViewAdapter).onItemClear()
        super.clearView(recyclerView, viewHolder)
    }
}



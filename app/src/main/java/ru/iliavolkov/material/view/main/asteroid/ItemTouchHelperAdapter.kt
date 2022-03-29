package ru.iliavolkov.material.view.main.asteroid

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int,toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewAdapter {
    fun onItemSelected()
    fun onItemClear()
}

fun interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}
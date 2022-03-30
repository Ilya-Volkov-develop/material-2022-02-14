package ru.iliavolkov.material.view.notes

import ru.iliavolkov.material.model.Note

interface OnItemClickListener {
    fun onItemClick(note:Note,position:Int)
}
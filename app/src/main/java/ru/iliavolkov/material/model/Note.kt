package ru.iliavolkov.material.model

import ru.iliavolkov.material.utils.TYPE_NOTE


data class Note(
        val title:String?,
        val description:String?="",
        val favorite:Boolean = false,
        val type:Int = TYPE_NOTE
)
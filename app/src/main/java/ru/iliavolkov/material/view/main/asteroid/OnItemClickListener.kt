package ru.iliavolkov.material.view.main.asteroid

import ru.iliavolkov.material.model.NearEarthObject

interface OnItemClickListener {
    fun onItemClick(nearEarthObject: NearEarthObject)
}
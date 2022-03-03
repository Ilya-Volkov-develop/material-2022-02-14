package ru.iliavolkov.material.view.main.asteroid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentAsteroidRecyclerViewItemBinding
import ru.iliavolkov.material.model.NearEarthObject

class AsteroidRecyclerViewAdapter: RecyclerView.Adapter<AsteroidRecyclerViewAdapter.ViewHolder>() {

    private var asteroidData:List<NearEarthObject> = listOf()
    private var isVisibilityContainer:MutableList<Boolean> = mutableListOf()

    fun setAsteroids(data:List<NearEarthObject>){
        this.asteroidData = data
        repeat(data.size) { isVisibilityContainer.add(false) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_asteroid_recycler_view_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.asteroidData[position],position)
    }

    override fun getItemCount(): Int {
        return asteroidData.size
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(nearEarthObject: NearEarthObject, position: Int){
            FragmentAsteroidRecyclerViewItemBinding.bind(itemView).run{
                asteroidName.text = nearEarthObject.name
                date.text = "Дата приближения: ${nearEarthObject.closeApproachData[0].closeApproachDateFull}"
                radius.text = "Радиус: max = ${nearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMax} км\nmin = ${nearEarthObject.estimatedDiameter.kilometers.estimatedDiameterMin} км"
                speed.text = "Скорость: ${nearEarthObject.closeApproachData[0].relativeVelocity.kilometersPerHour} км/час"
                orbitalBody.text = "Орбитальное тело ${nearEarthObject.closeApproachData[0].orbitingBody}"
                motionLayout.setOnClickListener {
                    TransitionManager.beginDelayedTransition(motionLayout)
                    isVisibilityContainer[position] = !isVisibilityContainer[position]
                    attrContainer.visibility = if (isVisibilityContainer[position]) View.VISIBLE else View.GONE
                }
            }
        }
    }
}
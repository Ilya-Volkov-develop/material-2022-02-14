package ru.iliavolkov.material.view.main.asteroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentAsteroidRecyclerViewItemBinding
import ru.iliavolkov.material.model.NearEarthObject

class AsteroidRecyclerViewAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<AsteroidRecyclerViewAdapter.ViewHolder>() {

    private var asteroidData:List<NearEarthObject> = listOf()

    fun setAsteroids(data:List<NearEarthObject>){
        this.asteroidData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_asteroid_recycler_view_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.asteroidData[position])
    }

    override fun getItemCount(): Int {
        return asteroidData.size
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(nearEarthObject: NearEarthObject){
            FragmentAsteroidRecyclerViewItemBinding.bind(itemView).run{
                asteroidName.text = nearEarthObject.name
                root.setOnClickListener{ listener.onItemClick(nearEarthObject) }
            }
        }
    }
}
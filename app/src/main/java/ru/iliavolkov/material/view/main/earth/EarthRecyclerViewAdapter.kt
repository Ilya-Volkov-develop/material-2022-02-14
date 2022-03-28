package ru.iliavolkov.material.view.main.earth

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentEarthRecyclerViewItemBinding
import ru.iliavolkov.material.model.Earth

class EarthRecyclerViewAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<EarthRecyclerViewAdapter.ViewHolder>() {

    private var earthData:List<Earth> = listOf()
    private var data:List<String> = listOf()

    fun setEarthData(earthList:List<Earth>,data:List<String>){
        this.earthData = earthList
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_earth_recycler_view_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.earthData[position],this.data)
    }

    override fun getItemCount(): Int {
        return earthData.size
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(earth: Earth, data: List<String>){
            FragmentEarthRecyclerViewItemBinding.bind(itemView).run {
                date.text = earth.date
                earthImage.load("https://epic.gsfc.nasa.gov/archive/natural/${data[0]}/${data[1]}/${data[2]}/png/${earth.image}.png"){
                    placeholder(R.drawable.progress_animation)
                    error(R.drawable.ic_load_error)
                }
                root.setOnClickListener{ listener.onItemClick("https://epic.gsfc.nasa.gov/archive/natural/${data[0]}/${data[1]}/${data[2]}/png/${earth.image}.png") }
            }
        }
    }
}
package ru.iliavolkov.material.view.main.asteroid

import android.annotation.SuppressLint
import android.graphics.Color.LTGRAY
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentAsteroidRecyclerViewItemBinding
import ru.iliavolkov.material.model.NearEarthObject
import ru.iliavolkov.material.utils.ITEM_CLOSE
import ru.iliavolkov.material.utils.ITEM_OPEN

@Suppress("DEPRECATION")
class AsteroidRecyclerViewAdapter(
        private val onStartDragListener:OnStartDragListener
): RecyclerView.Adapter<AsteroidRecyclerViewAdapter.ViewHolder>(),ItemTouchHelperAdapter {

    private var asteroidData:MutableList<Pair<Int,NearEarthObject>> = mutableListOf()

    fun setAsteroids(data:List<Pair<Int,NearEarthObject>>){
        this.asteroidData = data as MutableList<Pair<Int, NearEarthObject>>
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

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view),ItemTouchHelperViewAdapter{
        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun bind(data:Pair<Int,NearEarthObject>){
            FragmentAsteroidRecyclerViewItemBinding.bind(itemView).run{
                asteroidName.text = data.second.name
                date.text = "Дата приближения: ${data.second.closeApproachData[0].closeApproachDateFull}"
                radius.text = "Радиус: max = ${data.second.estimatedDiameter.kilometers.estimatedDiameterMax} км\nmin = ${data.second.estimatedDiameter.kilometers.estimatedDiameterMin} км"
                speed.text = "Скорость: ${data.second.closeApproachData[0].relativeVelocity.kilometersPerHour} км/час"
                orbitalBody.text = "Орбитальное тело ${data.second.closeApproachData[0].orbitingBody}"
                iconRemove.setOnClickListener { removeItem() }
                root.setOnClickListener {
                    asteroidData[layoutPosition] = asteroidData[layoutPosition].let{
                        val currentState = if(it.first== ITEM_CLOSE) ITEM_OPEN else  ITEM_CLOSE
                        Pair(currentState,it.second)
                    }
                    notifyItemChanged(layoutPosition)
                }
                attrContainer.visibility = if(data.first == ITEM_CLOSE) View.GONE else View.VISIBLE
                moreInfo.visibility = if(data.first == ITEM_OPEN) View.GONE else View.VISIBLE
                iconMove.setOnTouchListener { _, event ->
                    if(MotionEventCompat.getActionMasked(event)== MotionEvent.ACTION_DOWN){
                        onStartDragListener.onStartDrag(this@ViewHolder)
                    }
                    false
                }
            }
        }


        private fun removeItem() {
            asteroidData.removeAt(layoutPosition)
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
        asteroidData.removeAt(fromPosition).apply {
            asteroidData.add(toPosition,this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        asteroidData.removeAt(position)
        notifyItemRemoved(position)
    }
}
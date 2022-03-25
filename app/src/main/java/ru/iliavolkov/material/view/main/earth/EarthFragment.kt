package ru.iliavolkov.material.view.main.earth

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.clear
import coil.load
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentEarthBinding
import ru.iliavolkov.material.model.DateDTO
import ru.iliavolkov.material.viewmodel.EarthViewModel
import ru.iliavolkov.material.viewmodel.appstate.AppStateAllDate
import ru.iliavolkov.material.viewmodel.appstate.AppStateEarthImages
import java.lang.Exception
import java.text.SimpleDateFormat


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EarthFragment : Fragment(),OnItemClickListener {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EarthViewModel by lazy { ViewModelProvider(this).get(EarthViewModel::class.java) }
    private val adapter: EarthRecyclerViewAdapter by lazy { EarthRecyclerViewAdapter(this) }
    var minDate:Long? = null
    var maxDate:Long? = null
    val data = mutableListOf("","","")
    private var flag = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllDate()
        binding.earthRecycler.adapter = adapter
        clickCalendar()
        binding.earthBigPicture.setOnClickListener {
            if (flag){
                flag = !flag
                binding.earthBigPicture.clear()
                ObjectAnimator.ofFloat(binding.earthBigPicture,View.ALPHA,1f,0f).setDuration(500).start()
                ObjectAnimator.ofFloat(binding.backgroundContainerImage,View.ALPHA,1f,0f).setDuration(500).start()
                Thread.sleep(500).apply {
                    binding.backgroundContainerImage.visibility = View.GONE
                    binding.earthBigPicture.visibility = View.GONE
                }
            }
        }
    }

    private fun renderData(it: Any?) {
        when(it){
            is AppStateAllDate.Error -> {

            }
            is AppStateAllDate.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppStateAllDate.Success -> {
                binding.loadingLayout.visibility = View.GONE
                convertStringToDate(it.listAllDate)
            }
            is AppStateEarthImages.Error->{

            }
            is AppStateEarthImages.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppStateEarthImages.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter.setEarthData(it.listEarthImages,data)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertStringToDate(date: List<DateDTO>) {
        maxDate = SimpleDateFormat("yyyy-MM-dd").parse(date[0].date).time
        minDate = SimpleDateFormat("yyyy-MM-dd").parse(date[date.size-1].date).time
    }

    @SuppressLint("SetTextI18n")
    private fun clickCalendar() {
        binding.calendarEarth.setOnClickListener {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val calendarView: View = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_calendar, null)
            alertDialog.setView(calendarView)
            val dialog = alertDialog.create()
            val calendar:CalendarView = calendarView.findViewById(R.id.calendar_view)
            calendar.maxDate = maxDate!!
            calendar.minDate = minDate!!
            dialog.show()
            calendar.setOnDateChangeListener { _, year, month, day ->
                data[0] = convertData(year)
                data[1] = convertData(month+1)
                data[2] = convertData(day)
                binding.dateEarth.text = "Дата: ${convertData(day)}.${convertData(month+1)}.${convertData(year)}"
                viewModel.getImageOnDate(convertData(day),convertData(month+1),convertData(year))
                dialog.dismiss()
            }
        }
    }

    private fun convertData(date:Int):String {
        return when (date) {
            1 -> "01"
            2 -> "02"
            3 -> "03"
            4 -> "04"
            5 -> "05"
            6 -> "06"
            7 -> "07"
            8 -> "08"
            9 -> "09"
            else -> "$date"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(path: String) {
        flag = !flag
        if (flag) {
            binding.earthBigPicture.load(path)
            binding.backgroundContainerImage.visibility = View.VISIBLE
            binding.earthBigPicture.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.backgroundContainerImage, View.ALPHA, 0f, 1f).setDuration(500).start()
            ObjectAnimator.ofFloat(binding.earthBigPicture,View.ALPHA,0f,1f).setDuration(500).start()
        }
    }
}
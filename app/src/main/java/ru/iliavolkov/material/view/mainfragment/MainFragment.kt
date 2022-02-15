package ru.iliavolkov.material.view.mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.iliavolkov.material.databinding.FragmentMainBinding
import viewmodel.PictureOfTheDayViewModel
import viewmodel.appstate.AppStatePictureOfTheDay

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getPictureOfTheDay()
    }

    private fun renderData(it: AppStatePictureOfTheDay?) {
        when(it){
            is AppStatePictureOfTheDay.Error -> TODO()
            is AppStatePictureOfTheDay.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppStatePictureOfTheDay.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.customImageView.load(it.pictureData.url)
            }
            null -> TODO()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
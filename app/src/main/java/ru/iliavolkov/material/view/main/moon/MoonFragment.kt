package ru.iliavolkov.material.view.main.moon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentMoonBinding

class MoonFragment : Fragment() {

    private var _binding: FragmentMoonBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitleTextColor(requireContext().getColor(R.color.white))
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoonFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
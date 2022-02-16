package ru.iliavolkov.material.view.navigationDrawerFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.BottomNavigationLayoutBinding

class BottomNavigationDrawerFragment: BottomSheetDialogFragment() {
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menu->
            when(menu.itemId){
                R.id.navigation_one -> {
                    Toast.makeText(requireContext(), "navigation_one", Toast.LENGTH_SHORT).show()
                }
                R.id.navigation_two -> {
                    Toast.makeText(requireContext(), "navigation_two", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
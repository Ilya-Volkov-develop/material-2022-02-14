package ru.iliavolkov.material.view.main.asteroid

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import coil.load
import ru.iliavolkov.material.R
import ru.iliavolkov.material.databinding.FragmentAsteroidBinding
import ru.iliavolkov.material.model.NearEarthObject
import ru.iliavolkov.material.utils.ITEM_CLOSE
import ru.iliavolkov.material.viewmodel.AsteroidViewModel
import ru.iliavolkov.material.viewmodel.appstate.AppStateAsteroid


class AsteroidFragment : Fragment() {

    private var _binding: FragmentAsteroidBinding? = null
    private val binding: FragmentAsteroidBinding get() = _binding!!
    private val viewModel: AsteroidViewModel by lazy { ViewModelProvider(this).get(AsteroidViewModel::class.java) }
    lateinit var adapter:AsteroidRecyclerViewAdapter

    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAsteroidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAsteroids()
        adapter = AsteroidRecyclerViewAdapter { itemTouchHelper.startDrag(it) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.header.isSelected = binding.recyclerView.canScrollVertically(-1)
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }



    private fun renderData(it: AppStateAsteroid?) {
        when(it){
            is AppStateAsteroid.Error -> {
                if (it.code != 0) loadingFailed(it.error, it.code)
                else viewModel.getAsteroids()
            }
            is AppStateAsteroid.Loading -> {
                binding.loadingImage.load(R.drawable.progress_animation){
                    error(R.drawable.ic_load_error)
                }
            }
            is AppStateAsteroid.Success -> {
                binding.loadingImage.load(0)
                val asteroidData = mutableListOf<Pair<Int,NearEarthObject>>()
                it.asteroidData.nearEarthObjects.values.toList().forEach {
                    it.forEach {
                        asteroidData.add(Pair(ITEM_CLOSE,it))
                    }
                }
                adapter.setAsteroids(asteroidData)
            }
        }
    }

    //при ошибке всплывает диалоговое окно
    private fun loadingFailed(textId: Int, code: Int) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater? = LayoutInflater.from(requireContext())
        val exitView: View = inflater!!.inflate(R.layout.dialog_error, null)
        dialog.setView(exitView)
        val dialog1: Dialog? = dialog.create()
        val ok: Button = exitView.findViewById(R.id.ok)
        val codeTextView = exitView.findViewById<TextView>(R.id.textError)

        codeTextView.text = when(textId) {
            R.string.errorOnServer -> getString(R.string.errorOnServer)
            R.string.codeError -> getString(R.string.codeError) + " " + code
            else -> ""
        }
        dialog1?.setCancelable(false)
        ok.setOnClickListener {
            dialog1?.dismiss()
            viewModel.getAsteroids()
        }
        dialog1?.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AsteroidFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
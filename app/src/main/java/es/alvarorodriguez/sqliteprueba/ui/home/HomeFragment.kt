package es.alvarorodriguez.sqliteprueba.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import es.alvarorodriguez.sqliteprueba.R
import es.alvarorodriguez.sqliteprueba.core.Result
import es.alvarorodriguez.sqliteprueba.data.local.AppDatabase
import es.alvarorodriguez.sqliteprueba.data.local.LocalCarDataSource
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity
import es.alvarorodriguez.sqliteprueba.databinding.FragmentHomeBinding
import es.alvarorodriguez.sqliteprueba.presentation.CarViewModel
import es.alvarorodriguez.sqliteprueba.presentation.CarViewModelFactory
import es.alvarorodriguez.sqliteprueba.repository.CarRepositoryImpl
import es.alvarorodriguez.sqliteprueba.ui.home.adapter.CarAdapter

class HomeFragment : Fragment(R.layout.fragment_home), CarAdapter.OnCarClickListener {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<CarViewModel> {
        CarViewModelFactory(
            CarRepositoryImpl(
                LocalCarDataSource(AppDatabase.getDatabase(requireContext()).carDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        getCars()
        fab()
    }

    private fun fab() {
        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            Snackbar.make(view, "Pantalla para añadir un Coche", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun getCars() {
        viewModel.getCars().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvCars.adapter = CarAdapter(result.data, this@HomeFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "getCars: ${result.exception}")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDeleteCarClick(car: CarEntity) {
        deleteCar(car)
    }

    private fun deleteCar(car: CarEntity) {
        viewModel.deleteCarById(car.id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Delete car ${car.id}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.rvCars.adapter?.notifyItemRemoved(car.id)
                    findNavController().navigate(R.id.homeFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onUpdateCarClick(car: CarEntity) {
        val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
            car.id,
            car.marca,
            car.color,
            car.precio,
            car.description
        )
        findNavController().navigate(action)
    }
}
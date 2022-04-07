package es.alvarorodriguez.sqliteprueba.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.sqliteprueba.R
import es.alvarorodriguez.sqliteprueba.core.Result
import es.alvarorodriguez.sqliteprueba.data.local.AppDatabase
import es.alvarorodriguez.sqliteprueba.data.local.LocalCarDataSource
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity
import es.alvarorodriguez.sqliteprueba.databinding.FragmentAddBinding
import es.alvarorodriguez.sqliteprueba.presentation.CarViewModel
import es.alvarorodriguez.sqliteprueba.presentation.CarViewModelFactory
import es.alvarorodriguez.sqliteprueba.repository.CarRepositoryImpl
import java.util.*
import kotlin.random.Random

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding

    private val viewModel by viewModels<CarViewModel> {
        CarViewModelFactory(
            CarRepositoryImpl(
                LocalCarDataSource(AppDatabase.getDatabase(requireContext()).carDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)

        btnAdd()
    }

    private fun btnAdd() {
        binding.btnAdd.setOnClickListener {

            val marca = binding.marca.text.toString().trim()
            val precio = binding.precio.text.toString().trim()
            val color = binding.color.text.toString().trim()
            val description = binding.description.text.toString().trim()

            val numRandom = Math.random().toInt()

            val car =
                CarEntity(id = numRandom, marca = marca, precio = precio, color = color, description = description)

            saveCar(car)
        }
    }

    private fun saveCar(car: CarEntity) {
        viewModel.saveCar(car).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "Add Car", Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
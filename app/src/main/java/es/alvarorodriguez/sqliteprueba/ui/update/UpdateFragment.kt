package es.alvarorodriguez.sqliteprueba.ui.update

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.alvarorodriguez.sqliteprueba.R
import es.alvarorodriguez.sqliteprueba.core.Result
import es.alvarorodriguez.sqliteprueba.data.local.AppDatabase
import es.alvarorodriguez.sqliteprueba.data.local.LocalCarDataSource
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity
import es.alvarorodriguez.sqliteprueba.databinding.FragmentUpdateBinding
import es.alvarorodriguez.sqliteprueba.presentation.CarViewModel
import es.alvarorodriguez.sqliteprueba.presentation.CarViewModelFactory
import es.alvarorodriguez.sqliteprueba.repository.CarRepositoryImpl

class UpdateFragment : Fragment(R.layout.fragment_update)  {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()

    private val viewModel by viewModels<CarViewModel> {
        CarViewModelFactory(
            CarRepositoryImpl(
                LocalCarDataSource(AppDatabase.getDatabase(requireContext()).carDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)
        implementation()
    }

    private fun implementation() {
        binding.marca.setText(args.marca)
        binding.color.setText(args.color)
        binding.precio.setText(args.precio)
        binding.description.setText(args.description)

        binding.btnUpdate.setOnClickListener {

            val marca = binding.marca.text.toString().trim()
            val description = binding.description.text.toString().trim()
            val precio = binding.precio.text.toString().trim()
            val color = binding.color.text.toString().trim()

            val car = CarEntity(id = args.id, marca = marca, description = description,
                                precio = precio, color = color)

            updateCar(car)
        }
    }

    private fun updateCar(car: CarEntity) {
        viewModel.updateCar(car).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Data ${result.data}",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
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
}
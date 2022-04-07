package es.alvarorodriguez.sqliteprueba.ui.home.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.alvarorodriguez.sqliteprueba.core.BaseViewHolder
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity
import es.alvarorodriguez.sqliteprueba.databinding.CarItemBinding

class CarAdapter(private val carsList: List<CarEntity>,
                 private val itemClickListener: OnCarClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface  OnCarClickListener {
        fun onDeleteCarClick(car: CarEntity)
        fun onUpdateCarClick(car: CarEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = CarsViewHolder(itemBinding)

        updateCar(itemBinding, holder)

        itemBinding.delete.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onDeleteCarClick(carsList[position])
            Log.d("DATA", "onCreateViewHolder: $position")
        }

        return holder
    }

    private fun updateCar(
        itemBinding: CarItemBinding,
        holder: CarsViewHolder
    ) {
        itemBinding.txtPrecio.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onUpdateCarClick(carsList[position])
        }

        itemBinding.txtDescription.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onUpdateCarClick(carsList[position])
        }

        itemBinding.txtMarca.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onUpdateCarClick(carsList[position])
        }

        itemBinding.txtColor.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onUpdateCarClick(carsList[position])
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CarsViewHolder -> holder.bind(carsList[position])
        }
    }

    override fun getItemCount(): Int = carsList.size

    private inner class CarsViewHolder(val binding: CarItemBinding) :
        BaseViewHolder<CarEntity>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: CarEntity) {
            binding.txtMarca.text = item.marca
            binding.txtDescription.text = item.description
            binding.txtPrecio.text = "Price: ${item.precio} €"
            binding.txtColor.text = "Es de color ${item.color.lowercase()}"
        }
    }
}
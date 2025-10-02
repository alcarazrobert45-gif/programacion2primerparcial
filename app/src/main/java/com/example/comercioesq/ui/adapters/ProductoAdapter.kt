package com.example.comercioesq.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comercioesq.R // Importa R para acceder a los recursos (como el string)
import com.example.comercioesq.databinding.ItemProductoBinding // REQUISITO 4: ViewBinding
import com.example.comercioesq.model.Producto
import java.util.Locale

class ProductoAdapter(private val listener: OnItemInteractionListener) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    private var productosList: List<Producto> = emptyList()

    // Interfaz para manejar clics desde el Fragment (Edición y Eliminación)
    interface OnItemInteractionListener {
        fun onEditClick(producto: Producto)
        fun onDeleteClick(producto: Producto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        // Usamos ViewBinding para inflar el layout de la fila
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productosList[position])
    }

    override fun getItemCount(): Int = productosList.size

    // Función para actualizar la lista desde el ViewModel (LiveData)
    fun setProductos(nuevosProductos: List<Producto>) {
        this.productosList = nuevosProductos
        notifyDataSetChanged()
    }

    // Clase ViewHolder
    class ProductoViewHolder(
        private val binding: ItemProductoBinding,
        listener: OnItemInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentProducto: Producto? = null

        init {
            // Listener para Edición (Botón)
            binding.btnEditProducto.setOnClickListener {
                currentProducto?.let {
                    listener.onEditClick(it)
                }
            }

            // Listener para Eliminación (Long Click en toda la fila)
            binding.root.setOnLongClickListener {
                currentProducto?.let {
                    listener.onDeleteClick(it)
                    true
                } ?: false
            }
        }

        fun bind(producto: Producto) {
            currentProducto = producto

            // Asignación de datos usando ViewBinding
            binding.tvNombreProducto.text = producto.nombre
            binding.tvPrecioProducto.text = String.format(Locale.getDefault(), "$%.2f", producto.precio)
            // Uso del String resource que creaste
            binding.tvStockId.text = itemView.context.getString(
                R.string.stock_id_format,
                producto.stock,
                producto.id
            )
        }
    }
}
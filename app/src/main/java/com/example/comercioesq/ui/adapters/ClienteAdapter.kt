package com.example.comercioesq.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comercioesq.databinding.ItemClienteBinding // ViewBinding
import com.example.comercioesq.model.Cliente

class ClienteAdapter(private val listener: OnItemInteractionListener) :
    RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    private var clientesList: List<Cliente> = emptyList()

    // Interfaz para manejar clics desde el Fragment
    interface OnItemInteractionListener {
        fun onEditClick(cliente: Cliente)
        fun onDeleteClick(cliente: Cliente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        // Usamos ViewBinding para inflar el layout de la fila
        val binding = ItemClienteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClienteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        holder.bind(clientesList[position])
    }

    override fun getItemCount(): Int = clientesList.size

    // Función para actualizar la lista desde el ViewModel
    fun setClientes(nuevosClientes: List<Cliente>) {
        this.clientesList = nuevosClientes
        notifyDataSetChanged()
    }

    // Clase ViewHolder
    class ClienteViewHolder(
        private val binding: ItemClienteBinding,
        listener: OnItemInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentCliente: Cliente? = null

        init {
            // Listener para Edición (Botón)
            binding.btnEditCliente.setOnClickListener {
                currentCliente?.let {
                    listener.onEditClick(it)
                }
            }

            // Listener para Eliminación (Long Click en toda la fila)
            binding.root.setOnLongClickListener {
                currentCliente?.let {
                    listener.onDeleteClick(it)
                    true
                } ?: false
            }
        }

        fun bind(cliente: Cliente) {
            currentCliente = cliente

            // Asignación de datos usando ViewBinding
            binding.tvNombreCliente.text = cliente.nombre
            binding.tvContactoCliente.text = "Teléfono: ${cliente.telefono}"
            binding.tvIdCliente.text = "ID: ${cliente.id}"
        }
    }
}
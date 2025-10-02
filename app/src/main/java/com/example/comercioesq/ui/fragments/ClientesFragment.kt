package com.example.comercioesq.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comercioesq.databinding.FragmentClientesBinding // ViewBinding
import com.example.comercioesq.model.Cliente
import com.example.comercioesq.ui.adapters.ClienteAdapter
import com.example.comercioesq.viewmodel.ClienteViewModel

class ClientesFragment : Fragment(), ClienteAdapter.OnItemInteractionListener {

    private var _binding: FragmentClientesBinding? = null
    private val binding get() = _binding!! // ViewBinding

    private lateinit var viewModel: ClienteViewModel
    private lateinit var adapter: ClienteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar con ViewBinding
        _binding = FragmentClientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel (asumiendo que ya creaste ClienteViewModel)
        viewModel = ViewModelProvider(this)[ClienteViewModel::class.java]

        // Inicializar Adapter y RecyclerView
        adapter = ClienteAdapter(this)
        binding.recyclerClientes.layoutManager = LinearLayoutManager(context)
        binding.recyclerClientes.adapter = adapter

        // Observar LiveData para actualizar la lista
        viewModel.clientes.observe(viewLifecycleOwner) { clientes ->
            adapter.setClientes(clientes)
        }

        // Configurar el botón FAB para agregar
        binding.fabAddCliente.setOnClickListener {
            Toast.makeText(context, "Navegar a Agregar Cliente (Fase 6)", Toast.LENGTH_SHORT).show()
        }
    }

    // --- Implementación de CRUD (Interfaz del Adapter) ---

    override fun onEditClick(cliente: Cliente) {
        Toast.makeText(context, "Editar ID Cliente: ${cliente.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(cliente: Cliente) {
        // Asumiendo que existe el método deleteCliente en ClienteViewModel
        viewModel.deleteCliente(cliente)
        Toast.makeText(context, "Cliente eliminado: ${cliente.nombre}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Liberar la referencia de ViewBinding
    }
}
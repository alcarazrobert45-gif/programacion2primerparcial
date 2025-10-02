package com.example.comercioesq.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController // Importación clave
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comercioesq.databinding.FragmentProductosBinding
import com.example.comercioesq.model.Producto
import com.example.comercioesq.ui.adapters.ProductoAdapter
import com.example.comercioesq.viewmodel.ProductoViewModel

class ProductosFragment : Fragment(), ProductoAdapter.OnItemInteractionListener {

    private var _binding: FragmentProductosBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductoViewModel
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        // Inicializar Adapter y RecyclerView
        adapter = ProductoAdapter(this)
        binding.recyclerProductos.layoutManager = LinearLayoutManager(context)
        binding.recyclerProductos.adapter = adapter

        // Observar LiveData para actualizar la lista
        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.setProductos(productos)
        }

        // CONEXIÓN CLAVE: FAB para AGREGAR (Alta)
        binding.fabAddProducto.setOnClickListener {
            // Navega al formulario sin argumentos de edición (usa los valores por defecto del nav_graph)
            val action = ProductosFragmentDirections.actionProductosFragmentToProductoFormFragment(
                productoId = -1L,
                title = "Agregar Nuevo Producto"
            )
            findNavController().navigate(action)
        }
    }

    // --- Implementación de CRUD (Interfaz del Adapter) ---

    // CONEXIÓN CLAVE: EDITAR
    override fun onEditClick(producto: Producto) {
        // Navega al formulario enviando el ID del producto (Edición)
        val action = ProductosFragmentDirections.actionProductosFragmentToProductoFormFragment(
            productoId = producto.id,
            title = "Editar Producto: ${producto.nombre}"
        )
        findNavController().navigate(action)
    }

    // BAJA (DELETE)
    override fun onDeleteClick(producto: Producto) {
        // Lógica de eliminación (ya implementada)
        viewModel.deleteProducto(producto)
        Toast.makeText(context, "Producto eliminado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
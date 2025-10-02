package com.example.comercioesq.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.comercioesq.databinding.FragmentProductoFormBinding
import com.example.comercioesq.model.Producto
import com.example.comercioesq.viewmodel.ProductoViewModel

class ProductoFormFragment : Fragment() {

    private var _binding: FragmentProductoFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductoViewModel
    private var productoId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        // LÓGICA CLAVE 1: Leer Argumentos de Edición
        arguments?.let { bundle ->
            // Usamos el ID pasado desde el nav_graph (por defecto es -1L si es Alta)
            productoId = bundle.getLong("productoId", -1L).takeIf { it != -1L }

            // Si el ID existe, estamos en modo EDICIÓN
            productoId?.let { id ->
                cargarDatosProducto(id)
            }
        }

        binding.btnGuardarProducto.setOnClickListener {
            guardarProducto()
        }
    }

    // LÓGICA CLAVE 2: Función para precargar los datos del producto existente
    private fun cargarDatosProducto(id: Long) {
        // Buscamos el producto en la lista del ViewModel
        // NOTA: Usamos .value para acceder a los datos de LiveData fuera de un observer.
        val producto = viewModel.productos.value?.find { it.id == id }

        producto?.let { p ->
            // Rellenar los campos con los datos existentes
            binding.etNombre.setText(p.nombre)
            // Usamos String.format para asegurar un formato de precio limpio
            binding.etPrecio.setText(String.format("%.2f", p.precio))
            binding.etStock.setText(p.stock.toString())

            // Actualizar el título del formulario
            binding.tvTitleForm.text = "Editar Producto: ${p.nombre}"
        } ?: run {
            // Si no se encuentra el producto, volvemos a modo Alta
            productoId = null
            Toast.makeText(context, "Error: Producto no encontrado para editar.", Toast.LENGTH_LONG).show()
        }
    }


    private fun guardarProducto() {
        // Validación de datos
        val nombre = binding.etNombre.text.toString()
        val precioText = binding.etPrecio.text.toString()
        val stockText = binding.etStock.text.toString()

        if (nombre.isBlank() || precioText.isBlank() || stockText.isBlank()) {
            Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val precio = precioText.toDoubleOrNull() ?: 0.0
        val stock = stockText.toIntOrNull() ?: 0

        val nuevoProducto: Producto

        if (productoId == null) {
            // ALTA (Create)
            nuevoProducto = Producto(0, nombre, precio, stock)
            viewModel.addProducto(nuevoProducto)
            Toast.makeText(context, "Producto agregado: $nombre", Toast.LENGTH_SHORT).show()
        } else {
            // EDICIÓN (Update)
            nuevoProducto = Producto(productoId!!, nombre, precio, stock)
            viewModel.updateProducto(nuevoProducto)
            Toast.makeText(context, "Producto editado: $nombre", Toast.LENGTH_SHORT).show()
        }

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
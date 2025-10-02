package com.example.comercioesq.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
// Importación del ViewBinding (asumiendo que el layout se llama fragment_pedidos)
import com.example.comercioesq.databinding.FragmentPedidosBinding

class PedidosFragment : Fragment() {

    // Variable para manejar el ViewBinding
    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando ViewBinding
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Es importante liberar la referencia del binding para evitar fugas de memoria
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // El resto de la lógica (ViewModel, Adapter) iría aquí en la Fase 7
}
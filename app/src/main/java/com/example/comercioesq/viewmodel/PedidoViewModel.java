package com.example.comercioesq.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.comercioesq.data.InMemoryRepository;
import com.example.comercioesq.model.Pedido;
import java.util.List;

public class PedidoViewModel extends AndroidViewModel {
    private final InMemoryRepository repository;
    private final MutableLiveData<List<Pedido>> pedidosLiveData = new MutableLiveData<>();

    public PedidoViewModel(Application application) {
        super(application);
        repository = InMemoryRepository.getInstance(application);
        loadPedidos();
    }

    public LiveData<List<Pedido>> getPedidos() {
        return pedidosLiveData;
    }

    public void loadPedidos() {
        pedidosLiveData.setValue(repository.getPedidos());
    }
}
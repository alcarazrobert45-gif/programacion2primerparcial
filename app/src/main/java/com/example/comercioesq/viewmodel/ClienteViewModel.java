package com.example.comercioesq.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.comercioesq.data.InMemoryRepository;
import com.example.comercioesq.model.Cliente;
import java.util.List;

public class ClienteViewModel extends AndroidViewModel {
    private final InMemoryRepository repository;
    private final MutableLiveData<List<Cliente>> clientesLiveData = new MutableLiveData<>();

    public ClienteViewModel(Application application) {
        super(application);
        repository = InMemoryRepository.getInstance(application);
        loadClientes();
    }

    public LiveData<List<Cliente>> getClientes() {
        return clientesLiveData;
    }

    public void loadClientes() {
        clientesLiveData.setValue(repository.getClientes());
    }

    public void addCliente(Cliente cliente) {
        repository.addCliente(cliente);
        loadClientes();
    }

    public void updateCliente(Cliente cliente) {
        repository.updateCliente(cliente);
        loadClientes();
    }

    public void deleteCliente(Cliente cliente) {
        repository.deleteCliente(cliente);
        loadClientes();
    }
}
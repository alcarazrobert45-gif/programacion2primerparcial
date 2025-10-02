package com.example.comercioesq.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.comercioesq.data.InMemoryRepository;
import com.example.comercioesq.model.Producto;
import java.util.List;

public class ProductoViewModel extends AndroidViewModel {
    private final InMemoryRepository repository;
    private final MutableLiveData<List<Producto>> productosLiveData = new MutableLiveData<>();

    public ProductoViewModel(Application application) {
        super(application);
        repository = InMemoryRepository.getInstance(application);
        loadProductos();
    }

    public LiveData<List<Producto>> getProductos() {
        return productosLiveData;
    }

    public void loadProductos() {
        // Cargar datos y actualizar LiveData para notificar a la UI
        productosLiveData.setValue(repository.getProductos());
    }

    public void addProducto(Producto producto) {
        repository.addProducto(producto);
        loadProductos(); // Recargar para notificar la UI
    }

    public void updateProducto(Producto producto) {
        repository.updateProducto(producto);
        loadProductos(); // Recargar para notificar la UI
    }

    public void deleteProducto(Producto producto) {
        repository.deleteProducto(producto);
        loadProductos(); // Recargar para notificar la UI
    }
}
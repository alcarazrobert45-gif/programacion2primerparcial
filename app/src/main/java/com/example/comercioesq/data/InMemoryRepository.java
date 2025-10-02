package com.example.comercioesq.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.comercioesq.model.Cliente;
import com.example.comercioesq.model.DetallePedido; // NUEVO IMPORT
import com.example.comercioesq.model.Pedido; // NUEVO IMPORT
import com.example.comercioesq.model.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date; // NUEVO IMPORT
import java.util.List;

public class InMemoryRepository {
    private static final String PREFS_NAME = "EcommercePrefs";
    private static final String KEY_PRODUCTOS = "productos";
    private static final String KEY_CLIENTES = "clientes";

    // NUEVAS CLAVES DE PERSISTENCIA
    private static final String KEY_PEDIDOS = "pedidos";
    private static final String KEY_DETALLES = "detalles";

    // Instancia Singleton
    private static InMemoryRepository instance;

    // Listas en memoria para simular las "tablas"
    private List<Producto> productosList;
    private List<Cliente> clientesList;

    // NUEVAS LISTAS
    private List<Pedido> pedidosList;
    private List<DetallePedido> detallesList;

    private SharedPreferences sharedPrefs;
    private Gson gson;

    // Constructor privado para el Singleton
    private InMemoryRepository(Context context) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadData();
    }

    // Método de acceso al Singleton
    public static synchronized InMemoryRepository getInstance(Context context) {
        if (instance == null) {
            instance = new InMemoryRepository(context.getApplicationContext());
        }
        return instance;
    }

    // --- Métodos de Persistencia con SharedPreferences (JSON) ---

    private void loadData() {
        // Cargar Productos
        Type typeProducto = new TypeToken<ArrayList<Producto>>() {}.getType();
        String jsonProductos = sharedPrefs.getString(KEY_PRODUCTOS, null);
        productosList = jsonProductos != null ? gson.fromJson(jsonProductos, typeProducto) : new ArrayList<>();

        // Cargar Clientes
        Type typeCliente = new TypeToken<ArrayList<Cliente>>() {}.getType();
        String jsonClientes = sharedPrefs.getString(KEY_CLIENTES, null);
        clientesList = jsonClientes != null ? gson.fromJson(jsonClientes, typeCliente) : new ArrayList<>();

        // NUEVA CARGA: Pedidos
        Type typePedido = new TypeToken<ArrayList<Pedido>>() {}.getType();
        String jsonPedidos = sharedPrefs.getString(KEY_PEDIDOS, null);
        pedidosList = jsonPedidos != null ? gson.fromJson(jsonPedidos, typePedido) : new ArrayList<>();

        // NUEVA CARGA: Detalles de Pedido
        Type typeDetalle = new TypeToken<ArrayList<DetallePedido>>() {}.getType();
        String jsonDetalles = sharedPrefs.getString(KEY_DETALLES, null);
        detallesList = jsonDetalles != null ? gson.fromJson(jsonDetalles, typeDetalle) : new ArrayList<>();


        // --- Datos de Prueba ---
        if (productosList.isEmpty()) {
            productosList.add(new Producto(1, "Laptop", 1200.00, 50));
            productosList.add(new Producto(2, "Monitor 27\"", 350.50, 80));
            productosList.add(new Producto(3, "Teclado Mecánico", 95.00, 120));
        }

        if (clientesList.isEmpty()) {
            // Los clientes ahora requieren 5 argumentos: (id, nombre, email, direccion, telefono)
            clientesList.add(new Cliente(1, "Juan Perez", "juan@mail.com", "Av. Principal 101", "555-1234"));
            clientesList.add(new Cliente(2, "Ana Garcia", "ana@mail.com", "Calle Secundaria 202", "555-5678"));
            clientesList.add(new Cliente(3, "Carlos Ruiz", "carlos@mail.com", "Urb. El Sol 303", "555-9012"));
        }

        // NUEVOS DATOS DE PRUEBA: Pedidos y Detalles
        if (pedidosList.isEmpty() && !clientesList.isEmpty() && !productosList.isEmpty()) {
            // Detalle 1: 2x Laptop
            DetallePedido d1 = new DetallePedido(1001, 1, productosList.get(0).getId(), productosList.get(0).getNombre(), 2, productosList.get(0).getPrecio());
            // Detalle 2: 1x Monitor
            DetallePedido d2 = new DetallePedido(1002, 1, productosList.get(1).getId(), productosList.get(1).getNombre(), 1, productosList.get(1).getPrecio());
            detallesList.addAll(Arrays.asList(d1, d2));

            // Pedido 1: Cliente 1
            double totalP1 = d1.getSubtotal() + d2.getSubtotal();
            Pedido p1 = new Pedido(1, clientesList.get(0).getId(), new Date(), totalP1, Arrays.asList(d1, d2));
            pedidosList.add(p1);
        }
    }

    private void saveData() {
        // Guardar Productos y Clientes (Existentes)
        sharedPrefs.edit().putString(KEY_PRODUCTOS, gson.toJson(productosList)).apply();
        sharedPrefs.edit().putString(KEY_CLIENTES, gson.toJson(clientesList)).apply();

        // NUEVOS GUARDADOS
        sharedPrefs.edit().putString(KEY_PEDIDOS, gson.toJson(pedidosList)).apply();
        sharedPrefs.edit().putString(KEY_DETALLES, gson.toJson(detallesList)).apply();
    }

    // --- Métodos CRUD para Producto (Sin cambios) ---

    public List<Producto> getProductos() { return productosList; }

    public void addProducto(Producto p) {
        // ID simple: usamos el ID más alto + 1, o 1 si está vacío.
        long newId = productosList.stream().mapToLong(Producto::getId).max().orElse(0L) + 1;
        p.setId(newId);
        productosList.add(p);
        saveData();
    }

    public void updateProducto(Producto p) {
        for (int i = 0; i < productosList.size(); i++) {
            if (productosList.get(i).getId() == p.getId()) {
                productosList.set(i, p);
                break;
            }
        }
        saveData();
    }

    public void deleteProducto(Producto p) {
        productosList.removeIf(producto -> producto.getId() == p.getId());
        saveData();
    }

    // --- Métodos CRUD para Cliente (Con cambios menores en la lógica) ---

    public List<Cliente> getClientes() { return clientesList; }

    public void addCliente(Cliente c) {
        long newId = clientesList.stream().mapToLong(Cliente::getId).max().orElse(0L) + 1;
        c.setId(newId);
        clientesList.add(c);
        saveData();
    }

    public void updateCliente(Cliente c) {
        for (int i = 0; i < clientesList.size(); i++) {
            if (clientesList.get(i).getId() == c.getId()) {
                clientesList.set(i, c);
                break;
            }
        }
        saveData();
    }

    public void deleteCliente(Cliente c) {
        clientesList.removeIf(cliente -> cliente.getId() == c.getId());
        saveData();
    }

    // --- Métodos CRUD para Pedido y DetallePedido (Solo Read/Listado) ---

    public List<Pedido> getPedidos() {
        return pedidosList;
    }

    // Método para obtener los detalles específicos de un pedido
    public List<DetallePedido> getDetallesPorPedido(long pedidoId) {
        List<DetallePedido> result = new ArrayList<>();
        for (DetallePedido detalle : detallesList) {
            if (detalle.getPedidoId() == pedidoId) {
                result.add(detalle);
            }
        }
        return result;
    }
}
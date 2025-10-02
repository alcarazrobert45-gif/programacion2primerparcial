package com.example.comercioesq.model;

public class DetallePedido {
    private long id;
    private long pedidoId; // Relación con Pedido
    private long productoId; // Relación con Producto
    private String nombreProducto; // Campo adicional para mostrar en la UI
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Constructor
    public DetallePedido(long id, long pedidoId, long productoId, String nombreProducto, int cantidad, double precioUnitario) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    // Constructor vacío (necesario para Gson)
    public DetallePedido() {}

    // Getters y Setters
    public long getId() { return id; }
    public long getPedidoId() { return pedidoId; }
    public long getProductoId() { return productoId; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
    // Omitiendo Setters para brevedad, pero puedes incluirlos.
}
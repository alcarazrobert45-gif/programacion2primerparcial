package com.example.comercioesq.model;


import java.util.Date;
import java.util.List;

public class Pedido {
    private long id;
    private long clienteId; // Relación 1:N con Cliente
    private Date fecha;
    private double total;
    private List<DetallePedido> detalles; // Lista de DetallePedido para esta orden

    // Constructor completo
    public Pedido(long id, long clienteId, Date fecha, double total, List<DetallePedido> detalles) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.detalles = detalles;
    }

    // Constructor vacío (necesario para Gson)
    public Pedido() {
        this.fecha = new Date();
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getClienteId() { return clienteId; }
    public void setClienteId(long clienteId) { this.clienteId = clienteId; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}
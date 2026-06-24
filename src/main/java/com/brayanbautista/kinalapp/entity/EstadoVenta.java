package com.brayanbautista.kinalapp.entity;

public enum EstadoVenta {
    PAGADO("Pagado"),
    ENVIADO("Enviado"),
    PEDIDO("Pedido"),
    ENTREGADO("Entregado"),
    DEVOLUCION("Devolución"),
    CANCELADO("Cancelado"),
    INVENTARIO("Inventario");

    private final String etiqueta;

    EstadoVenta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}

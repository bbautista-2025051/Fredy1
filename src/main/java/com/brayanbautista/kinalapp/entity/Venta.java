package com.brayanbautista.kinalapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import com.brayanbautista.kinalapp.util.RouteEncryptionUtil;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private Long codigoVenta;

    @Column(name = "fecha_venta", nullable = false)
    private long fechaVenta;

    // Opcional
    @Column(name = "numero_guia", length = 100)
    private String numeroGuia;

    @Column(name = "direccion_envio", length = 255)
    private String direccionEnvio;

    // Opcional
    @Column(name = "costo_envio", precision = 10, scale = 2)
    private BigDecimal costoEnvio;

    @Column(name = "telefono_referencia", length = 20)
    private String telefonoReferencia;

    @Column(name = "costo", precision = 10, scale = 2, nullable = false)
    private BigDecimal costo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estilo", nullable = false, length = 30)
    private EstiloZapato estilo;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false, length = 20)
    private ColorZapato color;

    @Column(name = "numero_zapato", nullable = false)
    private int numeroZapato;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoVenta estado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;

    public Venta() {}

    public Long getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(Long codigoVenta) { this.codigoVenta = codigoVenta; }
    public long getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(long fechaVenta) { this.fechaVenta = fechaVenta; }
    public String getNumeroGuia() { return numeroGuia; }
    public void setNumeroGuia(String numeroGuia) { this.numeroGuia = numeroGuia; }
    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }
    public BigDecimal getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(BigDecimal costoEnvio) { this.costoEnvio = costoEnvio; }
    public String getTelefonoReferencia() { return telefonoReferencia; }
    public void setTelefonoReferencia(String telefonoReferencia) { this.telefonoReferencia = telefonoReferencia; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public EstiloZapato getEstilo() { return estilo; }
    public void setEstilo(EstiloZapato estilo) { this.estilo = estilo; }
    public ColorZapato getColor() { return color; }
    public void setColor(ColorZapato color) { this.color = color; }
    public int getNumeroZapato() { return numeroZapato; }
    public void setNumeroZapato(int numeroZapato) { this.numeroZapato = numeroZapato; }
    public EstadoVenta getEstado() { return estado; }
    public void setEstado(EstadoVenta estado) { this.estado = estado; }
    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }

    public String getEncryptedId() {
        return RouteEncryptionUtil.encryptLong(this.codigoVenta);
    }

    public java.util.Date getFechaVentaDate() {
        return new java.util.Date(this.fechaVenta);
    }

    // Zona horaria fija de Guatemala (UTC-6), independiente del timezone del servidor.
    private static final java.time.ZoneId ZONA_GT = java.time.ZoneId.of("America/Guatemala");

    // Usar este getter en las vistas (Thymeleaf #temporals) para mostrar la hora
    // correcta de Guatemala sin importar en qué timezone corra el servidor (Railway = UTC).
    public java.time.ZonedDateTime getFechaVentaGt() {
        return java.time.Instant.ofEpochMilli(this.fechaVenta).atZone(ZONA_GT);
    }

    public BigDecimal getTotal() {
        BigDecimal envio = (costoEnvio != null) ? costoEnvio : BigDecimal.ZERO;
        BigDecimal base  = (costo != null) ? costo : BigDecimal.ZERO;
        return base.add(envio);
    }
}

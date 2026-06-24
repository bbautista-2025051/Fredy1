package com.brayanbautista.kinalapp.service;

import com.brayanbautista.kinalapp.entity.EstadoVenta;
import com.brayanbautista.kinalapp.entity.Venta;
import com.brayanbautista.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        if (venta.getFechaVenta() <= 0) venta.setFechaVenta(System.currentTimeMillis());
        if (venta.getEstado() == null) venta.setEstado(EstadoVenta.PAGADO);
        return ventaRepository.save(venta);
    }

    @Override
    public Venta actualizar(Long id, Venta venta) {
        if (!ventaRepository.existsById(id)) throw new RuntimeException("Venta no encontrada: " + id);
        venta.setCodigoVenta(id);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override @Transactional(readOnly = true)
    public List<Venta> listarTodos() { return ventaRepository.findAll(); }

    @Override @Transactional(readOnly = true)
    public Optional<Venta> buscarPorId(Long id) { return ventaRepository.findById(id); }

    @Override
    public void eliminar(Long id) { ventaRepository.deleteById(id); }

    @Override
    public boolean existePorId(Long id) { return ventaRepository.existsById(id); }

    @Override
    public List<Venta> obtenerPorEstado(EstadoVenta estado) { return ventaRepository.findByEstado(estado); }

    @Override @Transactional(readOnly = true)
    public List<Venta> obtenerPorRangoFechas(long inicio, long fin) {
        if (inicio > fin) throw new IllegalArgumentException("Fecha inicio > fin");
        return ventaRepository.findByFechaVentaBetween(inicio, fin);
    }

    private void validarVenta(Venta venta) {
        if (venta.getEstilo() == null) throw new IllegalArgumentException("El estilo es obligatorio");
        if (venta.getNumeroZapato() < 34 || venta.getNumeroZapato() > 39)
            throw new IllegalArgumentException("Número de zapato debe ser 34-39");
        if (venta.getCosto() == null) throw new IllegalArgumentException("El costo es obligatorio");
    }
}

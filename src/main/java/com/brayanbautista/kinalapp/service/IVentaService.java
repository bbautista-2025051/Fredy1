package com.brayanbautista.kinalapp.service;

import com.brayanbautista.kinalapp.entity.EstadoVenta;
import com.brayanbautista.kinalapp.entity.Venta;
import java.util.List;
import java.util.Optional;

public interface IVentaService {
    Venta guardar(Venta venta);
    Venta actualizar(Long id, Venta venta);
    List<Venta> listarTodos();
    Optional<Venta> buscarPorId(Long id);
    void eliminar(Long id);
    boolean existePorId(Long id);
    List<Venta> obtenerPorEstado(EstadoVenta estado);
    List<Venta> obtenerPorRangoFechas(long inicio, long fin);
}

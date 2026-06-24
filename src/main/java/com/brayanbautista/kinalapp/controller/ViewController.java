package com.brayanbautista.kinalapp.controller;

import com.brayanbautista.kinalapp.entity.Venta;
import com.brayanbautista.kinalapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final IProductoService productoService;
    private final IVentaService ventaService;
    private final IDetalleVentaService detalleVentaService;

    public ViewController(IProductoService productoService,
                          IVentaService ventaService,
                          IDetalleVentaService detalleVentaService) {
        this.productoService     = productoService;
        this.ventaService        = ventaService;
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        int productosCount   = productoService.listarTodos().size();
        int productosActivos = productoService.obtenerPorEstado(1).size();

        model.addAttribute("productosCount",   productosCount);
        model.addAttribute("productosActivos", productosActivos);

        int ventasCount     = ventaService.listarTodos().size();
        int detallesCount   = detalleVentaService.listarTodos().size();

        List<Venta> ventas = ventaService.listarTodos();
        List<Venta> ventasRecientes = ventas.stream()
                .sorted(Comparator.comparing(Venta::getFechaVenta).reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("ventasCount",     ventasCount);
        model.addAttribute("detallesCount",   detallesCount);
        model.addAttribute("ventasRecientes", ventasRecientes);

        return "index";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }
}

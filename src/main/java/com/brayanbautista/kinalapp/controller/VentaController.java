package com.brayanbautista.kinalapp.controller;

import com.brayanbautista.kinalapp.entity.*;
import com.brayanbautista.kinalapp.service.IVentaService;
import com.brayanbautista.kinalapp.util.RouteEncryptionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        return "ventas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("estilos", EstiloZapato.values());
        model.addAttribute("colores", ColorZapato.values());
        model.addAttribute("estados", EstadoVenta.values());
        model.addAttribute("tallasZapato", new int[]{34, 35, 36, 37, 38, 39});
        return "ventas/formulario";
    }

    @PostMapping
    public String guardar(
            @RequestParam(required = false) String numeroGuia,
            @RequestParam String direccionEnvio,
            @RequestParam(required = false) BigDecimal costoEnvio,
            @RequestParam String telefonoReferencia,
            @RequestParam BigDecimal costo,
            @RequestParam EstiloZapato estilo,
            @RequestParam ColorZapato color,
            @RequestParam int numeroZapato,
            @RequestParam EstadoVenta estado,
            RedirectAttributes ra) {
        try {
            Venta venta = new Venta();
            venta.setFechaVenta(System.currentTimeMillis());
            venta.setNumeroGuia(numeroGuia != null && !numeroGuia.isBlank() ? numeroGuia : null);
            venta.setDireccionEnvio(direccionEnvio);
            venta.setCostoEnvio(costoEnvio);
            venta.setTelefonoReferencia(telefonoReferencia);
            venta.setCosto(costo);
            venta.setEstilo(estilo);
            venta.setColor(color);
            venta.setNumeroZapato(numeroZapato);
            venta.setEstado(estado);
            ventaService.guardar(venta);
            ra.addFlashAttribute("successMsg", "Venta registrada exitosamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Error: " + e.getMessage());
        }
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{token}")
    public String mostrarFormularioEditar(@PathVariable String token, Model model, RedirectAttributes ra) {
        Long id = RouteEncryptionUtil.decryptLong(token);
        return ventaService.buscarPorId(id).map(v -> {
            model.addAttribute("venta", v);
            model.addAttribute("estilos", EstiloZapato.values());
            model.addAttribute("colores", ColorZapato.values());
            model.addAttribute("estados", EstadoVenta.values());
            model.addAttribute("tallasZapato", new int[]{34, 35, 36, 37, 38, 39});
            model.addAttribute("idToken", token);
            return "ventas/formulario";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Venta no encontrada.");
            return "redirect:/ventas";
        });
    }

    @PostMapping("/actualizar/{token}")
    public String actualizar(
            @PathVariable String token,
            @RequestParam(required = false) String numeroGuia,
            @RequestParam String direccionEnvio,
            @RequestParam(required = false) BigDecimal costoEnvio,
            @RequestParam String telefonoReferencia,
            @RequestParam BigDecimal costo,
            @RequestParam EstiloZapato estilo,
            @RequestParam ColorZapato color,
            @RequestParam int numeroZapato,
            @RequestParam EstadoVenta estado,
            @RequestParam(required = false) Long fechaVenta,
            RedirectAttributes ra) {
        try {
            Long id = RouteEncryptionUtil.decryptLong(token);
            Venta venta = new Venta();
            venta.setFechaVenta((fechaVenta != null && fechaVenta > 0) ? fechaVenta : System.currentTimeMillis());
            venta.setNumeroGuia(numeroGuia != null && !numeroGuia.isBlank() ? numeroGuia : null);
            venta.setDireccionEnvio(direccionEnvio);
            venta.setCostoEnvio(costoEnvio);
            venta.setTelefonoReferencia(telefonoReferencia);
            venta.setCosto(costo);
            venta.setEstilo(estilo);
            venta.setColor(color);
            venta.setNumeroZapato(numeroZapato);
            venta.setEstado(estado);
            ventaService.actualizar(id, venta);
            ra.addFlashAttribute("successMsg", "Venta actualizada exitosamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Error: " + e.getMessage());
        }
        return "redirect:/ventas";
    }

    @GetMapping("/eliminar/{token}")
    public String eliminar(@PathVariable String token, RedirectAttributes ra) {
        try {
            Long id = RouteEncryptionUtil.decryptLong(token);
            ventaService.eliminar(id);
            ra.addFlashAttribute("successMsg", "Venta eliminada.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Error: " + e.getMessage());
        }
        return "redirect:/ventas";
    }
}

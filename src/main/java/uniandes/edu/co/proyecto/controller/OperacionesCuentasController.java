package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.modelo.OperacionCuenta;
import uniandes.edu.co.proyecto.repositorios.OperacionCuentaRepository;

@Controller
public class OperacionesCuentasController {
  @Autowired
  private OperacionCuentaRepository operacionCuentaRepository;

  @GetMapping("/operaciones_cuentas")
  public String operaciones_cuentas(Model model) {
    model.addAttribute("operaciones_cuentas", operacionCuentaRepository.darOperacionesCuentas());
    return "operaciones_cuentas";
  }

  @GetMapping("/operaciones_cuentas/new")
  public String operaciones_cuentasForm(Model model) {
    model.addAttribute("operacion_cuenta", new OperacionCuenta());
    return "operaciones_cuentas";
  }

  @PostMapping("/operaciones_cuentas/new/save")
  public String operaciones_cuentasSave(@ModelAttribute OperacionCuenta operacionCuenta) {
    operacionCuentaRepository.insertarOperacioneCuenta(operacionCuenta.getTipo_operacion(), operacionCuenta.getFecha(),
        operacionCuenta.getCuenta_salida(), operacionCuenta.getMonto_operacion(), operacionCuenta.getCliente(),
        operacionCuenta.getPunto_atencion().getId(), operacionCuenta.getCuenta_llegada());
    return "redirect:/operaciones_cuentas";
  }

  @GetMapping("/operaciones_cuentas/{id}/edit")
  public String operaciones_cuentasEditForm(@PathVariable("id") int id, Model model) {
    OperacionCuenta operacionCuenta = operacionCuentaRepository.darOperacioneCuenta(id);
    if (operacionCuenta != null) {
      model.addAttribute("operacion_cuenta", operacionCuenta);
      return "operacion_cuentaEdit";
    } else {
      return "redirect:/operaciones_cuentas";
    }
  }

  @PostMapping("/operaciones_cuentas/{id}/edit/save")
  public String operaciones_cuentasEditSave(@PathVariable("id") long id,
      @ModelAttribute OperacionCuenta operacionCuenta) {
    operacionCuentaRepository.actualizarOperacioneCuenta(id, operacionCuenta.getTipo_operacion(),
        operacionCuenta.getFecha(), operacionCuenta.getCuenta_salida(), operacionCuenta.getMonto_operacion(),
        operacionCuenta.getCliente(), operacionCuenta.getCliente(), operacionCuenta.getCuenta_llegada());
    return "redirect:/operaciones_cuentas";
  }

  @GetMapping("/operaciones_cuentas/{id}/delete")
  public String operaciones_cuentasBorrar(@PathVariable("id") long id) {
    operacionCuentaRepository.eliminaOperacioneCuenta(id);
    return "redirect:/operaciones_cuentas";
  }

}
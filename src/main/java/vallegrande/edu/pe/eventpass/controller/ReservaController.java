package vallegrande.edu.pe.eventpass.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vallegrande.edu.pe.eventpass.dto.ReservaResponse;
import vallegrande.edu.pe.eventpass.service.ReservaEventPassService;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaEventPassService service = new ReservaEventPassService();

    @PostMapping("/confirmar")
    public ResponseEntity<ReservaResponse> confirmarReserva(
            @RequestParam int cantidad,
            @RequestParam double precio,
            @RequestParam(required = false, defaultValue = "") String codigo,
            @RequestParam int stock) {
        try {
            double total = service.calcularTotalReserva(cantidad, precio, codigo, stock);
            return ResponseEntity.ok(new ReservaResponse(true, "Reserva procesada exitosamente", total));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ReservaResponse(false, e.getMessage(), 0.0));
        }
    }
}
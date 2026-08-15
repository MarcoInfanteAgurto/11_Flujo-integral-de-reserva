package vallegrande.edu.pe.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vallegrande.edu.pe.eventpass.service.ReservaEventPassService;

import static org.junit.jupiter.api.Assertions.*;

class ReservaEventPassServiceTest {

    private final ReservaEventPassService service = new ReservaEventPassService();

    @Test
    @DisplayName("CP-01 (AUTOMÁTICO): Confirmar reserva exitosa con stock suficiente")
    void testReservaExitosaConStockSuficiente() {
        double resultado = service.calcularTotalReserva(2, 100.0, "", 50);
        assertEquals(200.0, resultado);
    }

    @Test
    @DisplayName("CP-03 (AUTOMÁTICO): Validar excepción ante cantidad de entradas igual a cero")
    void testCantidadEntradasInvalidaCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            service.calcularTotalReserva(0, 100.0, "", 50)
        );
    }

    @Test
    @DisplayName("CP-04 (AUTOMÁTICO): Aplicar un 20% de descuento con código válido")
    void testReservaConCodigoDescuentoValido() {
        double resultado = service.calcularTotalReserva(2, 100.0, "EVENTO20", 50);
        assertEquals(160.0, resultado);
    }

    @Test
    @DisplayName("CP-05 (AUTOMÁTICO): Bloquear compra superior al límite máximo de 10 entradas")
    void testReservaExcedeLimiteMaximoEntradas() {
        assertThrows(IllegalArgumentException.class, () -> 
            service.calcularTotalReserva(12, 100.0, "", 50)
        );
    }
}
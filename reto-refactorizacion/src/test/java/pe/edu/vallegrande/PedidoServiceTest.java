package pe.edu.vallegrande;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    private final PedidoService service = new PedidoService();

    @Test
    void debeCalcularTotal() {
        double resultado = service.calcularTotal(100, 2, false);

        assertEquals(200, resultado);
    }

    @Test
    void debeAplicarDescuentoClienteFrecuente() {
        double resultado = service.calcularTotal(100, 2, true);

        assertEquals(180, resultado);
    }

    @Test
    void debeObtenerEstadoMediano() {
        String resultado = service.obtenerEstado(200);

        assertEquals("MEDIANO", resultado);
    }

    @Test
    void debeValidarPedidoCorrecto() {
        boolean resultado = service.validarPedido("Laptop", 2);

        assertTrue(resultado);
    }

    @Test
    void debeAplicarDescuentoMayorista() {
        assertEquals(950, service.calcularTotal(100, 10, false));
    }

    @Test
    void debeRetornarCeroCuandoLaCantidadNoEsValida() {
        assertEquals(0, service.calcularTotal(100, 0, false));
    }

    @Test
    void debeClasificarLosLimitesDeEstado() {
        assertAll(
                () -> assertEquals("ERROR", service.obtenerEstado(0)),
                () -> assertEquals("PEQUEÑO", service.obtenerEstado(99.99)),
                () -> assertEquals("MEDIANO", service.obtenerEstado(499.99)),
                () -> assertEquals("GRANDE", service.obtenerEstado(500))
        );
    }

    @Test
    void debeRechazarProductoNuloVacioOSoloConEspacios() {
        assertAll(
                () -> assertFalse(service.validarPedido(null, 1)),
                () -> assertFalse(service.validarPedido("", 1)),
                () -> assertFalse(service.validarPedido("   ", 1)),
                () -> assertFalse(service.validarPedido("Laptop", 0))
        );
    }
}
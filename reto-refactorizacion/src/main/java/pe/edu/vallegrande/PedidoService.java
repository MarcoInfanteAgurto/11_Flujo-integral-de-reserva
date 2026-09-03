package pe.edu.vallegrande;

public class PedidoService {

    private static final double DESCUENTO_CLIENTE_FRECUENTE = 0.90;
    private static final double DESCUENTO_MAYORISTA = 0.95;
    private static final int CANTIDAD_MINIMA_MAYORISTA = 10;

    public double calcularTotal(double precio, int cantidad, boolean clienteFrecuente) {
        if (cantidad <= 0) {
            return 0;
        }

        double subtotal = precio * cantidad;
        double total = aplicarDescuentoClienteFrecuente(subtotal, clienteFrecuente);
        return aplicarDescuentoMayorista(total, cantidad);
    }

    public String obtenerEstado(double total) {

        if (total <= 0) {
            return "ERROR";
        } else if (total < 100) {
            return "PEQUEÑO";
        } else if (total < 500) {
            return "MEDIANO";
        } else {
            return "GRANDE";
        }
    }

    public boolean validarPedido(String producto, int cantidad) {

        return producto != null && !producto.isBlank() && cantidad > 0;
    }

    private double aplicarDescuentoClienteFrecuente(double subtotal, boolean clienteFrecuente) {
        return clienteFrecuente ? subtotal * DESCUENTO_CLIENTE_FRECUENTE : subtotal;
    }

    private double aplicarDescuentoMayorista(double total, int cantidad) {
        return cantidad >= CANTIDAD_MINIMA_MAYORISTA ? total * DESCUENTO_MAYORISTA : total;
    }
}
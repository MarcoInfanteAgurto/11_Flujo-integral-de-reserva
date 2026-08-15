package vallegrande.edu.pe.eventpass.service;

public class ReservaEventPassService {

    public double calcularTotalReserva(int cantidadEntradas, double precioUnitario, String codigoDescuento, int stockDisponible) {
        if (cantidadEntradas <= 0) {
            throw new IllegalArgumentException("La cantidad mínima de entradas debe ser 1.");
        }
        if (cantidadEntradas > 10) {
            throw new IllegalArgumentException("No se permite comprar más de 10 entradas por transacción.");
        }
        if (cantidadEntradas > stockDisponible) {
            throw new IllegalArgumentException("No hay suficiente stock disponible.");
        }

        double subtotal = cantidadEntradas * precioUnitario;
        double descuento = 0.0;

        // Limpiar espacios en blanco
        String codigoLimpio = (codigoDescuento != null) ? codigoDescuento.trim() : "";

        // Lista de códigos disponibles
        if ("EVENTO20".equalsIgnoreCase(codigoLimpio)) {
            descuento = subtotal * 0.20; // 20% de descuento
        } else if ("EVENTO50".equalsIgnoreCase(codigoLimpio)) {
            descuento = subtotal * 0.50; // 50% de descuento
        }

        return subtotal - descuento;
    }
}
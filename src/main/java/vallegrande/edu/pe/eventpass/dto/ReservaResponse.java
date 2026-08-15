package vallegrande.edu.pe.eventpass.dto;

public class ReservaResponse {
    private boolean exito;
    private String mensaje;
    private double totalPagar;

    public ReservaResponse(boolean exito, String mensaje, double totalPagar) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.totalPagar = totalPagar;
    }
    // Getters y Setters
    public boolean isExito() { return exito; }
    public String getMensaje() { return mensaje; }
    public double getTotalPagar() { return totalPagar; }
}
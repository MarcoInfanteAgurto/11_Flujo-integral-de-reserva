package pruebas.parametrizada;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Producto {

	private final String codigo;
	private final String nombre;
	private final BigDecimal precio;
	private final int stock;

	private Producto(String codigo, String nombre, BigDecimal precio, int stock) {
		this.codigo = validarCodigo(codigo);
		this.nombre = validarNombre(nombre);
		this.precio = validarPrecio(precio);
		this.stock = validarStock(stock);
	}

	public static Producto registrar(String codigo, String nombre, BigDecimal precio, int stock) {
		return new Producto(codigo, nombre, precio, stock);
	}

	public static String validarCodigo(String codigo) {
		if (codigo == null || codigo.isBlank()) {
			throw new IllegalArgumentException("El codigo es obligatorio");
		}
		return codigo.trim();
	}

	public static String validarNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}
		return nombre.trim();
	}

	public static BigDecimal validarPrecio(BigDecimal precio) {
		if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("El precio debe ser mayor que cero");
		}
		return precio.setScale(2, RoundingMode.HALF_UP);
	}

	public static int validarStock(int stock) {
		if (stock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo");
		}
		return stock;
	}

	public BigDecimal calcularPrecioFinal(BigDecimal descuentoPorcentaje) {
		if (descuentoPorcentaje == null || descuentoPorcentaje.compareTo(BigDecimal.ZERO) < 0
				|| descuentoPorcentaje.compareTo(BigDecimal.valueOf(100)) > 0) {
			throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
		}
		BigDecimal factor = BigDecimal.ONE.subtract(descuentoPorcentaje.divide(BigDecimal.valueOf(100)));
		return precio.multiply(factor).setScale(2, RoundingMode.HALF_UP);
	}

	public boolean tieneStockDisponible() {
		return stock > 0;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (!(objeto instanceof Producto producto)) {
			return false;
		}
		return codigo.equals(producto.codigo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
}
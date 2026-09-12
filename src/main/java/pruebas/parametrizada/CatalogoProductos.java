package pruebas.parametrizada;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class CatalogoProductos {

	private final Map<String, Producto> productos = new LinkedHashMap<>();

	public Producto registrar(String codigo, String nombre, BigDecimal precio, int stock) {
		Producto producto = Producto.registrar(codigo, nombre, precio, stock);
		if (productos.putIfAbsent(producto.getCodigo(), producto) != null) {
			throw new IllegalArgumentException("Ya existe un producto con el codigo " + producto.getCodigo());
		}
		return producto;
	}

	public Optional<Producto> buscarPorCodigo(String codigo) {
		return Optional.ofNullable(productos.get(codigo));
	}

	public Collection<Producto> listar() {
		return productos.values();
	}
}
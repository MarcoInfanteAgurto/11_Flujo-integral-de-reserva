package pruebas.parametrizada;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductoTest {

	@Test
	void registraProductoConDatosValidos() {
		Producto producto = Producto.registrar(" P-001 ", " Teclado ", new BigDecimal("25.5"), 10);

		assertEquals("P-001", producto.getCodigo());
		assertEquals("Teclado", producto.getNombre());
		assertEquals(new BigDecimal("25.50"), producto.getPrecio());
		assertEquals(10, producto.getStock());
	}

	@Test
	void rechazaCodigoVacio() {
		assertThrows(IllegalArgumentException.class,
				() -> Producto.registrar(" ", "Teclado", new BigDecimal("10"), 1));
	}

	@Test
	void rechazaNombreVacio() {
		assertThrows(IllegalArgumentException.class,
				() -> Producto.registrar("P-001", "", new BigDecimal("10"), 1));
	}

	@Test
	void rechazaPrecioCero() {
		assertThrows(IllegalArgumentException.class,
				() -> Producto.registrar("P-001", "Teclado", BigDecimal.ZERO, 1));
	}

	@Test
	void rechazaPrecioNegativo() {
		assertThrows(IllegalArgumentException.class,
				() -> Producto.registrar("P-001", "Teclado", new BigDecimal("-1"), 1));
	}

	@Test
	void rechazaStockNegativo() {
		assertThrows(IllegalArgumentException.class,
				() -> Producto.registrar("P-001", "Teclado", new BigDecimal("10"), -1));
	}

	@Test
	void calculaPrecioConDescuento() {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("100"), 1);

		assertEquals(new BigDecimal("75.00"), producto.calcularPrecioFinal(new BigDecimal("25")));
	}

	@Test
	void aceptaDescuentoCero() {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("100"), 1);

		assertEquals(new BigDecimal("100.00"), producto.calcularPrecioFinal(BigDecimal.ZERO));
	}

	@Test
	void aceptaDescuentoDelCienPorCiento() {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("100"), 1);

		assertEquals(new BigDecimal("0.00"), producto.calcularPrecioFinal(new BigDecimal("100")));
	}

	@Test
	void rechazaDescuentoFueraDelRango() {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("100"), 1);

		assertThrows(IllegalArgumentException.class,
				() -> producto.calcularPrecioFinal(new BigDecimal("100.01")));
	}

	@Test
	void informaStockDisponibleCuandoEsMayorQueCero() {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("10"), 1);

		assertTrue(producto.tieneStockDisponible());
	}

	@Test
	void informaSinStockCuandoEsCero() {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("10"), 0);

		assertFalse(producto.tieneStockDisponible());
	}

	@Test
	void catalogoRegistraBuscaYListaProductos() {
		CatalogoProductos catalogo = new CatalogoProductos();
		Producto producto = catalogo.registrar("P-001", "Teclado", new BigDecimal("10"), 2);

		assertEquals(Optional.of(producto), catalogo.buscarPorCodigo("P-001"));
		assertEquals(1, catalogo.listar().size());
		assertTrue(catalogo.buscarPorCodigo("P-999").isEmpty());
	}

	@Test
	void catalogoRechazaCodigoDuplicado() {
		CatalogoProductos catalogo = new CatalogoProductos();
		catalogo.registrar("P-001", "Teclado", new BigDecimal("10"), 2);

		assertThrows(IllegalArgumentException.class,
				() -> catalogo.registrar("P-001", "Otro", new BigDecimal("20"), 1));
	}

	@Test
	void productosConElMismoCodigoSonIguales() {
		Producto primero = Producto.registrar("P-001", "Teclado", new BigDecimal("10"), 1);
		Producto segundo = Producto.registrar("P-001", "Otro nombre", new BigDecimal("20"), 5);

		assertEquals(primero, segundo);
		assertEquals(primero.hashCode(), segundo.hashCode());
	}

	@Test
	void productosConCodigoDiferenteNoSonIguales() {
		Producto primero = Producto.registrar("P-001", "Teclado", new BigDecimal("10"), 1);
		Producto segundo = Producto.registrar("P-002", "Teclado", new BigDecimal("10"), 1);

		assertNotEquals(primero, segundo);
		assertNotEquals(primero, null);
		assertNotEquals(primero, "P-001");
	}

	@ParameterizedTest(name = "nombre valido: {0}")
	@ValueSource(strings = { "Teclado", "Monitor 24 pulgadas", "A" })
	void aceptaNombresValidos(String nombre) {
		assertEquals(nombre, Producto.validarNombre(nombre));
	}

	@ParameterizedTest(name = "nombre invalido: {0}")
	@NullSource
	@ValueSource(strings = { "", "   " })
	void rechazaNombresInvalidos(String nombre) {
		assertThrows(IllegalArgumentException.class, () -> Producto.validarNombre(nombre));
	}

	@ParameterizedTest(name = "precio {0} es valido: {1}")
	@CsvSource({ "0.01, true", "10, true", "99999.99, true", "0, false", "-10, false" })
	void validaPreciosEnVariosEscenarios(String valor, boolean valido) {
		if (valido) {
			assertTrue(Producto.validarPrecio(new BigDecimal(valor)).compareTo(BigDecimal.ZERO) > 0);
		} else {
			assertThrows(IllegalArgumentException.class,
					() -> Producto.validarPrecio(new BigDecimal(valor)));
		}
	}

	@ParameterizedTest(name = "stock {0} disponible: {1}")
	@CsvSource({ "0, false", "1, true", "25, true" })
	void determinaDisponibilidadParaVariosStocks(int stock, boolean disponible) {
		Producto producto = Producto.registrar("P-001", "Teclado", new BigDecimal("10"), stock);

		assertEquals(disponible, producto.tieneStockDisponible());
	}
}
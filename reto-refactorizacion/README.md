# Reto de refactorizacion

## Objetivo

Mejorar la legibilidad y mantenibilidad de `PedidoService` sin cambiar las reglas principales del negocio: calcular el total, clasificar el tamano de un pedido y validar sus datos.

## Problemas del codigo inicial

- La variable `x` no expresaba que almacenaba el total del pedido.
- Los valores `0.90`, `0.95` y `10` eran numeros magicos.
- `calcularTotal` concentraba el subtotal y los dos descuentos en un solo bloque.
- La validacion tenia varios `if` consecutivos para una misma regla.
- Las pruebas solo cubrian los casos felices y no comprobaban limites ni entradas invalidas.

## Refactorizacion realizada

- Se reemplazo `x` por `subtotal` y `total`.
- Se crearon constantes con nombres de negocio:
  - `DESCUENTO_CLIENTE_FRECUENTE`.
  - `DESCUENTO_MAYORISTA`.
  - `CANTIDAD_MINIMA_MAYORISTA`.
- Se extrajeron `aplicarDescuentoClienteFrecuente` y `aplicarDescuentoMayorista` para separar responsabilidades.
- Se simplifico `validarPedido` usando una expresion booleana clara y `String.isBlank()`.
- Se reutiliza una instancia del servicio en las pruebas.
- Se agregaron pruebas para descuento mayorista, cantidad invalida, limites de estado y producto nulo/vacio/blanco.

## Comparacion de codigo

### Antes

```java
public double calcularTotal(double precio, int cantidad, boolean clienteFrecuente) {
    double x = 0;

    if (cantidad > 0) {
        x = precio * cantidad;
    }

    if (clienteFrecuente) {
        x = x * 0.90;
    }

    if (cantidad >= 10) {
        x = x * 0.95;
    }

    return x;
}
```

### Despues

```java
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
```

La logica publica y las reglas de calculo se mantienen. El unico ajuste funcional intencional esta en la validacion: un producto compuesto solo por espacios ahora se considera invalido.

## Respuestas para la sustentacion

### 1. Que problemas o code smells tenia el codigo inicial?

Presentaba nombres poco expresivos, numeros magicos, demasiadas decisiones dentro de un mismo metodo y validaciones repetitivas. Ademas, las pruebas no cubrian las ramas de cantidad mayorista, errores ni limites de clasificacion.

### 2. Por que decidieron cambiar esa parte especifica?

`calcularTotal` concentra una regla importante del negocio y era dificil identificar rapidamente cada descuento. Separar las reglas permite leerlas, probarlas y modificarlas de forma independiente.

### 3. Que tecnica concreta de refactorizacion utilizaron?

Se aplicaron **Rename Variable**, **Replace Magic Number with Symbolic Constant**, **Extract Method**, **Guard Clause** y simplificacion de condiciones booleanas.

### 4. Cambio el comportamiento en algun momento?

El calculo del total y la clasificacion conservan el comportamiento anterior. La validacion mejora un caso limite: cadenas de espacios ahora se rechazan. No se modificaron las firmas publicas.

### 5. Las pruebas JUnit siguen pasando al 100%?

Si. La ejecucion Maven final obtuvo 8 pruebas ejecutadas, 0 fallos y 0 errores.

### 6. Que revelo el reporte de JaCoCo al comparar antes y despues?

El proyecto no tenia un reporte historico del estado inicial para una comparacion numerica exacta. Como resultado final, JaCoCo reporta 100% en instrucciones, ramas, lineas, metodos y complejidad de `PedidoService`. Las pruebas nuevas hicieron explicita la cobertura de limites y ramas que antes no estaban verificadas.

### 7. Que mejoro realmente en la mantenibilidad?

Ahora las reglas tienen nombres que explican su intencion, los descuentos estan aislados y una modificacion puede hacerse en un metodo pequeno. Las pruebas tambien documentan el comportamiento esperado y protegen los limites.

## Cuadro comparativo general

| Aspecto evaluado | Estado inicial (antes) | Estado final (despues) |
|---|---|---|
| Calidad del codigo | Dificil de leer por nombres y condiciones poco expresivas | Codigo limpio, directo y organizado por responsabilidad |
| Legibilidad | Variable generica `x` y numeros sin contexto | `subtotal`, `total` y constantes con nombres de negocio |
| Codigo repetido | Logica de descuentos agrupada en un metodo largo | Descuentos extraidos en metodos pequenos |
| Cobertura JaCoCo | Sin linea base historica disponible; pruebas parciales | 100% de instrucciones, ramas, lineas, metodos y complejidad |
| Pruebas JUnit | 4 pruebas, principalmente de caminos felices | 8 pruebas, todas pasan, con limites y entradas invalidas |
| Mantenibilidad | Cambios mas riesgosos y reglas dificiles de localizar | Facil de adaptar, extender y verificar |
| Contrato publico | Tres metodos publicos | Se conservan los mismos tres metodos publicos |

## Ejecucion

Desde la carpeta `reto-refactorizacion`:

```bash
mvn clean test
```

El reporte HTML de JaCoCo queda disponible en:

```text
target/site/jacoco/index.html
```

## Tecnologias

- Java 17
- Maven
- JUnit Jupiter 5.10.2
- JaCoCo 0.8.12

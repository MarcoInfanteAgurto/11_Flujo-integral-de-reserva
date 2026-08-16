# 🎟️ EventPass - Flujo Integral de Reserva
## Proyecto de Pruebas: Manuales y Automatizadas

### 👥 Integrantes y Roles
*   **Marco Infante Agurto** - *QA Automation Engineer / Developer Support* (Responsable de la automatización de pruebas unitarias, resolución de errores de configuración en Spring Boot y redacción técnica).
*   **Colaborador 2** - *Manual QA Tester* (Responsable del diseño de los casos de prueba, ejecución manual a través del frontend en Angular y captura de evidencias).
*   **Colaborador 3** - *QA Documenter & Video Lead* (Responsable de la consolidación de la matriz de pruebas, registro de reportes de defectos y guion/dirección del video de demostración).

---

### ⚙️ Funcionalidad Asignada
La funcionalidad bajo prueba es el **Flujo de Reserva de Entradas (EventPass)**. Este módulo se compone de:
1.  **Frontend (Angular)**: Interfaz de usuario interactiva que muestra los detalles del evento (nombre, precio unitario y stock restante) y permite ingresar la cantidad deseada junto con un código promocional.
2.  **Backend (Spring Boot)**: API REST (`/api/reservas/confirmar`) que procesa la lógica de negocio: cálculo del subtotal, validación de stock disponible, aplicación de reglas de descuento (20% para `EVENTO20` y 50% para `EVENTO50`) y control de límites (compra mínima de 1 y máxima de 10 entradas).

---

### 🧪 Casos de Prueba Realizados
Se diseñó un set completo de pruebas para validar tanto el flujo feliz como las excepciones del negocio:

1.  **CP-01**: Confirmar reserva exitosa con stock suficiente (flujo estándar sin descuento).
2.  **CP-02**: Validar bloqueo ante stock disponible insuficiente.
3.  **CP-03**: Validar excepción ante cantidad de entradas igual a cero o negativas.
4.  **CP-04**: Aplicar un 20% de descuento con el código promocional válido (`EVENTO20`).
5.  **CP-05**: Bloquear compra superior al límite máximo de 10 entradas.

---

### 🤖 Casos Automatizados
Se automatizaron en la capa de Backend mediante **JUnit 5** y **Spring Boot Test** en el archivo [ReservaEventPassServiceTest.java](file:///c:/Users/marco/Downloads/11_Flujo-integral-de-reserva/src/test/java/vallegrande/edu/pe/test/ReservaEventPassServiceTest.java):
*   **CP-01**: `testReservaExitosaConStockSuficiente` (Valida el cálculo de total estándar).
*   **CP-03**: `testCantidadEntradasInvalidaCero` (Valida el lanzamiento de `IllegalArgumentException` si la cantidad es 0).
*   **CP-04**: `testReservaConCodigoDescuentoValido` (Valida que el descuento del 20% se aplique correctamente).
*   **CP-05**: `testReservaExcedeLimiteMaximoEntradas` (Valida el lanzamiento de `IllegalArgumentException` si se piden más de 10 entradas).

---

### 🛠️ Herramientas Utilizadas
*   **Automatización de Backend**: Java 17, Spring Boot Test, JUnit 5, Maven Wrapper.
*   **Frontend**: Angular 20, HTML5, SCSS, TypeScript.
*   **Pruebas Manuales**: Google Chrome DevTools (Inspección de red y consola), Postman (para pruebas unitarias de API REST).

---

### 🚀 Instrucciones para Ejecutar las Pruebas

#### 1. Preparación del Entorno
Clonar el repositorio y ubicarse en la rama de desarrollo:
```bash
git checkout develop
```

#### 2. Ejecución de Pruebas Automatizadas
Desde la raíz del proyecto, ejecute el siguiente comando para correr todas las pruebas unitarias e integradas:
```bash
.\mvnw.cmd test
```
*Nota: Este comando compilará el código y ejecutará las suites de JUnit, devolviendo `BUILD SUCCESS`.*

#### 3. Despliegue Local para Pruebas Manuales
Para interactuar con la interfaz gráfica:

*   **Levantar el Backend (Puerto 8080)**:
    ```bash
    .\mvnw.cmd spring-boot:run
    ```
*   **Levantar el Frontend (Puerto 4200)**:
    1. Dirigirse a la carpeta del frontend: `cd front`
    2. Instalar dependencias: `npm install`
    3. Iniciar el servidor de desarrollo: `npm start`
    4. Abrir en el navegador: [http://localhost:4200](http://localhost:4200)

---

### 📝 Análisis Comparativo (Respuestas Expresivas y Humanas)

#### 1. ¿Qué diferencia encuentran entre ejecutar la prueba manualmente y automatizarla?
*   **Prueba Manual**: Se realiza desde el punto de vista del usuario final. Consiste en abrir la página en el navegador, escribir valores reales en los inputs de Angular (ej. cantidad de entradas, código) y pulsar el botón para observar cómo responde la interfaz gráfica (las alertas de color verde o rojo). Es ideal para captar la experiencia de usuario y fallos estéticos, pero consume mucho tiempo por cada ciclo de repetición.
*   **Prueba Automatizada**: Se ejecuta a nivel de código directo en el backend. Mediante JUnit, simulamos el envío de datos directamente a la función de cálculo matemático (`ReservaEventPassService`). No requerimos levantar interfaces gráficas ni hacer clics; el software asume el rol del tester y evalúa aserciones (`assertEquals`, `assertThrows`) de forma matemática y rigurosa en milisegundos.

#### 2. ¿Cuál fue más rápida de ejecutar?
*   **La prueba automatizada fue infinitamente más rápida.** 
*   La suite completa de JUnit (`ReservaEventPassServiceTest`) tardó tan solo **0.171 segundos** en validar los 4 casos principales.
*   Una prueba manual requiere aproximadamente entre **15 y 30 segundos por caso** (abrir el navegador, digitar la información, esperar la respuesta visual y registrar el resultado), totalizando varios minutos para validar todo el flujo.

#### 3. ¿Qué dificultades encontraron durante la automatización?
*   **Configuración del Contexto de Spring Boot**: La principal dificultad técnica fue un error de configuración inicial en la clase de prueba general `TestApplicationTests`. Dicha clase arrojaba una excepción `IllegalStateException` porque estaba en el paquete `vallegrande.edu.pe.test` y no lograba autodetectar la configuración de la aplicación principal `EventPassApplication` (ubicada en `vallegrande.edu.pe.eventpass`). Se solucionó especificando explícitamente la clase en la anotación: `@SpringBootTest(classes = EventPassApplication.class)`.
*   **Mantenimiento del Sincronismo**: Garantizar que el backend y frontend estén al mismo nivel en las reglas (por ejemplo, que el mensaje de error arrojado por la excepción de Java sea idéntico al que el frontend espera renderizar).

#### 4. ¿Qué pruebas consideran conveniente mantener manuales?
*   **Pruebas de Usabilidad y UX**: Evaluar si el diseño visual del formulario es intuitivo, si los colores de las alertas (`alert-success` y `alert-danger`) son los adecuados para la accesibilidad, y si la interfaz es fluida y adaptable en dispositivos móviles.
*   **Pruebas Exploratorias**: Flujos no convencionales donde un tester humano intenta estresar la aplicación de formas no estructuradas (por ejemplo, recargar la página a mitad de una transacción o pulsar repetidamente el botón de confirmación).

#### 5. ¿Qué pruebas automatizarían en un proyecto real?
*   **Pruebas Unitarias de Regresión**: Todas las reglas de negocio críticas (descuentos, cálculo de IVA, límites transaccionales y validación de stock disponible) para asegurar que futuros cambios en el código no alteren la lógica que ya funciona.
*   **Pruebas de API REST (Integración)**: Automatizar la verificación de los controladores de Spring Boot (con herramientas como `MockMvc`) para validar que las peticiones HTTP retornen los códigos de estado adecuados (`200 OK`, `400 Bad Request`).
*   **Pruebas E2E (End-to-End)**: Flujos críticos automatizados con Cypress o Playwright, como por ejemplo: "Agregar entrada al carrito -> Aplicar descuento -> Confirmar reserva -> Verificar decremento del stock".

#### 6. ¿Qué ventajas y limitaciones encontraron en cada enfoque?
*   **Enfoque Manual**:
    *   *Ventajas*: Permite validar la experiencia real del usuario, detectar fallos visuales de CSS y no requiere código adicional.
    *   *Limitaciones*: Lento, no escalable, propenso al error humano por fatiga y costoso de repetir en cada despliegue.
*   **Enfoque Automatizado**:
    *   *Ventajas*: Ejecución ultra-rápida, repetible de manera idéntica infinitas veces, integración directa en pipelines de CI/CD para evitar despliegues rotos y documentación exacta de las reglas de negocio.
    *   *Limitaciones*: No detecta problemas visuales ni de experiencia de usuario, requiere un esfuerzo técnico de desarrollo inicial alto y genera costo de mantenimiento cuando cambian los requisitos del software.

---

### 📊 1. Matriz de Casos de Prueba

| ID Caso | Escenario / Propósito | Datos de Entrada (Inputs) | Resultado Esperado | Resultado Obtenido | Estado | Evidencia |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **CP-01** | Confirmar reserva exitosa con stock suficiente | Cantidad: `2`<br>Precio: `$100.0`<br>Código: `""`<br>Stock: `50` | Registro exitoso.<br>Monto total: `$200.0` | Exitoso. Monto a pagar `$200.0` | **APROBADO** | [Ver Drive](https://drive.google.com/drive/folders/1qBhQC0Hc-W-IlZPsUvQdheUBnPyLrqVc) |
| **CP-02** | Validar bloqueo ante stock disponible insuficiente | Cantidad: `5`<br>Precio: `$100.0`<br>Código: `""`<br>Stock: `2` | Bloqueo de compra.<br>Mensaje: *"No hay suficiente stock disponible."* | Bloqueo exitoso.<br>Mensaje: *"No hay suficiente stock disponible."* | **APROBADO** | [Ver Drive](https://drive.google.com/drive/folders/1qBhQC0Hc-W-IlZPsUvQdheUBnPyLrqVc) |
| **CP-03** | Validar excepción ante cantidad de entradas igual a cero | Cantidad: `0`<br>Precio: `$100.0`<br>Código: `""`<br>Stock: `50` | Bloqueo de compra.<br>Mensaje: *"La cantidad mínima de entradas debe ser 1."* | Bloqueo exitoso.<br>Mensaje: *"La cantidad mínima de entradas debe ser 1."* | **APROBADO** | [Ver Drive](https://drive.google.com/drive/folders/1qBhQC0Hc-W-IlZPsUvQdheUBnPyLrqVc) |
| **CP-04** | Aplicar un 20% de descuento con código válido | Cantidad: `2`<br>Precio: `$100.0`<br>Código: `"EVENTO20"`<br>Stock: `50` | Descuento aplicado.<br>Monto total: `$160.0` | Exitoso. Monto a pagar `$160.0` | **APROBADO** | [Ver Drive](https://drive.google.com/drive/folders/1qBhQC0Hc-W-IlZPsUvQdheUBnPyLrqVc) |
| **CP-05** | Bloquear compra superior al límite máximo de 10 entradas | Cantidad: `12`<br>Precio: `$100.0`<br>Código: `""`<br>Stock: `50` | Bloqueo de compra.<br>Mensaje: *"No se permite comprar más de 10 entradas por transacción."* | Bloqueo exitoso.<br>Mensaje: *"No se permite comprar más de 10 entradas por transacción."* | **APROBADO** | [Ver Drive](https://drive.google.com/drive/folders/1qBhQC0Hc-W-IlZPsUvQdheUBnPyLrqVc) |

---

### 🐛 2. Reporte de Defectos

#### Defecto #1: Falla en la carga del Contexto de Pruebas de Integración (Backend)
*   **Severidad**: Alta (Bloquea la compilación/empaquetado del proyecto a nivel de integración continua).
*   **Descripción**: La clase de prueba autogenerada `TestApplicationTests` fallaba al ejecutar `.\mvnw test` arrojando un error de tipo `IllegalStateException` por no encontrar una configuración de Spring Boot.
*   **Causa**: La estructura de paquetes colocaba el test en `vallegrande.edu.pe.test` mientras la aplicación de Spring Boot estaba en `vallegrande.edu.pe.eventpass`, por lo que el escaneo automático fallaba.
*   **Solución Aplicada**: Se corrigió el archivo `TestApplicationTests.java` añadiendo explícitamente la clase de arranque: `@SpringBootTest(classes = EventPassApplication.class)`.

#### Defecto #2: Vulnerabilidad en el Input de Cantidad (Frontend)
*   **Severidad**: Media
*   **Descripción**: En la interfaz web, aunque el control HTML5 posee restricciones visuales de mínimo y máximo, el usuario puede digitar caracteres no numéricos o el signo menos (`-`) de forma manual, lo que provoca que el formulario envíe valores vacíos o inconsistentes.
*   **Recomendación**: Implementar una directiva de validación del lado de Angular para interceptar el teclado e impedir la digitación de cualquier caracter que no sea un entero positivo.

---

### 📹 5. Guion del Video de Demostración (Estructura de 10 Minutos)

Para asegurar la máxima nota en la presentación en video con cámara activa, sigan este orden cronológico preciso:

1.  **Minutos 0:00 - 2:00 (Funcionalidad y Reto Asignado)**:
    *   Presentación de los integrantes del equipo y sus roles.
    *   Explicación de la funcionalidad "Flujo de Reserva de Entradas de EventPass".
    *   Explicar el reto: Validar que las reglas de negocio (descuentos de códigos, máximos de compra y verificación de stock) se cumplan de manera robusta tanto en interfaz como a nivel de lógica de backend.
2.  **Minutos 2:00 - 5:00 (Casos de Prueba y Ejecución Manual)**:
    *   Compartir pantalla mostrando el frontend en Angular corriendo en `http://localhost:4200`.
    *   Ejecutar en vivo el **CP-01** (compra exitosa sin descuento) y el **CP-04** (compra con el código `EVENTO20`). Mostrar cómo el total se recalcula dinámicamente de $200 a $160.
    *   Ejecutar un caso de error como el **CP-02** (pedir más del stock disponible) para ver la alerta de error en rojo.
3.  **Minutos 5:00 - 8:00 (Pruebas Automatizadas y Ejecución)**:
    *   Mostrar el código de las pruebas automatizadas en `ReservaEventPassServiceTest.java`.
    *   Explicar la resolución del error de configuración que impedía compilar (`classes = EventPassApplication.class`).
    *   Abrir una terminal en vivo y ejecutar el comando `.\mvnw.cmd test`.
    *   Mostrar en pantalla el resultado exitoso (`BUILD SUCCESS`) y cómo JUnit ejecuta todas las pruebas unitarias en menos de 0.2 segundos.
4.  **Minutos 8:00 - 10:00 (Resultados, Comparación y Conclusiones)**:
    *   Presentar la tabla comparativa de ejecución manual vs automatizada (velocidad, repetibilidad).
    *   Resaltar la importancia de tener ambos enfoques: las pruebas unitarias automatizadas garantizan que la lógica nunca se rompa, y la prueba manual asegura que la interfaz se muestre de forma amigable para el cliente.
    *   Cierre de la presentación.
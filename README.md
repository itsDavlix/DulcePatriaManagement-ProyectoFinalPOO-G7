<p align="center">
  <img src="logo-dulcepatria.png" alt="Dulce Patria" width="220"/>
</p>

# 🍰 DulceManagement - Gestión de operaciones Dulce Patria

Aplicación web modular para la gestión de inventario, recetas, reservas y **pendientes por falta de stock** de la cafetería/pastelería **Dulce Patria**.  
Desarrollada en **Java**, **OpenXava** y **PostgreSQL**, usando **IntelliJ IDEA** como IDE principal.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-007396" alt="Java 17+"/>
  <img src="https://img.shields.io/badge/OpenXava-7.4.5-00a8e8" alt="OpenXava 7.4.5"/>
  <img src="https://img.shields.io/badge/Base%20de%20Datos-PostgreSQL%2017-336791" alt="PostgreSQL 17"/>
  <img src="https://img.shields.io/badge/Estado-En%20desarrollo-informational" alt="Estado En desarrollo"/>
  <img src="https://img.shields.io/badge/UAM-Proyecto%20Acad%C3%A9mico-00bcd4" alt="UAM Proyecto Académico"/>
</p>

---

## ✨ Funcionalidades principales

- **Ingredientes**
  - Alta/edición de ingredientes.
  - Unidad de medida (g, ml, unidades, etc.).
  - Cantidad disponible en inventario.

- **Recetas y productos**
  - Registro de productos de venta.
  - Asociación de recetas: ingredientes + cantidad + unidad.

- **Reservas/Pedidos**
  - Selección de productos y cantidades para el cliente.
  - Cálculo automático de ingredientes a consumir según las recetas.
  - Verificación de inventario antes de confirmar la reserva.

- **Inventario**
  - Registro de existencias de ingredientes.
  - Descuento automático de stock al confirmar pedidos (según los ingredientes utilizados).

- **Lista de pendientes**
  - Cuando el inventario no es suficiente para cubrir totalmente una reserva, el sistema genera registros en **Pendientes** indicando:
    - El ingrediente afectado.
    - La cantidad faltante.
    - La reserva asociada.
  - Esto permite saber exactamente qué ingredientes hay que reponer para completar los pedidos.

---

## 🧪 Cómo probar el sistema desde cero

1. **Crear ingredientes**  
   - Registrar varios ingredientes con su unidad de medida y una cantidad inicial de inventario.

2. **Definir recetas y productos**  
   - Crear productos de venta.
   - Asociar a cada producto una receta con sus ingredientes y cantidades.

3. **Crear una reserva con inventario suficiente**  
   - Generar una reserva/pedido que use menos cantidad de la disponible.
   - Verificar que el inventario se descuente correctamente y que no se generen pendientes.

4. **Crear una reserva con inventario insuficiente**  
   - Generar otra reserva que requiera más cantidad de uno o varios ingredientes de la que hay en stock.
   - Confirmar la reserva y revisar:
     - Que se hayan registrado entradas en **Pendientes** con la cantidad faltante.
     - Que puedas identificar fácilmente qué ingresar al inventario para poder cumplir el pedido.

---

## 🧰 Tecnologías

- Java (JDK 17)  
- OpenXava 7.4.5  
- PostgreSQL 17  
- IntelliJ IDEA  

---

## 🚀 Puesta en marcha

1. Clonar el repositorio:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd DulceManagement
   ```

2. Crear una base de datos en PostgreSQL (por ejemplo `dulce_management`).

3. Configurar las credenciales de la base de datos en los archivos de configuración del proyecto  
   (por ejemplo `persistence.xml` o las propiedades de OpenXava).

4. Importar el proyecto en **IntelliJ IDEA** como proyecto Maven/Gradle (según corresponda) y ejecutar la aplicación  
   usando la clase principal de OpenXava o la configuración de ejecución incluida en el proyecto.

5. Acceder desde el navegador a la URL local configurada  
   (por ejemplo `http://localhost:8080/DulceManagement`).

---

## 👤 Autor

Desarrollado por **David Espinoza** como proyecto académico.
<p align="center">
  <img src="logo-dulcepatria.png" alt="Dulce Patria" width="220"/>
</p>

# 🍰 DulceManagement - Gestion de operaciones DulcePatria

Aplicación web modular para la gestión de inventario, recetas y reservas de la cafetería/pastelería **Dulce Patria**.  
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
  - Descuento automático de stock al confirmar pedidos.

- **Lista de pendientes**
  - Generación de una lista de ingredientes faltantes cuando no alcanza el stock para un pedido.

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

3. Configurar las credenciales de la BD en los archivos de configuración del proyecto (p. ej. `persistence.xml` o propiedades de OpenXava).

4. Importar el proyecto en IntelliJ IDEA y ejecutar la aplicación (clase principal de OpenXava).

5. Acceder desde el navegador a la URL local configurada (por ejemplo `http://localhost:8080/DulceManagement`).

---

## 👤 Autor

Desarrollado por **David Espinoza** como proyecto académico.

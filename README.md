<p align="center">
  <img src="logo-dulcepatria.png" alt="Dulce Patria" width="220"/>
</p>

# 🍰 DulceManagement - Gestión integral de operaciones Dulce Patria

Aplicación web modular para la gestión de inventario, recetas, reservas y control de pendientes por falta de stock de la cafetería/pastelería **Dulce Patria**.

Desarrollada en **Java**, **OpenXava** y **PostgreSQL**, usando **IntelliJ IDEA** como IDE principal.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-007396" alt="Java 17+"/>
  <img src="https://img.shields.io/badge/OpenXava-7.4.5-00a8e8" alt="OpenXava 7.4.5"/>
  <img src="https://img.shields.io/badge/Base%20de%20Datos-PostgreSQL%2017-336791" alt="PostgreSQL 17"/>
  <img src="https://img.shields.io/badge/Estado-Completado-success" alt="Estado Completado"/>
  <img src="https://img.shields.io/badge/UAM-Proyecto%20Acad%C3%A9mico-00bcd4" alt="UAM Proyecto Académico"/>
</p>

---

## 📝 Sobre el Proyecto

**DulceManagement** nace como una solución para centralizar y automatizar la operación diaria de la cafetería/pastelería **Dulce Patria**:

- Evita el control manual de inventarios y recetas.
- Reduce errores al tomar reservas y pedidos.
- Permite identificar de forma inmediata qué ingredientes faltan para completar las órdenes.
- Brinda trazabilidad entre **ingredientes → recetas → productos → reservas**.

El sistema está pensado para uso interno del negocio, con una interfaz web sencilla basada en OpenXava, de fácil despliegue y mantenimiento.

---

## ✨ Funcionalidades Clave (Requerimientos Funcionales)

### 1. Gestión de Ingredientes

- Alta, edición y eliminación de ingredientes.
- Registro de:
  - Nombre del ingrediente.
  - Unidad de medida (gramos, mililitros, unidades, etc.).
  - Cantidad disponible en inventario.
- Consulta rápida del stock disponible.

### 2. Gestión de Recetas y Productos

- Registro de **productos de venta** (pasteles, postres, bebidas, etc.).
- Definición de **recetas** asociadas a cada producto:
  - Ingredientes requeridos.
  - Cantidad y unidad de medida por ingrediente.
- Cálculo automático de los ingredientes necesarios según la cantidad de productos solicitados.

### 3. Reservas / Pedidos

- Creación de reservas para clientes indicando:
  - Productos deseados.
  - Cantidades.
  - Fecha y detalles de la reserva.
- Validación automática del inventario antes de confirmar la reserva.
- Asociación de cada reserva con los ingredientes que consumirá.

### 4. Inventario

- Registro de existencias de ingredientes.
- Actualización automática del stock:
  - **Descuento** al confirmar reservas o pedidos.
  - Posibilidad de **reabastecer** inventario ingresando nuevas existencias.
- Consulta de movimientos de inventario por ingrediente.

### 5. Lista de Pendientes por Falta de Stock

Cuando el inventario no es suficiente para cubrir totalmente una reserva, el sistema genera registros en el módulo de **Pendientes**, indicando:

- Ingrediente afectado.
- Cantidad faltante.
- Reserva asociada.

Esto permite saber con precisión qué ingredientes y en qué cantidad deben reponerse para cumplir los pedidos pendientes.

### 6. Flujo básico de uso

1. Registrar ingredientes.
2. Definir recetas y productos.
3. Registrar reservas/pedidos.
4. Revisar:
   - Inventario actualizado.
   - Pendientes generados (si hubo faltante de stock).

---

## 🏗️ Arquitectura y Recursos

### Arquitectura General

DulceManagement sigue una arquitectura típica de aplicación empresarial Java:

1. **Capa de Presentación (Web UI)**  
   - Implementada con **OpenXava**, que genera una interfaz web basada en módulos.
   - Formularios y listas para gestionar las entidades principales (Ingredientes, Recetas, Productos, Reservas, Inventario, Pendientes).

2. **Capa de Lógica de Negocio**  
   - Clases Java que representan los módulos de dominio.
   - Reglas de negocio clave:
     - Cálculo de ingredientes necesarios por producto.
     - Verificación de stock al confirmar reservas.
     - Generación automática de registros en Pendientes.

3. **Capa de Persistencia**  
   - Uso de JPA/Hibernate (incluido en OpenXava) para mapear entidades a tablas.
   - Base de datos relacional **PostgreSQL 17**.

### Recursos Principales del Proyecto

- **Lenguaje:** Java (JDK 17+)
- **Framework:** OpenXava 7.4.5
- **Base de Datos:** PostgreSQL 17
- **IDE recomendado:** IntelliJ IDEA
- **Módulos de negocio:**
  - Ingredientes
  - Recetas
  - Productos
  - Reservas/Pedidos
  - Inventario
  - Pendientes

---

## 👥 Actores del Sistema

| Actor                      | Descripción                                                                                      | Permisos principales                                                                   |
|---------------------------|--------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| **Administrador/Coordinador** | Usuario con visión global del sistema.                                                        | Configurar catálogos, gestionar usuarios, revisar reportes e inventario general.      |
| **Encargado de Inventario**   | Responsable de registrar entradas y salidas de ingredientes.                                  | Crear/editar ingredientes, actualizar existencias, revisar y atender pendientes.      |
| **Personal de Ventas**        | Atiende al cliente y registra las reservas/pedidos en el sistema.                             | Crear reservas, consultar disponibilidad de productos y revisar estado de pedidos.    |
| **Cliente (indirecto)**       | No interactúa directamente con el sistema, pero se ve beneficiado por la correcta operación. | Sus pedidos se gestionan a través del personal de ventas usando DulceManagement.      |

> *Los actores pueden ajustarse a la estructura real del negocio (por ejemplo, fusionar roles o añadir nuevos usuarios según sea necesario).*

---

## 🧑‍💻 Equipo de Desarrollo

| CIF      | Nombre Completo                              | Rol                         |
|----------|----------------------------------------------|-----------------------------|
| 24010572 | David Alejandro Espinoza Largaespada         | Coordinador y desarrollador |

Proyecto desarrollado como parte de trabajo académico en la **Universidad Americana (UAM)**, orientado a resolver una necesidad real de gestión en la cafetería/pastelería **Dulce Patria**.

---

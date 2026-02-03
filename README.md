# 🏥 Sistema de Gestión de Citas Médicas

Sistema de aplicación de consola desarrollado en **Java** para gestionar pacientes, médicos y citas médicas de manera eficiente y organizada.

---

## 📁 Estructura del Proyecto

```
proyecto_citas_medicas/
│
├── src/
│   ├── Main.java                  → Clase principal y menú general
│   │
│   ├── modelo/                    → Clases de datos (entidades)
│   │   ├── Paciente.java          → Modelo de paciente
│   │   ├── Medico.java            → Modelo de médico
│   │   ├── Cita.java              → Modelo de cita
│   │   └── EstadoCita.java        → Enum de estados (Pendiente, Confirmada, etc.)
│   │
│   ├── servicio/                  → Lógica de negocio
│   │   ├── PacienteServicio.java  → CRUD de pacientes
│   │   ├── MedicoServicio.java    → CRUD de médicos
│   │   └── CitaServicio.java      → Lógica de citas y validaciones
│   │
│   ├── menu/                      → Controladores de menú (interfaz consola)
│   │   ├── MenuPacientes.java     → Menú de gestión de pacientes
│   │   ├── MenuMedicos.java       → Menú de gestión de médicos
│   │   ├── MenuCitas.java         → Menú de gestión de citas
│   │   └── MenuEstadisticas.java  → Dashboard de estadísticas
│   │
│   └── util/                      → Utilidades
│       └── Consola.java           → Formateo y entrada/salida en consola
│
└── README.md
```

---

## ✅ Funcionalidades

### Pacientes
- Registrar, actualizar y eliminar pacientes
- Buscar pacientes por nombre o apellido
- Ver lista completa de pacientes

### Médicos
- Registrar médicos con especialidad y horarios
- Buscar médicos por especialidad
- Actualizar y eliminar médicos

### Citas
- Crear citas con validación de disponibilidad
- Verificar que la hora esté dentro del horario del médico
- Ver horas disponibles en tiempo real
- Gestionar estados: Pendiente → Confirmada → Completada / Cancelada / No se presentó
- Agregar notas a cada cita
- Ver citas filtradas por fecha, paciente o médico

### Estadísticas
- Dashboard con resumen general del sistema
- Cantidad de pacientes, médicos y citas
- Distribución visual de citas por estado

---

## 🚀 Como ejecutar

### Requisitos
- Java 17 o superior instalado

### Pasos de compilación

```bash
# 1. Crear carpetas de compilación
mkdir -p out

# 2. Compilar todos los archivos .java
javac -d out src/modelo/*.java src/servicio/*.java src/util/*.java src/menu/*.java src/Main.java

# 3. Ejecutar el programa
java -cp out Main
```

---

## 🛠️ Tecnologías usadas
- **Java 17** (switch expressions, var, texto multilínea)
- **java.time API** (LocalDate, LocalTime) para manejo de fechas
- **Java Streams** para filtrado y búsquedas
- **Programación orientada a objetos** (herencia, encapsulamiento, polimorfismo)
- **Enum** para control de estados

---

## 📸 Datos de ejemplo pre-cargados

Al iniciar el sistema se cargan automáticamente:

**Pacientes:**
| ID   | Nombre         | Edad |
|------|----------------|------|
| P001 | María López    | 35   |
| P002 | Carlos Rodríguez| 48  |
| P003 | Ana Martínez   | 27   |

**Médicos:**
| ID   | Nombre            | Especialidad     | Horario       |
|------|-------------------|------------------|---------------|
| M001 | Dr. Juan Pérez    | Medicina General | 08:00 - 17:00 |
| M002 | Dra. Laura García | Pediatría        | 09:00 - 18:00 |
| M003 | Dr. Roberto Sánchez| Odontología     | 07:00 - 16:00 |

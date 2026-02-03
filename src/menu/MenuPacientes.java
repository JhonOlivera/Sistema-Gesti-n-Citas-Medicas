package menu;

import modelo.Paciente;
import servicio.PacienteServicio;
import util.Consola;

import java.util.List;

public class MenuPacientes {
    private PacienteServicio servicio;

    public MenuPacientes(PacienteServicio servicio) {
        this.servicio = servicio;
    }

    public void ejecutar() {
        boolean salir = false;

        while (!salir) {
            Consola.titulo("GESTIÓN DE PACIENTES");
            Consola.opcion(1, "Registrar nuevo paciente");
            Consola.opcion(2, "Ver todos los pacientes");
            Consola.opcion(3, "Buscar paciente");
            Consola.opcion(4, "Actualizar paciente");
            Consola.opcion(5, "Eliminar paciente");
            Consola.opcion(0, "Volver al menú principal");

            String opcion = Consola.pedirTexto("\n  Seleccione una opción");

            switch (opcion) {
                case "1": registrarPaciente(); break;
                case "2": verPacientes(); break;
                case "3": buscarPaciente(); break;
                case "4": actualizarPaciente(); break;
                case "5": eliminarPaciente(); break;
                case "0": salir = true; break;
                default: Consola.error("Opción no válida.");
            }
        }
    }

    // ─── Registrar paciente ──────────────────────────────────
    private void registrarPaciente() {
        Consola.subtitulo("Nuevo Paciente");

        String nombre = Consola.pedirTexto("Nombre");
        String apellido = Consola.pedirTexto("Apellido");
        String telefono = Consola.pedirTexto("Teléfono");
        String correo = Consola.pedirTexto("Correo electrónico");
        int edad = Consola.pedirEntero("Edad");

        System.out.println("\n  Género:");
        Consola.opcion(1, "Masculino");
        Consola.opcion(2, "Femenino");
        Consola.opcion(3, "Otro");
        String generoOpcion = Consola.pedirTexto("Seleccione");
        String genero = switch (generoOpcion) {
            case "1" -> "Masculino";
            case "2" -> "Femenino";
            default  -> "Otro";
        };

        Paciente paciente = servicio.agregarPaciente(nombre, apellido, telefono, correo, edad, genero);
        Consola.exito("Paciente registrado exitosamente.");
        Consola.info("ID asignado: " + paciente.getId());
        Consola.pausar();
    }

    // ─── Ver todos los pacientes ─────────────────────────────
    private void verPacientes() {
        Consola.subtitulo("Lista de Pacientes");
        List<Paciente> pacientes = servicio.obtenerTodos();

        if (pacientes.isEmpty()) {
            Consola.info("No hay pacientes registrados.");
        } else {
            System.out.println();
            for (Paciente p : pacientes) {
                System.out.println("  " + p);
            }
            Consola.info("Total de pacientes: " + servicio.cantidadPacientes());
        }
        Consola.pausar();
    }

    // ─── Buscar paciente ─────────────────────────────────────
    private void buscarPaciente() {
        Consola.subtitulo("Buscar Paciente");
        String nombre = Consola.pedirTexto("Ingrese nombre o apellido a buscar");
        List<Paciente> resultados = servicio.buscarPorNombre(nombre);

        if (resultados.isEmpty()) {
            Consola.error("No se encontraron pacientes con ese nombre.");
        } else {
            System.out.println("\n  Resultados encontrados:");
            for (Paciente p : resultados) {
                System.out.println("  " + p);
                Consola.info("Correo: " + p.getCorreo() + " | Género: " + p.getGenero());
                Consola.separador();
            }
        }
        Consola.pausar();
    }

    // ─── Actualizar paciente ─────────────────────────────────
    private void actualizarPaciente() {
        Consola.subtitulo("Actualizar Paciente");
        String id = Consola.pedirTexto("Ingrese el ID del paciente a actualizar");

        var paciente = servicio.buscarPorId(id);
        if (paciente.isPresent()) {
            Paciente p = paciente.get();
            Consola.info("Datos actuales: " + p);

            String nombre = Consola.pedirTexto("Nuevo nombre (o ENTER para mantener: " + p.getNombre() + ")");
            String apellido = Consola.pedirTexto("Nuevo apellido (o ENTER para mantener: " + p.getApellido() + ")");
            String telefono = Consola.pedirTexto("Nuevo teléfono (o ENTER para mantener: " + p.getTelefono() + ")");
            String correo = Consola.pedirTexto("Nuevo correo (o ENTER para mantener: " + p.getCorreo() + ")");

            servicio.actualizarPaciente(id,
                    nombre.isEmpty() ? p.getNombre() : nombre,
                    apellido.isEmpty() ? p.getApellido() : apellido,
                    telefono.isEmpty() ? p.getTelefono() : telefono,
                    correo.isEmpty() ? p.getCorreo() : correo,
                    p.getEdad(),
                    p.getGenero()
            );
            Consola.exito("Paciente actualizado correctamente.");
        } else {
            Consola.error("No se encontró un paciente con ese ID.");
        }
        Consola.pausar();
    }

    // ─── Eliminar paciente ───────────────────────────────────
    private void eliminarPaciente() {
        Consola.subtitulo("Eliminar Paciente");
        String id = Consola.pedirTexto("Ingrese el ID del paciente a eliminar");

        var paciente = servicio.buscarPorId(id);
        if (paciente.isPresent()) {
            Consola.info("Paciente encontrado: " + paciente.get());
            if (Consola.pedirConfirmacion("¿Está seguro que desea eliminar este paciente?")) {
                servicio.eliminarPaciente(id);
                Consola.exito("Paciente eliminado exitosamente.");
            } else {
                Consola.info("Operación cancelada.");
            }
        } else {
            Consola.error("No se encontró un paciente con ese ID.");
        }
        Consola.pausar();
    }
}

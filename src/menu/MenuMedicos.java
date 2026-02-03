package menu;

import modelo.Medico;
import servicio.MedicoServicio;
import util.Consola;

import java.util.List;

public class MenuMedicos {
    private MedicoServicio servicio;

    public MenuMedicos(MedicoServicio servicio) {
        this.servicio = servicio;
    }

    public void ejecutar() {
        boolean salir = false;

        while (!salir) {
            Consola.titulo("GESTIÓN DE MÉDICOS");
            Consola.opcion(1, "Registrar nuevo médico");
            Consola.opcion(2, "Ver todos los médicos");
            Consola.opcion(3, "Buscar por especialidad");
            Consola.opcion(4, "Actualizar médico");
            Consola.opcion(5, "Eliminar médico");
            Consola.opcion(0, "Volver al menú principal");

            String opcion = Consola.pedirTexto("\n  Seleccione una opción");

            switch (opcion) {
                case "1": registrarMedico(); break;
                case "2": verMedicos(); break;
                case "3": buscarPorEspecialidad(); break;
                case "4": actualizarMedico(); break;
                case "5": eliminarMedico(); break;
                case "0": salir = true; break;
                default: Consola.error("Opción no válida.");
            }
        }
    }

    // ─── Registrar médico ────────────────────────────────────
    private void registrarMedico() {
        Consola.subtitulo("Nuevo Médico");

        String nombre = Consola.pedirTexto("Nombre");
        String apellido = Consola.pedirTexto("Apellido");
        String especialidad = Consola.pedirTexto("Especialidad");
        String telefono = Consola.pedirTexto("Teléfono");
        String horarioInicio = Consola.pedirTexto("Hora de inicio (ejemplo: 08:00)");
        String horarioFin = Consola.pedirTexto("Hora de fin (ejemplo: 17:00)");

        Medico medico = servicio.agregarMedico(nombre, apellido, especialidad, telefono, horarioInicio, horarioFin);
        Consola.exito("Médico registrado exitosamente.");
        Consola.info("ID asignado: " + medico.getId());
        Consola.pausar();
    }

    // ─── Ver todos los médicos ───────────────────────────────
    private void verMedicos() {
        Consola.subtitulo("Lista de Médicos");
        List<Medico> medicos = servicio.obtenerTodos();

        if (medicos.isEmpty()) {
            Consola.info("No hay médicos registrados.");
        } else {
            System.out.println();
            for (Medico m : medicos) {
                System.out.println("  " + m);
                Consola.info("Tel: " + m.getTelefono());
                Consola.separador();
            }
            Consola.info("Total de médicos: " + servicio.cantidadMedicos());
        }
        Consola.pausar();
    }

    // ─── Buscar por especialidad ─────────────────────────────
    private void buscarPorEspecialidad() {
        Consola.subtitulo("Buscar por Especialidad");
        String especialidad = Consola.pedirTexto("Ingrese la especialidad a buscar");
        List<Medico> resultados = servicio.buscarPorEspecialidad(especialidad);

        if (resultados.isEmpty()) {
            Consola.error("No se encontraron médicos con esa especialidad.");
        } else {
            System.out.println("\n  Resultados:");
            for (Medico m : resultados) {
                System.out.println("  " + m);
            }
        }
        Consola.pausar();
    }

    // ─── Actualizar médico ───────────────────────────────────
    private void actualizarMedico() {
        Consola.subtitulo("Actualizar Médico");
        String id = Consola.pedirTexto("Ingrese el ID del médico a actualizar");

        var medico = servicio.buscarPorId(id);
        if (medico.isPresent()) {
            Medico m = medico.get();
            Consola.info("Datos actuales: " + m);

            String nombre = Consola.pedirTexto("Nuevo nombre (o ENTER para mantener: " + m.getNombre() + ")");
            String apellido = Consola.pedirTexto("Nuevo apellido (o ENTER para mantener: " + m.getApellido() + ")");
            String especialidad = Consola.pedirTexto("Nueva especialidad (o ENTER para mantener: " + m.getEspecialidad() + ")");

            servicio.actualizarMedico(id,
                    nombre.isEmpty() ? m.getNombre() : nombre,
                    apellido.isEmpty() ? m.getApellido() : apellido,
                    especialidad.isEmpty() ? m.getEspecialidad() : especialidad,
                    m.getTelefono(),
                    m.getHorarioInicio(),
                    m.getHorarioFin()
            );
            Consola.exito("Médico actualizado correctamente.");
        } else {
            Consola.error("No se encontró un médico con ese ID.");
        }
        Consola.pausar();
    }

    // ─── Eliminar médico ─────────────────────────────────────
    private void eliminarMedico() {
        Consola.subtitulo("Eliminar Médico");
        String id = Consola.pedirTexto("Ingrese el ID del médico a eliminar");

        var medico = servicio.buscarPorId(id);
        if (medico.isPresent()) {
            Consola.info("Médico encontrado: " + medico.get());
            if (Consola.pedirConfirmacion("¿Está seguro que desea eliminar este médico?")) {
                servicio.eliminarMedico(id);
                Consola.exito("Médico eliminado exitosamente.");
            } else {
                Consola.info("Operación cancelada.");
            }
        } else {
            Consola.error("No se encontró un médico con ese ID.");
        }
        Consola.pausar();
    }
}

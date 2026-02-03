package menu;

import modelo.*;
import servicio.CitaServicio;
import servicio.MedicoServicio;
import servicio.PacienteServicio;
import util.Consola;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MenuCitas {
    private CitaServicio citaServicio;
    private PacienteServicio pacienteServicio;
    private MedicoServicio medicoServicio;

    public MenuCitas(CitaServicio citaServicio, PacienteServicio pacienteServicio, MedicoServicio medicoServicio) {
        this.citaServicio = citaServicio;
        this.pacienteServicio = pacienteServicio;
        this.medicoServicio = medicoServicio;
    }

    public void ejecutar() {
        boolean salir = false;

        while (!salir) {
            Consola.titulo("GESTIÓN DE CITAS");
            Consola.opcion(1, "Crear nueva cita");
            Consola.opcion(2, "Ver todas las citas");
            Consola.opcion(3, "Ver citas por fecha");
            Consola.opcion(4, "Ver citas de un paciente");
            Consola.opcion(5, "Ver agenda de un médico");
            Consola.opcion(6, "Ver horas disponibles de un médico");
            Consola.opcion(7, "Cambiar estado de una cita");
            Consola.opcion(8, "Agregar notas a una cita");
            Consola.opcion(0, "Volver al menú principal");

            String opcion = Consola.pedirTexto("\n  Seleccione una opción");

            switch (opcion) {
                case "1": crearCita(); break;
                case "2": verCitas(); break;
                case "3": verCitasPorFecha(); break;
                case "4": verCitasPaciente(); break;
                case "5": verAgendaMedico(); break;
                case "6": verHorasDisponibles(); break;
                case "7": cambiarEstado(); break;
                case "8": agregarNotas(); break;
                case "0": salir = true; break;
                default: Consola.error("Opción no válida.");
            }
        }
    }

    // ─── Crear nueva cita ────────────────────────────────────
    private void crearCita() {
        Consola.subtitulo("Nueva Cita");

        // Seleccionar paciente
        Consola.info("Pacientes registrados:");
        pacienteServicio.obtenerTodos().forEach(p -> System.out.println("    " + p));
        String idPaciente = Consola.pedirTexto("Ingrese el ID del paciente");
        var paciente = pacienteServicio.buscarPorId(idPaciente);
        if (paciente.isEmpty()) {
            Consola.error("Paciente no encontrado.");
            Consola.pausar();
            return;
        }

        // Seleccionar médico
        Consola.info("\nMédicos disponibles:");
        medicoServicio.obtenerTodos().forEach(m -> System.out.println("    " + m));
        String idMedico = Consola.pedirTexto("Ingrese el ID del médico");
        var medico = medicoServicio.buscarPorId(idMedico);
        if (medico.isEmpty()) {
            Consola.error("Médico no encontrado.");
            Consola.pausar();
            return;
        }

        // Fecha y hora
        LocalDate fecha = Consola.pedirFecha("Fecha de la cita");

        // Mostrar horas disponibles
        List<String> horasDisponibles = citaServicio.obtenerHorasDisponibles(medico.get(), fecha);
        if (horasDisponibles.isEmpty()) {
            Consola.error("El médico no tiene horas disponibles en esa fecha.");
            Consola.pausar();
            return;
        }
        Consola.info("Horas disponibles: " + horasDisponibles);

        LocalTime hora = Consola.pedirHora("Hora de la cita");
        String motivo = Consola.pedirTexto("Motivo de la consulta");

        // Crear la cita
        String resultado = citaServicio.crearCita(paciente.get(), medico.get(), fecha, hora, motivo);

        if (resultado.startsWith("EXITO")) {
            Consola.exito(resultado.replace("EXITO: ", ""));
        } else {
            Consola.error(resultado.replace("ERROR: ", ""));
        }
        Consola.pausar();
    }

    // ─── Ver todas las citas ─────────────────────────────────
    private void verCitas() {
        Consola.subtitulo("Todas las Citas");
        List<Cita> citas = citaServicio.obtenerTodas();

        if (citas.isEmpty()) {
            Consola.info("No hay citas registradas.");
        } else {
            System.out.println();
            for (Cita c : citas) {
                mostrarCita(c);
            }
        }
        Consola.pausar();
    }

    // ─── Ver citas por fecha ─────────────────────────────────
    private void verCitasPorFecha() {
        Consola.subtitulo("Citas por Fecha");
        LocalDate fecha = Consola.pedirFecha("Ingrese la fecha");
        List<Cita> citas = citaServicio.buscarPorFecha(fecha);

        if (citas.isEmpty()) {
            Consola.info("No hay citas en esa fecha.");
        } else {
            System.out.println();
            for (Cita c : citas) {
                mostrarCita(c);
            }
        }
        Consola.pausar();
    }

    // ─── Ver citas de un paciente ────────────────────────────
    private void verCitasPaciente() {
        Consola.subtitulo("Citas de un Paciente");
        Consola.info("Pacientes registrados:");
        pacienteServicio.obtenerTodos().forEach(p -> System.out.println("    " + p));

        String idPaciente = Consola.pedirTexto("Ingrese el ID del paciente");
        var paciente = pacienteServicio.buscarPorId(idPaciente);

        if (paciente.isEmpty()) {
            Consola.error("Paciente no encontrado.");
        } else {
            List<Cita> citas = citaServicio.buscarPorPaciente(idPaciente);
            if (citas.isEmpty()) {
                Consola.info(paciente.get().getNombreCompleto() + " no tiene citas registradas.");
            } else {
                Consola.info("Citas de " + paciente.get().getNombreCompleto() + ":");
                for (Cita c : citas) {
                    mostrarCita(c);
                }
            }
        }
        Consola.pausar();
    }

    // ─── Ver agenda de un médico ─────────────────────────────
    private void verAgendaMedico() {
        Consola.subtitulo("Agenda del Médico");
        Consola.info("Médicos registrados:");
        medicoServicio.obtenerTodos().forEach(m -> System.out.println("    " + m));

        String idMedico = Consola.pedirTexto("Ingrese el ID del médico");
        var medico = medicoServicio.buscarPorId(idMedico);

        if (medico.isEmpty()) {
            Consola.error("Médico no encontrado.");
        } else {
            List<Cita> citas = citaServicio.buscarPorMedico(idMedico);
            if (citas.isEmpty()) {
                Consola.info("Dr. " + medico.get().getNombreCompleto() + " no tiene citas registradas.");
            } else {
                Consola.info("Agenda del Dr. " + medico.get().getNombreCompleto() + ":");
                for (Cita c : citas) {
                    mostrarCita(c);
                }
            }
        }
        Consola.pausar();
    }

    // ─── Ver horas disponibles ───────────────────────────────
    private void verHorasDisponibles() {
        Consola.subtitulo("Horas Disponibles");
        Consola.info("Médicos registrados:");
        medicoServicio.obtenerTodos().forEach(m -> System.out.println("    " + m));

        String idMedico = Consola.pedirTexto("Ingrese el ID del médico");
        var medico = medicoServicio.buscarPorId(idMedico);

        if (medico.isEmpty()) {
            Consola.error("Médico no encontrado.");
        } else {
            LocalDate fecha = Consola.pedirFecha("Ingrese la fecha");
            List<String> horas = citaServicio.obtenerHorasDisponibles(medico.get(), fecha);

            if (horas.isEmpty()) {
                Consola.info("No hay horas disponibles en esa fecha.");
            } else {
                Consola.info("Horas disponibles para el Dr. " + medico.get().getNombreCompleto() + " el " + fecha + ":");
                Consola.info(horas.toString());
            }
        }
        Consola.pausar();
    }

    // ─── Cambiar estado ──────────────────────────────────────
    private void cambiarEstado() {
        Consola.subtitulo("Cambiar Estado de Cita");
        String idCita = Consola.pedirTexto("Ingrese el ID de la cita");

        var cita = citaServicio.buscarPorId(idCita);
        if (cita.isEmpty()) {
            Consola.error("Cita no encontrada.");
            Consola.pausar();
            return;
        }

        Consola.info("Cita actual: " + cita.get());
        Consola.info("Estado actual: " + cita.get().getEstado());

        System.out.println("\n  Seleccione el nuevo estado:");
        Consola.opcion(1, "Confirmada");
        Consola.opcion(2, "Cancelada");
        Consola.opcion(3, "Completada");
        Consola.opcion(4, "No se presentó");

        String opcion = Consola.pedirTexto("Opción");
        EstadoCita nuevoEstado = switch (opcion) {
            case "1" -> EstadoCita.CONFIRMADA;
            case "2" -> EstadoCita.CANCELADA;
            case "3" -> EstadoCita.COMPLETADA;
            case "4" -> EstadoCita.NO_PRESENTO;
            default  -> null;
        };

        if (nuevoEstado == null) {
            Consola.error("Opción no válida.");
        } else {
            String resultado = citaServicio.cambiarEstado(idCita, nuevoEstado);
            if (resultado.startsWith("EXITO")) {
                Consola.exito(resultado.replace("EXITO: ", ""));
            } else {
                Consola.error(resultado.replace("ERROR: ", ""));
            }
        }
        Consola.pausar();
    }

    // ─── Agregar notas ───────────────────────────────────────
    private void agregarNotas() {
        Consola.subtitulo("Agregar Notas");
        String idCita = Consola.pedirTexto("Ingrese el ID de la cita");

        var cita = citaServicio.buscarPorId(idCita);
        if (cita.isEmpty()) {
            Consola.error("Cita no encontrada.");
        } else {
            Consola.info("Cita: " + cita.get());
            String notas = Consola.pedirTexto("Ingrese las notas");
            String resultado = citaServicio.agregarNotas(idCita, notas);
            Consola.exito(resultado.replace("EXITO: ", ""));
        }
        Consola.pausar();
    }

    // ─── Método auxiliar para mostrar cita detallada ────────
    private void mostrarCita(Cita c) {
        System.out.println("\n    ┌─────────────────────────────────────────┐");
        System.out.println("    │  ID Cita   : " + c.getId());
        System.out.println("    │  Fecha     : " + c.getFechaFormateada());
        System.out.println("    │  Hora      : " + c.getHoraFormateada());
        System.out.println("    │  Paciente  : " + c.getPaciente().getNombreCompleto());
        System.out.println("    │  Médico    : Dr. " + c.getMedico().getNombreCompleto());
        System.out.println("    │  Motivo    : " + c.getMotivo());
        System.out.println("    │  Estado    : " + c.getEstado());
        if (!c.getNotas().isEmpty()) {
            System.out.println("    │  Notas     : " + c.getNotas());
        }
        System.out.println("    └─────────────────────────────────────────┘");
    }
}

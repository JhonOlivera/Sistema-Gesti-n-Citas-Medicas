package servicio;

import modelo.Cita;
import modelo.EstadoCita;
import modelo.Medico;
import modelo.Paciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CitaServicio {
    private List<Cita> citas;
    private int contador;

    public CitaServicio() {
        this.citas = new ArrayList<>();
        this.contador = 1;
    }

    // Genera un ID único para cada cita
    private String generarId() {
        return "C" + String.format("%03d", contador++);
    }

    // Validar que el médico no tenga otra cita en la misma fecha y hora
    public boolean estaDisponible(Medico medico, LocalDate fecha, LocalTime hora) {
        return citas.stream().noneMatch(c ->
                c.getMedico().getId().equals(medico.getId())
                        && c.getFecha().equals(fecha)
                        && c.getHora().equals(hora)
                        && (c.getEstado() == EstadoCita.PENDIENTE || c.getEstado() == EstadoCita.CONFIRMADA)
        );
    }

    // Validar que la hora esté dentro del horario del médico
    public boolean horaEnHorario(Medico medico, LocalTime hora) {
        LocalTime inicio = LocalTime.parse(medico.getHorarioInicio());
        LocalTime fin = LocalTime.parse(medico.getHorarioFin());
        return !hora.isBefore(inicio) && hora.isBefore(fin);
    }

    // Crear una nueva cita con validaciones
    public String crearCita(Paciente paciente, Medico medico, LocalDate fecha, LocalTime hora, String motivo) {
        if (fecha.isBefore(LocalDate.now())) {
            return "ERROR: No se puede crear una cita en una fecha pasada.";
        }
        if (!horaEnHorario(medico, hora)) {
            return "ERROR: La hora seleccionada está fuera del horario del médico (" + medico.getHorarioInicio() + " - " + medico.getHorarioFin() + ").";
        }
        if (!estaDisponible(medico, fecha, hora)) {
            return "ERROR: El médico no está disponible en esa fecha y hora.";
        }

        Cita cita = new Cita(generarId(), paciente, medico, fecha, hora, motivo);
        citas.add(cita);
        return "EXITO: Cita creada exitosamente. ID: " + cita.getId();
    }

    // Cambiar estado de una cita
    public String cambiarEstado(String idCita, EstadoCita nuevoEstado) {
        Optional<Cita> cita = buscarPorId(idCita);
        if (cita.isPresent()) {
            Cita c = cita.get();
            EstadoCita actual = c.getEstado();

            // Validar transiciones permitidas
            if (actual == EstadoCita.CANCELADA || actual == EstadoCita.COMPLETADA) {
                return "ERROR: No se puede modificar una cita que ya está " + actual.getDescripcion().toLowerCase() + ".";
            }
            c.setEstado(nuevoEstado);
            return "EXITO: Estado cambiado a " + nuevoEstado.getDescripcion();
        }
        return "ERROR: Cita no encontrada.";
    }

    // Agregar notas a una cita
    public String agregarNotas(String idCita, String notas) {
        Optional<Cita> cita = buscarPorId(idCita);
        if (cita.isPresent()) {
            cita.get().setNotas(notas);
            return "EXITO: Notas agregadas.";
        }
        return "ERROR: Cita no encontrada.";
    }

    // Buscar por ID
    public Optional<Cita> buscarPorId(String id) {
        return citas.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    // Buscar citas por paciente
    public List<Cita> buscarPorPaciente(String idPaciente) {
        return citas.stream()
                .filter(c -> c.getPaciente().getId().equals(idPaciente))
                .collect(Collectors.toList());
    }

    // Buscar citas por médico
    public List<Cita> buscarPorMedico(String idMedico) {
        return citas.stream()
                .filter(c -> c.getMedico().getId().equals(idMedico))
                .collect(Collectors.toList());
    }

    // Buscar citas por fecha
    public List<Cita> buscarPorFecha(LocalDate fecha) {
        return citas.stream()
                .filter(c -> c.getFecha().equals(fecha))
                .sorted((a, b) -> a.getHora().compareTo(b.getHora()))
                .collect(Collectors.toList());
    }

    // Buscar citas por estado
    public List<Cita> buscarPorEstado(EstadoCita estado) {
        return citas.stream()
                .filter(c -> c.getEstado() == estado)
                .collect(Collectors.toList());
    }

    // Obtener horarios disponibles de un médico en una fecha
    public List<String> obtenerHorasDisponibles(Medico medico, LocalDate fecha) {
        List<String> horasDisponibles = new ArrayList<>();
        LocalTime inicio = LocalTime.parse(medico.getHorarioInicio());
        LocalTime fin = LocalTime.parse(medico.getHorarioFin());
        LocalTime actual = inicio;

        while (actual.isBefore(fin)) {
            if (estaDisponible(medico, fecha, actual)) {
                horasDisponibles.add(actual.toString());
            }
            actual = actual.plusMinutes(30);
        }
        return horasDisponibles;
    }

    // Obtener todas las citas
    public List<Cita> obtenerTodas() {
        return new ArrayList<>(citas);
    }

    // Estadísticas básicas
    public long cantidadPorEstado(EstadoCita estado) {
        return citas.stream().filter(c -> c.getEstado() == estado).count();
    }

    public int cantidadTotal() {
        return citas.size();
    }
}

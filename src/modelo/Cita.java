package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cita {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private EstadoCita estado;
    private String notas;

    public Cita(String id, Paciente paciente, Medico medico, LocalDate fecha, LocalTime hora, String motivo) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = EstadoCita.PENDIENTE;
        this.notas = "";
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getHoraFormateada() {
        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String toString() {
        return "[CITA " + id + "] " + getFechaFormateada() + " " + getHoraFormateada()
                + " | Paciente: " + paciente.getNombreCompleto()
                + " | Dr. " + medico.getNombreCompleto()
                + " | Estado: " + estado;
    }
}

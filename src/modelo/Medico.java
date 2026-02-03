package modelo;

public class Medico {
    private String id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String horarioInicio;  // Ej: "08:00"
    private String horarioFin;     // Ej: "18:00"

    public Medico(String id, String nombre, String apellido, String especialidad, String telefono, String horarioInicio, String horarioFin) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(String horarioInicio) { this.horarioInicio = horarioInicio; }

    public String getHorarioFin() { return horarioFin; }
    public void setHorarioFin(String horarioFin) { this.horarioFin = horarioFin; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "[ID: " + id + "] Dr. " + getNombreCompleto() + " | Especialidad: " + especialidad + " | Horario: " + horarioInicio + " - " + horarioFin;
    }
}

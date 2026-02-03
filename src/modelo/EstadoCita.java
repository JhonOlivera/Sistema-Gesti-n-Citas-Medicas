package modelo;

public enum EstadoCita {
    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada"),
    COMPLETADA("Completada"),
    NO_PRESENTO("No se presentó");

    private final String descripcion;

    EstadoCita(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

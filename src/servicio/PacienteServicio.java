package servicio;

import modelo.Paciente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PacienteServicio {
    private List<Paciente> pacientes;
    private int contador;

    public PacienteServicio() {
        this.pacientes = new ArrayList<>();
        this.contador = 1;
        cargarPacientesEjemplo();
    }

    private void cargarPacientesEjemplo() {
        pacientes.add(new Paciente("P001", "María", "López", "3101234567", "maria.lopez@email.com", 35, "Femenino"));
        pacientes.add(new Paciente("P002", "Carlos", "Rodríguez", "3209876543", "carlos.r@email.com", 48, "Masculino"));
        pacientes.add(new Paciente("P003", "Ana", "Martínez", "3115554433", "ana.martinez@email.com", 27, "Femenino"));
        contador = 4;
    }

    public Paciente agregarPaciente(String nombre, String apellido, String telefono, String correo, int edad, String genero) {
        String id = "P" + String.format("%03d", contador++);
        Paciente paciente = new Paciente(id, nombre, apellido, telefono, correo, edad, genero);
        pacientes.add(paciente);
        return paciente;
    }

    public boolean eliminarPaciente(String id) {
        return pacientes.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Paciente> buscarPorId(String id) {
        return pacientes.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Paciente> buscarPorNombre(String nombre) {
        return pacientes.stream()
                .filter(p -> p.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Paciente> obtenerTodos() {
        return new ArrayList<>(pacientes);
    }

    public boolean actualizarPaciente(String id, String nombre, String apellido, String telefono, String correo, int edad, String genero) {
        Optional<Paciente> paciente = buscarPorId(id);
        if (paciente.isPresent()) {
            Paciente p = paciente.get();
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setTelefono(telefono);
            p.setCorreo(correo);
            p.setEdad(edad);
            p.setGenero(genero);
            return true;
        }
        return false;
    }

    public int cantidadPacientes() {
        return pacientes.size();
    }
}

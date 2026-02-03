package servicio;

import modelo.Medico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedicoServicio {
    private List<Medico> medicos;
    private int contador;

    public MedicoServicio() {
        this.medicos = new ArrayList<>();
        this.contador = 1;
        cargarMedicosEjemplo();
    }

    private void cargarMedicosEjemplo() {
        medicos.add(new Medico("M001", "Juan", "Pérez", "Medicina General", "3101112233", "08:00", "17:00"));
        medicos.add(new Medico("M002", "Laura", "García", "Pediatría", "3204455667", "09:00", "18:00"));
        medicos.add(new Medico("M003", "Roberto", "Sánchez", "Odontología", "3118889900", "07:00", "16:00"));
        contador = 4;
    }

    public Medico agregarMedico(String nombre, String apellido, String especialidad, String telefono, String horarioInicio, String horarioFin) {
        String id = "M" + String.format("%03d", contador++);
        Medico medico = new Medico(id, nombre, apellido, especialidad, telefono, horarioInicio, horarioFin);
        medicos.add(medico);
        return medico;
    }

    public boolean eliminarMedico(String id) {
        return medicos.removeIf(m -> m.getId().equals(id));
    }

    public Optional<Medico> buscarPorId(String id) {
        return medicos.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public List<Medico> buscarPorEspecialidad(String especialidad) {
        return medicos.stream()
                .filter(m -> m.getEspecialidad().toLowerCase().contains(especialidad.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Medico> obtenerTodos() {
        return new ArrayList<>(medicos);
    }

    public boolean actualizarMedico(String id, String nombre, String apellido, String especialidad, String telefono, String horarioInicio, String horarioFin) {
        Optional<Medico> medico = buscarPorId(id);
        if (medico.isPresent()) {
            Medico m = medico.get();
            m.setNombre(nombre);
            m.setApellido(apellido);
            m.setEspecialidad(especialidad);
            m.setTelefono(telefono);
            m.setHorarioInicio(horarioInicio);
            m.setHorarioFin(horarioFin);
            return true;
        }
        return false;
    }

    public int cantidadMedicos() {
        return medicos.size();
    }
}

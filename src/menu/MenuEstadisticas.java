package menu;

import modelo.EstadoCita;
import servicio.CitaServicio;
import servicio.MedicoServicio;
import servicio.PacienteServicio;
import util.Consola;

public class MenuEstadisticas {
    private CitaServicio citaServicio;
    private PacienteServicio pacienteServicio;
    private MedicoServicio medicoServicio;

    public MenuEstadisticas(CitaServicio citaServicio, PacienteServicio pacienteServicio, MedicoServicio medicoServicio) {
        this.citaServicio = citaServicio;
        this.pacienteServicio = pacienteServicio;
        this.medicoServicio = medicoServicio;
    }

    public void ejecutar() {
        Consola.titulo("DASHBOARD DE ESTADÍSTICAS");

        System.out.println("\n  ╔═══════════════════════════════════════════╗");
        System.out.println("  ║         RESUMEN GENERAL DEL SISTEMA       ║");
        System.out.println("  ╠═══════════════════════════════════════════╣");
        System.out.println("  ║  👤 Pacientes registrados  : " + formatear(pacienteServicio.cantidadPacientes()) + "          ║");
        System.out.println("  ║  👨‍⚕️ Médicos registrados   : " + formatear(medicoServicio.cantidadMedicos()) + "          ║");
        System.out.println("  ║  📅 Citas totales          : " + formatear(citaServicio.cantidadTotal()) + "          ║");
        System.out.println("  ╚═══════════════════════════════════════════╝");

        System.out.println("\n  ╔═══════════════════════════════════════════╗");
        System.out.println("  ║           CITAS POR ESTADO                ║");
        System.out.println("  ╠═══════════════════════════════════════════╣");
        System.out.println("  ║  🟡 Pendientes             : " + formatear((int) citaServicio.cantidadPorEstado(EstadoCita.PENDIENTE)) + "          ║");
        System.out.println("  ║  🟢 Confirmadas            : " + formatear((int) citaServicio.cantidadPorEstado(EstadoCita.CONFIRMADA)) + "          ║");
        System.out.println("  ║  🔴 Canceladas             : " + formatear((int) citaServicio.cantidadPorEstado(EstadoCita.CANCELADA)) + "          ║");
        System.out.println("  ║  ✅ Completadas            : " + formatear((int) citaServicio.cantidadPorEstado(EstadoCita.COMPLETADA)) + "          ║");
        System.out.println("  ║  ⛔ No se presentó         : " + formatear((int) citaServicio.cantidadPorEstado(EstadoCita.NO_PRESENTO)) + "          ║");
        System.out.println("  ╚═══════════════════════════════════════════╝");

        // Barra visual de estados
        int total = citaServicio.cantidadTotal();
        if (total > 0) {
            Consola.subtitulo("Distribución Visual de Citas");
            mostrarBarra("Pendientes", (int) citaServicio.cantidadPorEstado(EstadoCita.PENDIENTE), total, "🟡");
            mostrarBarra("Confirmadas", (int) citaServicio.cantidadPorEstado(EstadoCita.CONFIRMADA), total, "🟢");
            mostrarBarra("Canceladas", (int) citaServicio.cantidadPorEstado(EstadoCita.CANCELADA), total, "🔴");
            mostrarBarra("Completadas", (int) citaServicio.cantidadPorEstado(EstadoCita.COMPLETADA), total, "✅");
            mostrarBarra("No presentó", (int) citaServicio.cantidadPorEstado(EstadoCita.NO_PRESENTO), total, "⛔");
        }

        Consola.pausar();
    }

    private String formatear(int numero) {
        return String.valueOf(numero);
    }

    private void mostrarBarra(String etiqueta, int valor, int total, String icono) {
        int longitud = (valor * 30) / total;
        String barra = "█".repeat(Math.max(longitud, 0));
        String vacio = "░".repeat(Math.max(30 - longitud, 0));
        System.out.printf("  %s %-12s |%s%s| %d%n", icono, etiqueta, barra, vacio, valor);
    }
}

import menu.MenuCitas;
import menu.MenuEstadisticas;
import menu.MenuMedicos;
import menu.MenuPacientes;
import servicio.CitaServicio;
import servicio.MedicoServicio;
import servicio.PacienteServicio;
import util.Consola;

public class Main {

    // Servicios compartidos en toda la aplicación
    private static PacienteServicio pacienteServicio = new PacienteServicio();
    private static MedicoServicio medicoServicio = new MedicoServicio();
    private static CitaServicio citaServicio = new CitaServicio();

    public static void main(String[] args) {
        mostrarBienvenida();
        ejecutarMenu();
    }

    // ─── Pantalla de bienvenida ──────────────────────────────
    private static void mostrarBienvenida() {
        Consola.limpiar();
        System.out.println("\n\n");
        System.out.println("  ╔═══════════════════════════════════════════════════════╗");
        System.out.println("  ║                                                       ║");
        System.out.println("  ║     🏥  SISTEMA DE GESTIÓN DE CITAS MÉDICAS  🏥       ║");
        System.out.println("  ║                                                       ║");
        System.out.println("  ║           Desarrollado en Java                        ║");
        System.out.println("  ║           Versión 1.0                                 ║");
        System.out.println("  ║                                                       ║");
        System.out.println("  ╚═══════════════════════════════════════════════════════╝");
        System.out.println("\n  Sistema cargado correctamente.");
        System.out.println("  Datos de ejemplo pre-cargados: 3 pacientes y 3 médicos.");
        Consola.pausar();
    }

    // ─── Menú principal ──────────────────────────────────────
    private static void ejecutarMenu() {
        boolean salir = false;

        while (!salir) {
            Consola.limpiar();
            Consola.titulo("MENÚ PRINCIPAL");
            Consola.opcion(1, "Gestión de Pacientes");
            Consola.opcion(2, "Gestión de Médicos");
            Consola.opcion(3, "Gestión de Citas");
            Consola.opcion(4, "Estadísticas / Dashboard");
            Consola.opcion(0, "Salir del sistema");

            String opcion = Consola.pedirTexto("\n  Seleccione una opción");

            switch (opcion) {
                case "1":
                    new MenuPacientes(pacienteServicio).ejecutar();
                    break;
                case "2":
                    new MenuMedicos(medicoServicio).ejecutar();
                    break;
                case "3":
                    new MenuCitas(citaServicio, pacienteServicio, medicoServicio).ejecutar();
                    break;
                case "4":
                    new MenuEstadisticas(citaServicio, pacienteServicio, medicoServicio).ejecutar();
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    Consola.error("Opción no válida. Por favor intente nuevamente.");
                    Consola.pausar();
            }
        }

        Consola.titulo("¡Gracias por usar el Sistema de Citas Médicas!");
        System.out.println("  Sistema cerrado correctamente.\n");
    }
}

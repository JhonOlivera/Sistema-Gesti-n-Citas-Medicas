package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Consola {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String LINEA = "═══════════════════════════════════════════════════════════";
    private static final String LINEA_SIMPLE = "───────────────────────────────────────────────────────────";

    // ─── Formateo de títulos ─────────────────────────────────
    public static void titulo(String texto) {
        System.out.println("\n" + LINEA);
        System.out.println("  ✦ " + texto);
        System.out.println(LINEA);
    }

    public static void subtitulo(String texto) {
        System.out.println("\n  " + LINEA_SIMPLE);
        System.out.println("  → " + texto);
        System.out.println("  " + LINEA_SIMPLE);
    }

    public static void separador() {
        System.out.println(LINEA_SIMPLE);
    }

    // ─── Mensajes ────────────────────────────────────────────
    public static void exito(String mensaje) {
        System.out.println("\n  ✔ " + mensaje);
    }

    public static void error(String mensaje) {
        System.out.println("\n  ✘ " + mensaje);
    }

    public static void info(String mensaje) {
        System.out.println("  ℹ " + mensaje);
    }

    public static void opcion(int numero, String texto) {
        System.out.println("    " + numero + ". " + texto);
    }

    // ─── Entrada de datos ────────────────────────────────────
    public static String pedirTexto(String mensaje) {
        System.out.print("  » " + mensaje + ": ");
        return scanner.nextLine().trim();
    }

    public static int pedirEntero(String mensaje) {
        while (true) {
            try {
                System.out.print("  » " + mensaje + ": ");
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                error("Por favor ingrese un número válido.");
            }
        }
    }

    public static LocalDate pedirFecha(String mensaje) {
        while (true) {
            try {
                String fecha = pedirTexto(mensaje + " (dd/MM/yyyy)");
                return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                error("Formato de fecha inválido. Use: dd/MM/yyyy (ejemplo: 15/02/2025)");
            }
        }
    }

    public static LocalTime pedirHora(String mensaje) {
        while (true) {
            try {
                String hora = pedirTexto(mensaje + " (HH:mm)");
                return LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                error("Formato de hora inválido. Use: HH:mm (ejemplo: 10:30)");
            }
        }
    }

    public static boolean pedirConfirmacion(String mensaje) {
        String respuesta = pedirTexto(mensaje + " (s/n)").toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
    }

    public static void pausar() {
        pedirTexto("\n  Presione ENTER para continuar...");
    }

    public static void limpiar() {
        System.out.print("\033[H\033[2J");
    }
}

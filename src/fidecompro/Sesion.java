package fidecompro;

public class Sesion {
    // Usuario que inició sesión (se asigna en VentanaLogin)
    public static Usuario usuarioActual = null;

    // Ayuda: devuelve el username o "Invitado" si no hay sesión
    public static String nombre() {
        return (usuarioActual != null ? usuarioActual.getUsername() : "Invitado");
    }

    // Útil para proteger ventanas si no hay login
    public static boolean haySesion() {
        return usuarioActual != null;
    }
}


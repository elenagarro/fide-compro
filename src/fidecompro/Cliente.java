
package fidecompro;

/**
 *
 * @author rgarr
 */
public class Cliente {
    private String nombre;
    private String cedula;
    private String direccion;
    private String telefono;

    public Cliente(String nombre, String cedula, String direccion, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Mostrar datos del cliente
    public String mostrarDatos() {
        return "Nombre: " + nombre + "\nCédula: " + cedula + "\nDirección: " + direccion + "\nTeléfono: " + telefono;
    }
}

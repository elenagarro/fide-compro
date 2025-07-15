
package fidecompro;

/**
 *
 * @author rgarr
 */
public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precioUnitario;
    private int cantidadDisponible;

    public Producto(String codigo, String nombre, String categoria, double precioUnitario, int cantidadDisponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioUnitario = precioUnitario;
        this.cantidadDisponible = cantidadDisponible;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    // Aumentar inventario
    public void aumentarInventario(int cantidad) {
        if (cantidad > 0) {
            this.cantidadDisponible += cantidad;
        }
    }

    // Descontar inventario
    public boolean descontarInventario(int cantidad) {
        if (cantidad > 0 && cantidad <= this.cantidadDisponible) {
            this.cantidadDisponible -= cantidad;
            return true;
        }
        return false; // no hay suficiente stock
    }

    // Mostrar producto
    public String mostrarProducto() {
        return "Código: " + codigo + ", Nombre: " + nombre + ", Categoría: " + categoria +
               ", Precio: " + precioUnitario + ", Stock: " + cantidadDisponible;
    }
}

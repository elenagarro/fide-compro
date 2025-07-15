
package fidecompro;

/**
 *
 * @author rgarr
 */
public class LineaFactura {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public LineaFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    // Getters y setters
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }
    public double getSubtotal() {
        return subtotal;
    }

    // Calcular subtotal (precio * cantidad)
    public double calcularSubtotal() {
        return producto.getPrecioUnitario() * cantidad;
    }

    public String mostrarLinea() {
        return producto.getNombre() + " - Cantidad: " + cantidad + " - Subtotal: " + subtotal;
    }
}

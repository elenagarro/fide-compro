
package fidecompro;
import java.util.ArrayList;
/**
 *
 * @author rgarr
 */
public class ListaProductos {
    private static ArrayList<Producto> productos = new ArrayList<>();

    public static void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public static ArrayList<Producto> getProductos() {
        return productos;
    }
}

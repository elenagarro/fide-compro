
package fidecompro;
import java.util.ArrayList;
/**
 *
 * @author rgarr
 */

public class ListaClientes {
    private static ArrayList<Cliente> clientes = new ArrayList<>();

    public static void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public static ArrayList<Cliente> getClientes() {
        return clientes;
    }
}


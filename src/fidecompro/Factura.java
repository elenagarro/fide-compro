
package fidecompro;

/**
 *
 * @author rgarr
 */
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Factura {
    private String numeroFactura;
    private Cliente cliente;
    private Date fecha;
    private ArrayList<LineaFactura> lineasFactura;

    public Factura(String numeroFactura, Cliente cliente) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.fecha = new Date();
        this.lineasFactura = new ArrayList<>();
    }

    // Getters y setters
    public String getNumeroFactura() {
        return numeroFactura;
    }
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public ArrayList<LineaFactura> getLineasFactura() {
        return lineasFactura;
    }

    // Agregar línea a la factura
    public void agregarLinea(LineaFactura linea) {
        lineasFactura.add(linea);
    }

    // Calcular total sumando subtotales
    public double calcularTotal() {
        double total = 0;
        for (LineaFactura linea : lineasFactura) {
            total += linea.calcularSubtotal();
        }
        return total;
    }

    // Generar factura física en archivo .txt
    public void generarFacturaFisica() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String nombreArchivo = "Factura_" + numeroFactura + ".txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write("Factura Número: " + numeroFactura);
            bw.newLine();
            bw.write("Fecha: " + sdf.format(fecha));
            bw.newLine();
            bw.write("Cliente:");
            bw.newLine();
            bw.write(cliente.mostrarDatos());
            bw.newLine();
            bw.write("Productos:");
            bw.newLine();
            for (LineaFactura linea : lineasFactura) {
                bw.write(linea.mostrarLinea());
                bw.newLine();
            }
            double subtotal = calcularTotal();
            double impuestos = subtotal * 0.13; // ejemplo 13% impuesto
            double total = subtotal + impuestos;

            bw.write(String.format("Subtotal: %.2f", subtotal));
            bw.newLine();
            bw.write(String.format("Impuestos (13%%): %.2f", impuestos));
            bw.newLine();
            bw.write(String.format("Total a pagar: %.2f", total));
            bw.newLine();

            bw.flush();
            System.out.println("Factura generada correctamente: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar factura: " + e.getMessage());
        }
    }
}


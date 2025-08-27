package fidecompro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;

public class VentanaFactura extends JFrame {

    private final JComboBox<Cliente>  cmbCliente  = new JComboBox<>();
    private final JComboBox<Producto> cmbProducto = new JComboBox<>();
    private final JTextField txtCant = new JTextField("1", 4);

   
    private final DefaultTableModel modelo = new DefaultTableModel(
        new String[]{"ID","Producto","Cant","P.Unit","Subtotal"}, 0
    ) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private final JLabel lblTotal = new JLabel("Total: 0.00");

    public VentanaFactura() {
        // Proteger por sesión
        if (Sesion.usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Inicie sesión primero.");
            new VentanaLogin().setVisible(true);
            dispose();
            return;
        }

        setTitle("Nueva Factura — " + Sesion.nombre());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        // Panel superior (cliente, producto, cantidad, agregar)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(new JLabel("Cliente:"));   top.add(cmbCliente);
        top.add(new JLabel("Producto:"));  top.add(cmbProducto);
        top.add(new JLabel("Cant:"));      top.add(txtCant);
        JButton bAgregar = new JButton("Agregar");
        top.add(bAgregar);

        // Panel inferior (quitar, total, guardar)
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton bQuitar  = new JButton("Quitar línea");
        JButton bGuardar = new JButton("Guardar Factura");
        bottom.add(bQuitar);
        bottom.add(lblTotal);
        bottom.add(bGuardar);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Acciones
        bAgregar.addActionListener(e -> agregarLinea());
        bQuitar .addActionListener(e -> quitarSeleccion());
        bGuardar.addActionListener(e -> guardarFactura());

        // Cargar combos
        cargarCombos();
    }

    private void cargarCombos() {
        try {
            cmbCliente.removeAllItems();
            for (Cliente c : new ClienteDAO().listar()) cmbCliente.addItem(c);

            cmbProducto.removeAllItems();
            for (Producto p : new ProductoDAO().listar()) cmbProducto.addItem(p);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo cargar clientes/productos: " + ex.getMessage());
        }
    }

    private void agregarLinea() {
        try {
            if (cmbProducto.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.");
                return;
            }
            Producto p = (Producto) cmbProducto.getSelectedItem();

            int cant = Integer.parseInt(txtCant.getText().trim());
            if (cant <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.");
                return;
            }
            if (p.getPrecio() == null) {
                JOptionPane.showMessageDialog(this, "El producto no tiene precio.");
                return;
            }

            BigDecimal subtotal = p.getPrecio().multiply(new BigDecimal(cant));
            modelo.addRow(new Object[]{p.getId(), p.getNombre(), cant, p.getPrecio(), subtotal});
            recalcularTotal();
            txtCant.setText("1");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida (solo números enteros).");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo agregar: " + ex.getMessage());
        }
    }

    private void quitarSeleccion() {
        int i = tabla.getSelectedRow();
        if (i >= 0) {
            modelo.removeRow(i);
            recalcularTotal();
        }
    }

    private void recalcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total = total.add(new BigDecimal(modelo.getValueAt(i, 4).toString()));
        }
        lblTotal.setText("Total: " + total);
    }

    private void guardarFactura() {
        if (cmbCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto con el botón \"Agregar\".");
            return;
        }

        try (Connection cn = ConexionBD.getConnection()) {
            cn.setAutoCommit(false);

            Cliente cli = (Cliente) cmbCliente.getSelectedItem();
            BigDecimal total = new BigDecimal(lblTotal.getText().replace("Total: ", ""));

            // Encabezado de factura
            int idFactura;
            try (PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO facturas(id_cliente, total) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, cli.getId());
                ps.setBigDecimal(2, total);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    idFactura = rs.getInt(1);
                }
            }

            // Detalle
            try (PreparedStatement psDet = cn.prepareStatement(
                    "INSERT INTO factura_detalle(id_factura, id_producto, cantidad, precio_unitario, subtotal) VALUES(?,?,?,?,?)")) {
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    int idProd       = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                    int cant         = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    BigDecimal punit = new BigDecimal(modelo.getValueAt(i, 3).toString());
                    BigDecimal sub   = new BigDecimal(modelo.getValueAt(i, 4).toString());

                    psDet.setInt(1, idFactura);
                    psDet.setInt(2, idProd);
                    psDet.setInt(3, cant);
                    psDet.setBigDecimal(4, punit);
                    psDet.setBigDecimal(5, sub);
                    psDet.addBatch();
                }
                psDet.executeBatch();
            }

            cn.commit();
            JOptionPane.showMessageDialog(this, "Factura guardada #" + idFactura + " por " + Sesion.nombre());
            modelo.setRowCount(0);
            recalcularTotal();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar factura: " + ex.getMessage());
        }
    }
}

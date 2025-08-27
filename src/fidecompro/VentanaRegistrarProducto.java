package fidecompro;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VentanaRegistrarProducto extends JFrame {
    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtPrecio = new JTextField(10);
    private final JTextField txtStock  = new JTextField(6);

    public VentanaRegistrarProducto() {
        if (Sesion.usuarioActual == null) { // protege por login
            JOptionPane.showMessageDialog(this, "Inicie sesión primero.");
            new VentanaLogin().setVisible(true); dispose(); return;
        }
        setTitle("Registrar Producto — " + Sesion.nombre());
        setSize(400, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(3,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Precio:")); form.add(txtPrecio);
        form.add(new JLabel("Stock:"));  form.add(txtStock);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        JPanel abajo = new JPanel(); abajo.add(btnGuardar);

        add(form, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,"El nombre es obligatorio."); return;
            }
            BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            String sql = "INSERT INTO productos(nombre, precio, stock) VALUES(?,?,?)";
            try (Connection cn = ConexionBD.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setBigDecimal(2, precio);
                ps.setInt(3, stock);
                ps.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Producto guardado.");
            txtNombre.setText(""); txtPrecio.setText(""); txtStock.setText("");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Precio/Stock inválidos. Ejemplo: 1500.00 y 10.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }
}

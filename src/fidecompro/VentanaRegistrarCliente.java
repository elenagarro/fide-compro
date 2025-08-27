package fidecompro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaRegistrarCliente extends JFrame {
    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtCorreo = new JTextField(18);
    private final JButton btnGuardar   = new JButton("Guardar");   // <— AQUÍ EL BOTÓN
    private final JButton btnRefrescar = new JButton("Refrescar");
    private final DefaultTableModel modelo =
            new DefaultTableModel(new String[]{"ID","Nombre","Correo"}, 0);
    private final JTable tabla = new JTable(modelo);
    private final ClienteDAO dao = new ClienteDAO();

    public VentanaRegistrarCliente() {
        super("FideCompro - Registrar Cliente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 420);                 // tamaño suficiente para ver los botones
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(btnGuardar);              // <— SE AGREGA AL PANEL
        form.add(btnRefrescar);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnGuardar); // Enter = Guardar
        btnGuardar.addActionListener(e -> guardar());
        btnRefrescar.addActionListener(e -> cargar());

        cargar();
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
                txtNombre.requestFocus(); return;
            }
            Cliente c = new Cliente(0, nombre, correo);
            dao.insertar(c);
            txtNombre.setText(""); txtCorreo.setText(""); txtNombre.requestFocus();
            cargar();
            JOptionPane.showMessageDialog(this, "Cliente guardado. ID: " + c.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargar() {
        try {
            modelo.setRowCount(0);
            for (Cliente c : dao.listar()) {
                modelo.addRow(new Object[]{c.getId(), c.getNombre(), c.getCorreo()});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaRegistrarCliente().setVisible(true));
    }
}

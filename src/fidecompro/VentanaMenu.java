package fidecompro;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {
    public VentanaMenu() {
        // Si alguien intenta abrir el menú sin login, lo devolvemos
        if (!Sesion.haySesion()) {
            JOptionPane.showMessageDialog(this, "Inicie sesión primero.");
            new VentanaLogin().setVisible(true);
            dispose();
            return;
        }

        setTitle("Fidecompro — Menú (" + Sesion.nombre() + ")");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null);

        JLabel lblUser = new JLabel("Conectado como: " + Sesion.nombre());

        JButton bCliente = new JButton("Registrar Cliente");
        JButton bProducto = new JButton("Registrar Producto");
        JButton bFactura  = new JButton("Nueva Factura");
        JButton bSalir    = new JButton("Cerrar Sesión");

        bCliente.addActionListener(e -> new VentanaRegistrarCliente().setVisible(true));
        bProducto.addActionListener(e -> new VentanaRegistrarProducto().setVisible(true));
        bFactura .addActionListener(e -> new VentanaFactura().setVisible(true));
        bSalir.addActionListener(e -> {
            Sesion.usuarioActual = null;   // limpia la sesión
            new VentanaLogin().setVisible(true);
            dispose();
        });

        JPanel p = new JPanel(new BorderLayout(0,10));
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel centro = new JPanel(new GridLayout(3,1,8,8));
        centro.add(bCliente); centro.add(bProducto); centro.add(bFactura);

        p.add(lblUser, BorderLayout.NORTH);
        p.add(centro,  BorderLayout.CENTER);
        p.add(bSalir,   BorderLayout.SOUTH);

        setContentPane(p);
    }
}

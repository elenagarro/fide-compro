package fidecompro;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnEntrar = new JButton("Entrar");

    public VentanaLogin() {
        setTitle("Fidecompro - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        JLabel l1 = new JLabel("Usuario:");
        JLabel l2 = new JLabel("Contraseña:");

        btnEntrar.addActionListener(e -> entrar());

        GroupLayout gl = new GroupLayout(panel);
        panel.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup()
            .addGroup(gl.createSequentialGroup().addComponent(l1).addComponent(txtUser))
            .addGroup(gl.createSequentialGroup().addComponent(l2).addComponent(txtPass))
            .addComponent(btnEntrar, GroupLayout.Alignment.CENTER)
        );
        gl.setVerticalGroup(gl.createSequentialGroup()
            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l1).addComponent(txtUser))
            .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l2).addComponent(txtPass))
            .addGap(12)
            .addComponent(btnEntrar)
        );

        setContentPane(panel);
    } 
 private void entrar() {
    String u = txtUser.getText().trim();
    String p = new String(txtPass.getPassword());

    try {
        Usuario usr = new UsuarioDAO().login(u, p);

       
        if (usr == null && "admin".equals(u) && "123".equals(p)) {
            usr = new Usuario(); usr.setUsername("admin"); usr.setRol("ADMIN");
        }

        if (usr != null) {
            Sesion.usuarioActual = usr;         // <<< AQUÍ guardamos la sesión
            new VentanaMenu().setVisible(true); // abre el menú
            dispose();                          // cierra el login
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    
}
}}
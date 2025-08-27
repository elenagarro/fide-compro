package fidecompro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public Cliente insertar(Cliente c) throws Exception {
        String sql = "INSERT INTO fidecompro.clientes(nombre, correo) VALUES (?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCorreo());

            int filas = ps.executeUpdate();
            if (filas == 0) throw new Exception("No se insertó ningún registro.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
            System.out.println("[DAO] Insertado id=" + c.getId());
            return c;
        }
    }

    public List<Cliente> listar() throws Exception {
        List<Cliente> list = new ArrayList<>();
        String sql = "SELECT id, nombre, correo FROM fidecompro.clientes ORDER BY id DESC";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo")
                ));
            }
        }
        return list;
    }
}

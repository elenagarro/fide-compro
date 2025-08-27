package fidecompro;

import java.sql.*;

public class UsuarioDAO {
    
    public Usuario login(String u, String p) throws SQLException {
    String sql = "SELECT id, username, password, rol FROM usuarios WHERE username=? AND password=?";
    try (Connection cn = ConexionBD.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, u);
        ps.setString(2, p);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Usuario usr = new Usuario();
                usr.setId(rs.getInt("id"));
                usr.setUsername(rs.getString("username"));
                usr.setPassword(rs.getString("password"));
                usr.setRol(rs.getString("rol"));
                return usr;
            }
        }
    }
    return null;
}
}
package UsuarioLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import UsuarioLocal.FiltroLocal.Usuario;
import conexao.FabricaConexao;

/**
 *
 * @author Diogo
 */
public class UsuarioLocalDAO {

    private final Connection con;

    public UsuarioLocalDAO(Connection conexao) throws SQLException {
        this.con = conexao;
    }

    public UsuarioLocalDAO() throws SQLException {
        this.con = FabricaConexao.getConnection();
    }

    public Usuario getUsuarioLocal(String chave) throws SQLException {
        Usuario local = new Usuario();
        String sql = "SELECT * FROM usuario  WHERE login= ? ";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, chave);
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            local.setId_usuario(rs.getInt("id_usuario"));
            local.setLogin(rs.getString("login"));
            local.setMatricula(rs.getString("matricula"));
            local.setSituacao(rs.getString("situacao"));
            local.setNome(rs.getString("nome"));
            // local.setNivelCodigo(rs.getString("nivel_codigo"));
        }
        return local;
    }

    public Usuario getUsuarioLocal(String login, String senha) throws SQLException {
        Usuario local = new Usuario();
        String sql = "SELECT id_usuario, nome, situacao FROM usuario WHERE login = ? and senha = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, login.toLowerCase());
        psmt.setString(2, senha.toLowerCase());
        try {
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                local.setId_usuario(rs.getInt("id_usuario"));
                local.setLogin(login.toLowerCase());
                local.setNome(rs.getString("nome"));
                local.setSituacao(rs.getString("situacao"));
                local.setFlagErro(false);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            local.setFlagErro(true);
        } finally {
            psmt.close();
        }
        return local;
    }

    public Usuario getUsuarioLocal(int id) throws SQLException {
        Usuario local = new Usuario();
        String sql = "SELECT * FROM usuario WHERE id_usuario = ? ";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setInt(1, id);
        try {
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                local.setId_usuario(rs.getInt("id_usuario"));
                local.setLogin(rs.getString("login"));
                local.setMatricula(rs.getString("matricula"));
                local.setSituacao(rs.getString("situacao"));
                local.setNome(rs.getString("nome"));
                // local.setNivelCodigo(rs.getString("nivel_codigo"));
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return local;
    }
}

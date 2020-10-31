/*package UsuarioLocal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroLocal_bkp implements Filter {

    public static class Usuario {

        private int id_usuario;
        private String nome;
        private String matricula;
        private String login;
        private String senha;
        private String situacao;
        private String nivelCodigo;
        private boolean flagErro;

        public Usuario() {

        }

        public int getId_usuario() {
            return id_usuario;
        }

        public void setId_usuario(int id_usuario) {
            this.id_usuario = id_usuario;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getSituacao() {
            return situacao;
        }

        public void setSituacao(String situacao) {
            this.situacao = situacao;
        }

        public String getNivelCodigo() {
            return nivelCodigo;
        }

        public void setNivelCodigo(String nivelCodigo) {
            this.nivelCodigo = nivelCodigo;
        }

        public boolean isFlagErro() {
            return flagErro;
        }

        public void setFlagErro(boolean flagErro) {
            this.flagErro = flagErro;
        }
    }

    public boolean login(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        HttpSession httpSession = req.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute("login");
        if (usuario == null) {
            UsuarioLocalDAO dao = new UsuarioLocalDAO();
            usuario = dao.getUsuarioLocal(1);
            httpSession.setAttribute("login", usuario);
        }
        return usuario != null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (login((HttpServletRequest) req, (HttpServletResponse) resp)) {
                filterChain.doFilter(req, resp);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FiltroLocal_bkp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {

    }

}
*/
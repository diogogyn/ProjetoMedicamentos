package sistema;

import UsuarioLocal.FiltroLocal.Usuario;
import conexao.FabricaConexao;
import UsuarioLocal.UsuarioLocalDAO;

import java.sql.Connection;
import java.sql.Timestamp;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author super8493
 */
public class Autenticacao extends Action {

    Connection con;

    @Override
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session;
        session = request.getSession(true);

        // ------Verifica se tem um Usuário logado
        if (session.getAttribute("login") != null) {
            session.setAttribute("sessao", session.getId());
            return map.findForward("principal");
        } else {
            if (request.getParameter("login") == null || request.getParameter("senha") == null) {
                // verificar se ususario informou os campos
                request.setAttribute("msgErro", "Voce não informou todos os campos");
                return map.findForward("login");
            } else {
                con = FabricaConexao.getConnection();
                UsuarioLocalDAO dao = new UsuarioLocalDAO(con);
                Usuario usuario = dao.getUsuarioLocal(request.getParameter("login"), request.getParameter("senha"));
                if (usuario.getSituacao().equals("A") && !usuario.isFlagErro()) {

                    session.setAttribute("id_usuario", usuario.getId_usuario());
                    response.addCookie(new Cookie("id_usuario", String.valueOf(usuario.getId_usuario())));

                    session.setAttribute("login", usuario.getLogin());
                    response.addCookie(new Cookie("login", usuario.getLogin()));

                    session.setAttribute("nome", usuario.getNome());
                    response.addCookie(new Cookie("nome", usuario.getNome()));

                    session.setAttribute("situacao", usuario.getSituacao());
                } else {
                    request.setAttribute("msgErro",
                            "Seu usuario encontra-se inativo. Favor entrar em contato com o administrador.");
                    return map.findForward("login");
                }
            }
            return map.findForward("principal");

        }
    }
}

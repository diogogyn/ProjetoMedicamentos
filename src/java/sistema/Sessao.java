package sistema;

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
public class Sessao extends Action {

    int id_usuario;
    String login, matricula, nome, situacao;

    @Override
    public ActionForward execute(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpSession session;
        session = request.getSession(true);

        // ------Verifica se tem um Usuário logado
        if ((request.getParameter("login") == null)
                && (session.getAttribute("login") == null)) {
            // primerio login.
            return map.findForward("login");
        } else{
            session.setAttribute("sessao", session.getId());
            return map.findForward("principal");
        }
    }
}

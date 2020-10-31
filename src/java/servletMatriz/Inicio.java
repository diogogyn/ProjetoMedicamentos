package servletMatriz;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Inicio extends Action {

    HttpSession session;  
    @Override
    public ActionForward execute(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException {
        session = request.getSession(true);
        if (session.getAttribute("login") == null) {
            return map.findForward("login");
        } else {
            return map.findForward("fwdInicio");
        }
    }
}

package sistema;

import javax.servlet.http.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FinalizaSessao extends Action{
    String chave, nome, prefDep, funcao, acessos;
    
    @Override
    public ActionForward execute (ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
            HttpSession session;
            
            session = request.getSession(false);
            session.invalidate();
            
           return map.findForward("login");     
    }
}
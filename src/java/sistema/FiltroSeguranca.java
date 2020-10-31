/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroSeguranca implements Filter {

    public static final String URL_LOGIN = "http://login.intranet.bb.com.br/distAuth/UI/Login?goto=";
    private static final String NOME_COOKIE_SSO = "BBSSOToken";
    private static final String NOME_COOKIE_ACR = "ssoacr";
    private static final String SERVIDOR_SSO_PADRAO = "sso.intranet.bb.com.br";

    public static class Usuario{

        private String chave;
        private Integer prefixo;
        private int codigoComissao;
        private String nome;
        private String nomeGuerra;
        private int rf;
        private String nivelCodigo;

        public void setChave(String chave) {
            this.chave = chave;
        }

        public String getChave() {
            return chave;
        }

        public void setPrefixo(Integer prefixo) {
            this.prefixo = prefixo;
        }

        public Integer getPrefixo() {
            return prefixo;
        }

        public void setCodigoComissao(int codigoComissao) {
            this.codigoComissao = codigoComissao;
        }

        public int getCodigoComissao() {
            return codigoComissao;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return nome;
        }

        public void setNomeGuerra(String nomeGuerra) {
            this.nomeGuerra = nomeGuerra;
        }

        public String getNomeGuerra() {
            return nomeGuerra;
        }

        public void setRf(int rf) {
            this.rf = rf;
        }

        public int getRf() {
            return rf;
        }

        public void setNivelCodigo(String nivelCodigo) {
            this.nivelCodigo = nivelCodigo;
        }

        public String getNivelCodigo() {
            return nivelCodigo;
        }
    }

    public boolean login ( HttpServletRequest req, HttpServletResponse resp ){
        HttpSession httpSession = req.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        if (usuario == null) {
            Cookie cookies[] = req.getCookies();
            if (cookies != null) {
                String tokenId = getCookieValue(cookies, NOME_COOKIE_SSO);
                if (tokenId == null) {
                    enviaPaginaLogin(req, resp);
                    return false;
                } else {
                    String servidor = getCookieValue(cookies, NOME_COOKIE_ACR);
                    if ( !servidor.matches("^sso[0-9]*\\.intranet\\.bb\\.com\\.br$") )
                        servidor = null;
                    if (servidor == null)
                        servidor = SERVIDOR_SSO_PADRAO;
                    usuario = getAtributosUsuario(servidor, tokenId);   
                    if (usuario == null) {
                        resetCookies(resp, NOME_COOKIE_SSO, NOME_COOKIE_ACR);
                        enviaPaginaLogin(req, resp);
                        return false;
                    }
                    httpSession.setAttribute("usuario", usuario);
                }
            }
        }
        return usuario != null;
    }

    private void resetCookies(HttpServletResponse response, String... names ) {
        for( String name: names ){
            Cookie cookie = new Cookie( name, "");
            cookie.setDomain("bb.com.br");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
            response.addCookie(cookie);
        }
    }

    public void enviaPaginaLogin( HttpServletRequest httpServletRequest, HttpServletResponse response ) {
        try {
            response.sendRedirect( getLoginUrl(httpServletRequest));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String getLoginUrl( HttpServletRequest req ){
        String url = req.getRequestURL().toString();
        String query = req.getQueryString();
       
        if (query != null && query.length() > 0 ) {
            url += ("?" + query);
        }
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        }
        return URL_LOGIN + url;
    }

    public void logout( HttpServletRequest req, HttpServletResponse resp ){
        Cookie cookies[] = req.getCookies();
        String tokenId = getCookieValue(cookies, NOME_COOKIE_SSO);
        String servidor = getCookieValue(cookies, NOME_COOKIE_ACR);
        if ( !servidor.matches("^sso[0-9]*\\.intranet\\.bb\\.com\\.br$") )
            servidor = null;
        if( servidor == null )
            servidor = SERVIDOR_SSO_PADRAO;
        get( "http://" +  servidor + "/sso/identity/logout?subjectid="+ tokenId);
        HttpSession s = req.getSession();
        s.removeAttribute("usuario");
        s.removeAttribute("tokenId");
        Cookie cookie = new Cookie(NOME_COOKIE_ACR, "");
        cookie.setDomain( "bb.com.br");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
        cookie = new Cookie(NOME_COOKIE_SSO, "");
        cookie.setDomain( "bb.com.br");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public String getCookieValue(Cookie[] cookies, String nomeCookie){
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(nomeCookie)) {
                return (cookie.getValue().toString());
            }
        }
        return null;
    }


    public Usuario getAtributosUsuario(String servidor, String tokenId) {
        String url = "http://" + servidor + "/sso/identity/attributes?subjectid=" + tokenId;
        String response = get(url);

        if (response != null) {
            int index2 = response.indexOf("userdetails.attribute.name=");
            String atributos = response.substring(index2, response.length());
            StringTokenizer st = new StringTokenizer(atributos, "\n");
            Map<String, String> map = new HashMap<String, String>();
            while (st.hasMoreElements()) {
                try {
                    String key = (String) st.nextElement();
                    String value = (String) st.nextElement();
                    if (value.startsWith("userdetails.attribute.name=")) {
                        key = value;
                        value = (String) st.nextElement();
                    }
                    map.put(key.substring(27, key.length()), value.substring(28, value.length()));
                } catch (NoSuchElementException nsee) {
                    System.out.println(nsee);
                }
            }
            Integer prefixo = Integer.parseInt(getAttribute(map, "cd-pref-depe")); // "prefixoDependencia",
            Usuario usuario = new Usuario();
            usuario.setChave(getAttribute(map, "chaveFuncionario", "uid", "ibm-nativeid", "cd-idgl-usu").toUpperCase());
            usuario.setPrefixo(prefixo);
            usuario.setCodigoComissao(Integer.parseInt(getAttribute(map, "codigoComissao", "cd-cmss-usu", "cd-cmss-fun")));
            usuario.setNome(getAttribute(map, "nomeFuncionario", "nm-idgl", "sn").toUpperCase());
            usuario.setNomeGuerra(getAttribute(map, "nomeGuerra", "displayname", "nomeFuncionario", "nm-idgl", "sn").toUpperCase());
            usuario.setRf(Integer.parseInt(getAttribute(map, "responsabilidadeFuncional")));
            usuario.setNivelCodigo(getAttribute(map, "cd-ref-orgc"));
     
            return usuario;
        }
        return null;
    }

    private String getAttribute(Map<String, String> map, String... ss ) {
        for( String s: ss ){
            String a = getAttribute( map, s );
            if( a != null && a.length() > 0 )
                return a;
        }
        return null;
    }

    private String get( String url ) {
        
        URLConnection connection;
        try {
            connection = new URL( url ).openConnection();
            connection.setRequestProperty("Request-Method", "GET");
            connection.setConnectTimeout( 1000 );
            connection.setReadTimeout( 1000 );
            connection.setDoInput(true);
            connection.setDoOutput(false);
            
            // 36ms
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            StringBuffer newData = new StringBuffer(10000);
            String s = "";
            while (null != ((s = br.readLine()))) {
                newData.append(s);
                newData.append("\n");
            }
            br.close();

            return newData.toString();

        } catch ( Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private String getAttribute(Map<String, String> map, String string) {
        String o = map.get(string);
        if( o != null )
            return o;
        return map.get( string.toLowerCase() );
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if ( login((HttpServletRequest)req,(HttpServletResponse)resp) )
            filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
    
}

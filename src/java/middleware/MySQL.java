package middleware;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;


public class MySQL {

    private String host;
    private String user;
    private String pass;
    private String database;
    
    public Connection c;
    
    /**
     * Construtor da classe
     * 
     * @param host Host em que se deseja conectar 
     * @param database Nome do database em que se deseja conectar
     * @param user Nome do usu�rio
     * @param pass Senha do usu�rio
     */
    public MySQL( String host, String database, String user, String pass ) {
        this.pass = pass;
        this.user = user;
        this.host = host;
        this.database = database;
    }
    
    /**
     * M�todo que estabelece a conex�o com o banco de dados
     * 
     * @return True se conseguir conectar, falso em caso contr�rio.
     */
    public boolean connect() {
        boolean isConnected = false;
        String url;
        
        url = "jdbc:mysql://"+this.host+"/"
              +this.database+"?"
              +"user="+this.user
              +"&password="+this.pass;
              
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.c = DriverManager.getConnection(url);
            isConnected = true;
        } catch( SQLException e ) {
            e.printStackTrace();
            isConnected = false;
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
            isConnected = false;
        } catch ( InstantiationException e ) {
            e.printStackTrace();
            isConnected = false;
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
            isConnected = false;
        }
        
        return isConnected;
    }
    
    /**
     * Esse m�todo executa a query dada, e retorna um ResultSet
     * Talvez fosse melhor id�ia fazer esse m�todo lan�ar uma exception
     * a faze-lo retornar null como eu fiz, por�m isso � apenas um exemplo
     * para demonstrar a funcionalidade do comando execute
     * 
     * @param query String contendo a query que se deseja executar
     * @return ResultSet em caso de estar tudo Ok, null em caso de erro.
     */
    public ResultSet executar( String query ) {
        Statement st;
        ResultSet rs;
        
        try {
            st = this.c.createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Executa uma query como update, delete ou insert.
     * Retorna o n�mero de registros afetados quando falamos de um update ou delete
     * ou retorna 1 quando o insert � bem sucedido. Em outros casos retorna -1
     * 
     * @param query A query que se deseja executar
     * @return 0 para um insert bem sucedido. -1 para erro
     */
    public int inserir( String query ) {
        Statement st;
        int result = -1;
        
        try {
            st = this.c.createStatement();
            result = st.executeUpdate(query);
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        
        return result;
    }
}


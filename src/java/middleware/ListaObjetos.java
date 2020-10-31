/*
 * Esta classe � necessaria para atualizar a lista dos objetos que tenho no banco de dados.
 * A sua neessidade se da no momento onde � realizado a compara��o para consultar o que tenho e o que saiu do meu estoque.
 */


package middleware;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaObjetos extends java.lang.Thread {

	 // �rea de dados da Thread
	 Middleware_LLRP lista;
	 MySQL db;
	 // inicializador da MinhaThread 
	 public ListaObjetos(Middleware_LLRP listaMateriais) {
		 	lista = listaMateriais;
		 	db = new MySQL("localhost","controleestoque","root","");
	        if ( db.connect() ) {
	        	System.out.println("Conex�o com o Banco de Dados Estabelecido!");
	        }else{
	        	System.out.println("Falha ao conectar o banco de dados!!");
	        	System.exit(0);
	        };
	        
	        //montar a primeira lista
	        String query = "select * from objetos";
            ResultSet rs = db.executar(query);
            try {
                if ( rs != null ) { // Verifica se a query retornou algo
                    while ( rs.next() ) {
                    	lista.listaComItensEPC.add(rs.getString("EPC"));
                    	lista.listaItensFora.add(rs.getString("EPC"));
                    }
                }
            } catch ( SQLException e ) {
              e.printStackTrace();
          }
            
	 }

	 // �rea de c�digo da Thread
	 public void run() {
	     while (true) {
	    	 if(lista.realizarAtualizacaoEstoque==0){	//Se a lista estiver sendo processada na outra thread, ent�o esperar para atualizar no outro ciclo;
		         try {
		             Thread.sleep(300000); // coloca a "thread" para "dormir" 1 segundo   --> AGUARDA 5 MIN PARA ATUALIZAR A LISTA
		             System.out.println("Atualizando a lista!!");
		             //esta variavel � usada para estabeler comunica��o entre os processos concorrentes.
		             lista.atualizarLista=1;
		            //Atualizar a lista de 60 em 60 segundos
		 	         String query = "select * from objetos";
		             ResultSet rs = db.executar(query);
		             lista.listaComItensEPC.clear();
		             try {
		                 if ( rs != null ) { // Verifica se a query retornou algo
		                     while ( rs.next() ) {
		                     	lista.listaComItensEPC.add(rs.getString("EPC"));
		                     }
		                 }
		                 lista.atualizarLista=0;
		             } catch ( SQLException e ) {
		               e.printStackTrace();
		           }
		         } catch (InterruptedException e) {
		             e.printStackTrace( System.err );
		         }
		     }
		 }
	 }
}

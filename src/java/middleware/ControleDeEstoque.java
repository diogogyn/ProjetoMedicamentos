package middleware;

import java.sql.Timestamp;
import java.sql.ResultSet;


public class ControleDeEstoque  extends java.lang.Thread{
	
	 // área de dados da Thread
	 Middleware_LLRP lista;
	 MySQL db;
	 
	 public ControleDeEstoque(Middleware_LLRP listaMateriais){
		 lista = listaMateriais;
		 	db = new MySQL("localhost","controleestoque","root","");
	        if ( db.connect() ) {
	        	System.out.println("Conexão com o Banco de Dados Estabelecido!");
	        }else{
	        	System.out.println("Falha ao conectar o banco de dados!!");
	        	System.exit(0);
	        };
	 }
	 
	 // área de código da Thread
	 public void run() {
	     while (true) {
	    	//Se a lista estiver sendo processada na outra thread, então esperar para atualizar no outro ciclo;
	    	 if(lista.realizarAtualizacaoEstoque==1){	
	    		 try{
	    			 Thread.sleep(3000); // coloca a "thread" para "dormir" 1 segundo
	    		 }catch (InterruptedException e) {
		             e.printStackTrace( System.err );
		         }
	    	 }
	    	 //Realiza o processo de atualização de estoque
	         try {
	             Thread.sleep(15000); // coloca a "thread" para "dormir" 1 segundo
	             System.out.println("Atualizando Estoque!!");
	             //esta variavel é usada para estabeler comunicação entre os processos concorrentes.
	             lista.realizarAtualizacaoEstoque=1;
	             
	             //Inicio da comparação
	             String epc, query;
	             int consultaBanco =1, tipoDeConsulta;  	//1 = saiu  e  2 = entrou
	             for(int i =0; i< lista.listaComItensEPC.size(); i++){
	            	 java.util.Date date= new java.util.Date();
					 String data = new Timestamp(date.getTime()).toString();
	            	 consultaBanco =1;
	            	 epc = lista.listaComItensEPC.get(i);
	            	 if(!lista.listaLeiturasEPC.contains(epc)){	//Simboliza item fora do estoque.
	            		//evitar consultas sucessivas ao banco de dados.
	            		//Cadastrar na lista para evitar que na próxima iteração ele procure no banco de dados novamente sobre este item.
	            		 tipoDeConsulta = 1;
	            		 if(!lista.listaItensFora.contains(epc)){	
	            			 lista.listaItensFora.add(epc);
	            		 }else{
	            			 consultaBanco = 0;
	            		 }
	            		 if(lista.listaItensDentro.contains(epc)){	//remove da lista de itens que estão na area de cobertura.
	            			 lista.listaItensDentro.remove(epc);
	            		 }
	            		 //manter uma lista informando sobre a saida do item.
	            	 }else {
	            		 //Itens dentro do estoque ou entrando
	            		 tipoDeConsulta = 2;
	            		 if(!lista.listaItensDentro.contains(epc)){	
	            			 lista.listaItensDentro.add(epc);
	            		 }else{
	            			 consultaBanco = 0;
	            		 }
	            		 if(lista.listaItensFora.contains(epc)){	//remove da lista de itens que estão na area de cobertura.
	            			 lista.listaItensFora.remove(epc);
	            		 }
	            	 }
	            	 //REALIZAR CONSULTAS
	            	 if(consultaBanco == 1){
	            		 if(tipoDeConsulta==1){
	            			 //Informar a saida de um item *Preencher no banco o ultimo registro deste item
	            			 //Preencher o max(cod) do EPC com a data saída.
	            			// query = "UPDATE controle " 
	            			// 		+"SET data_saida = '"+data+"' "
	            			// 		+"WHERE controle.id = (select max(id) from controle where controle.EPC = '"+epc+"')"
	            			// 		+"AND data_saida is NULL";
	            			 try{
	            			 query = "select max(id) as id from controle where controle.EPC = '"+epc+"'";
	            			 ResultSet rs = db.executar(query);
	            			 int id=0;
	            			 while(rs.next()){
	            				id = rs.getInt("id");
	            			 }
	            			 query = "UPDATE controle SET data_saida = '"+data+"' WHERE controle.id = "+id +" AND data_saida is NULL";
	            			 db.inserir(query);
	            			 }catch(Exception e){
	            			      //Handle errors for Class.forName
	            			      e.printStackTrace();
	            			   }
	            		 }else if(tipoDeConsulta ==2){
	            			 //Informar a entrada de um item *Incluir no banco um novo registro
	            			 //Consultar se o item já existe no estoque.
	            			 //Se existe, então não fazer nada, caso contrário, criar um novo registro preenchendo data_entrada.
	            			query = "INSERT INTO controle(EPC, data_entrada) VALUES"
	            					+ "('"+epc+"', '"+data+"')"; 
	            			db.inserir(query);
	            			System.out.println(query);
	            		 }
	            	 }
	             }
	             lista.realizarAtualizacaoEstoque=0;
	             lista.listaLeiturasEPC.clear();
	         } catch (InterruptedException e) {
	             e.printStackTrace( System.err );
	         }
		 }
	 }

}

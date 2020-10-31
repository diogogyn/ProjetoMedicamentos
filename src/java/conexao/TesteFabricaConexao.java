/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

/**
 *
 * @author bb
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TesteFabricaConexao {

	private static Connection connection = null;

	public TesteFabricaConexao() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");

		//para rodar no local
//		connection = DriverManager.getConnection(
//				"jdbc:postgresql://172.17.85.129:5432/novapaginasuper", "postgres",
//				"postgres");
		
		//para subir para o site
		connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/paginasuper", "postgres",
		"postgres");
	}

	public static Connection getConnection() throws SQLException {

		if (connection == null) {
			try {
				new TesteFabricaConexao();
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(TesteFabricaConexao.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		return connection;
	}
}

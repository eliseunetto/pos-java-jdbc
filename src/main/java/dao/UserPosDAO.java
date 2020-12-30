package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.UserPosJava;

public class UserPosDAO {

	private Connection connection;
	
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(UserPosJava userPosJava) {
		String sql = "INSERT INTO userposjava(nome, email) VALUES(?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userPosJava.getNome());
			insert.setString(2, userPosJava.getEmail());
			insert.execute();
			connection.commit(); /* Salva no Banco */
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List<UserPosJava> listar() {
		
		List<UserPosJava> lista = new ArrayList<UserPosJava>();
		
		String sql = "SELECT id, nome, email FROM userposjava";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();
			
			while(resultado.next()) {
				UserPosJava retorno = new UserPosJava();
				retorno.setId(resultado.getLong("id"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setEmail(resultado.getString("email"));
				
				lista.add(retorno);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public UserPosJava buscar(Long id) {
		UserPosJava retorno = new UserPosJava();
		
		String sql = "SELECT * FROM userposjava WHERE id = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet resultado = ps.executeQuery();
			
			if(resultado.next()) {
				retorno.setId(resultado.getLong("id"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setEmail(resultado.getString("email"));
			} else {
				System.out.println("Registro não encontrato");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public void atualizar(UserPosJava userposjava) {
		
		String sql = "UPDATE userposjava SET nome = ? WHERE id = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, userposjava.getNome());
			ps.setLong(2, userposjava.getId());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
 				e1.printStackTrace();
			}
		}
	}
	
	public void deletar(Long id) {
		
		String sql = "DELETE FROM userposjava WHERE id = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, id);
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void salvarTelefone(Telefone telefone) {
		
		String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, telefone.getNumero());
			ps.setString(2, telefone.getTipo());
			ps.setLong(3, telefone.getUsuario());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List<BeanUserFone> listaUserFone(Long id) {
		
		List<BeanUserFone> lista = new ArrayList<BeanUserFone>();
		
		String sql = "SELECT upj.nome, fone.numero, upj.email FROM telefoneuser AS fone"
				+ " INNER JOIN userposjava AS upj"
				+ " ON fone.usuariopessoa = upj.id"
				+ " WHERE upj.id = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BeanUserFone userFone = new BeanUserFone();
				userFone.setNome(rs.getString("nome"));
				userFone.setNumero(rs.getString("numero"));
				userFone.setEmail(rs.getString("email"));
				
				lista.add(userFone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public void deleteUserFone(Long idUser) {
		
		String sqlFone = "DELETE FROM telefoneuser WHERE usuariopessoa = ?";
		String sqlUser = "DELETE FROM userposjava WHERE id = ?";
		
		try {
			PreparedStatement psFone = connection.prepareStatement(sqlFone);
			psFone.setLong(1, idUser);
			psFone.executeUpdate();
			connection.commit();
			
			PreparedStatement psUser = connection.prepareStatement(sqlUser);
			psUser.setLong(1, idUser);
			psUser.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.UserPosJava;

public class TestBancoJdbc {

	@Test
	public void initBanco() {
		
		UserPosDAO userPosDAO = new UserPosDAO();
		UserPosJava userPosJava = new UserPosJava();
		
		userPosJava.setNome("Eliseu Netto");
		userPosJava.setEmail("eliseunetto@mail.com");
		
		userPosDAO.salvar(userPosJava);
	}
	
	@Test
	public void initListar() {
		
		UserPosDAO dao = new UserPosDAO();
		List<UserPosJava> lista = dao.listar();
		
		for (UserPosJava u : lista) {
			System.out.println(u);
			System.out.println("-----------------------------------------------------------------");
		}
	}
	
	@Test
	public void initBuscar() {
		
		UserPosDAO dao = new UserPosDAO();
		UserPosJava userposjava = dao.buscar(4L);
		System.out.println(userposjava);
	}
	
	@Test
	public void initAtualizar() {
		
		UserPosDAO dao = new UserPosDAO();
		
		UserPosJava objetoBanco = dao.buscar(4L);
		
		objetoBanco.setNome("Nome mudado com metodo atualizar");
		
		dao.atualizar(objetoBanco);
	}
	
	@Test
	public void initDeletar() {
		
		UserPosDAO dao = new UserPosDAO();
		dao.deletar(8L);
	}
	
	@Test
	public void initSalvarTelefone() {
		
		Telefone telefone = new Telefone();
		telefone.setNumero("(11) 1234-5678");
		telefone.setTipo("Casa");
		telefone.setUsuario(10L);
		
		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);
	}
	
	@Test
	public void listarUserTelefone() {
		
		UserPosDAO dao = new UserPosDAO();
		List<BeanUserFone> beanUserFones = dao.listaUserFone(10L);
		
		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("-----------------------------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void deleteUserFone() {
		
		UserPosDAO dao = new UserPosDAO();
		dao.deleteUserFone(11L);
	}
}

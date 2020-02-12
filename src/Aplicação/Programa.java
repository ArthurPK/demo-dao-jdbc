package Aplicação;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {
		System.out.println("=== Teste 1:vendedor procurar por id ===");
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		
		Vendedor vendedor = vendedorDao.EncontrarPorId(3);
		
		System.out.println(vendedor);
		
		System.out.println("\n\n=== Teste 2:vendedor procurar por departamento ===");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> list = vendedorDao.EncontrarPorDepartamento(departamento);
		
		for(Vendedor x : list) {
			System.out.println(x);
		}
		
		System.out.println("\n\n=== Teste 3:vendedor procurar por todos ===");
		list = vendedorDao.EncontrarTodos();
		
		for(Vendedor x : list) {
			System.out.println(x);
		}
		
		System.out.println("\n\n=== Teste 4:vendedor inserção ===");
		Vendedor Novovendedor = new Vendedor(null, "Greg", "Greg@gmail.com", new Date(), 5500.00, departamento);
		vendedorDao.Inserir(Novovendedor);
		System.out.println("Inserido! novo id: "+ Novovendedor.getId());
	}

}

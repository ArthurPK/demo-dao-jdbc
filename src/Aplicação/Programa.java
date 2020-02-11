package Aplica��o;

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
	}

}

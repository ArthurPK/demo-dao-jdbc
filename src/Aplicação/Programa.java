package Aplica��o;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);		
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
		
		System.out.println("\n\n=== Teste 4:vendedor inser��o ===");
		Vendedor Novovendedor = new Vendedor(null, "Greg", "Greg@gmail.com", new Date(), 5500.00, departamento);
		vendedorDao.Inserir(Novovendedor);
		System.out.println("Inserido! novo id: "+ Novovendedor.getId());
		
		System.out.println("\n\n=== Teste 5:vendedor update ===");
		vendedor = vendedorDao.EncontrarPorId(1);
		vendedor.setNome("Marta");
		vendedorDao.Update(vendedor);
		System.out.println("atualizacao completa");
		
		System.out.println("\n\n=== Teste 6:vendedor deletar ===");
		System.out.println("Digite o id para deletar: ");
		int id = sc.nextInt();
		vendedorDao.DeletarPorId(id);
		System.out.println("completado!");
	}

}

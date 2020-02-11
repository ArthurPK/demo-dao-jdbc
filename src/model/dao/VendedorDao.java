package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Vendedor;

public interface VendedorDao {

		void Inserir (Vendedor obj);
		void Update (Vendedor obj);
		void DeletarPorId(Integer id);
		Vendedor EncontrarPorId(Integer id);
		List<Vendedor> EncontrarTodos();
		List<Vendedor> EncontrarPorDepartamento(Departamento departamento);
	
}

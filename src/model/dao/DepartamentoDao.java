package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {

	void Inserir (Departamento obj);
	void Update (Departamento obj);
	void DeletarPorId(Integer id);
	Departamento EncontrarPorId(Integer id);
	List<Departamento> EncontrarTodos();
}

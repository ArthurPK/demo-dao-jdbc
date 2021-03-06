package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao{

	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void Inserir(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " 
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartamento().getId());
			
			int linhasAfetadas = st.executeUpdate();
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DBException("Erro inesperado!");
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void Update(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}

		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void DeletarPorId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Vendedor EncontrarPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departamento dep = InstanciarDepartamento(rs);
				Vendedor ven = InstanciarVendedor(rs, dep);
				return ven;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Vendedor InstanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor ven = new Vendedor();
		ven.setId(rs.getInt("Id"));
		ven.setNome(rs.getString("Name"));
		ven.setEmail(rs.getString("Email"));
		ven.setBaseSalary(rs.getDouble("BaseSalary"));
		ven.setDataAniversario(rs.getDate("BirthDate"));
		ven.setDepartamento(dep);
		return ven;
	}

	private Departamento InstanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> EncontrarTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
				  +	"FROM seller INNER JOIN department "
				  +	"ON seller.DepartmentId = department.Id "
				  +	"ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while(rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = InstanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor ven = InstanciarVendedor(rs, dep);
				list.add(ven);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> EncontrarPorDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
				  +	"FROM seller INNER JOIN department "
				  +	"ON seller.DepartmentId = department.Id "
				  +	"WHERE DepartmentId = ? "
				  +	"ORDER BY Name");
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while(rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = InstanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor ven = InstanciarVendedor(rs, dep);
				list.add(ven);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}

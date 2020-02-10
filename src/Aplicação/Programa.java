package Aplicação;

import java.util.Date;

import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {
		Departamento obj = new Departamento(1, "Books");
		
		Vendedor vendedor = new Vendedor(21, "arthur", "arthur@gmail.com", new Date(), 10000.00, obj);

		System.out.println(vendedor);
	}

}

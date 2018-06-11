package DB;

import java.util.ArrayList;
import java.util.Arrays;

public class Subhabilidad {
	private String nombre;
	private ArrayList<String> atributos;
	private int[] contadorAtributos;
	private int suma;
	
	public Subhabilidad (String nombre) {
		this.nombre = nombre;
	}
	
	public Subhabilidad(String[] atributes) {
		atributos = new ArrayList<>();
		int cant = atributes.length;
		contadorAtributos = new int[cant];
		this.nombre = atributes[0];
		for(int i = 1; i < cant; i++) {
			this.atributos.add(atributes[i]);
			contadorAtributos[i-1] = 0;
		}
		setSuma(0);
	}
	
	public void sumarAtributo(String atributo) {
		contadorAtributos[atributos.indexOf(atributo)]++;
		suma++;
	}

	public int getSuma() {
		return suma;
	}

	public void setSuma(int suma) {
		this.suma = suma;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.nombre.equals(((Subhabilidad)obj).getNombre()))
				return true;
		return false;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getAtributos() {
		return atributos;
	}

	public void setAtributos(ArrayList<String> atributos) {
		this.atributos = atributos;
	}

	public int[] getContadorAtributos() {
		return contadorAtributos;
	}

	public void setContadorAtributos(int[] contadorAtributos) {
		this.contadorAtributos = contadorAtributos;
	}

	@Override
	public String toString() {
		String linea = "Subhabilidad: " + nombre + "\n";
		int i = 0;
		for (String s : atributos) {
			linea = linea + " atributo " + s + "cant "+ contadorAtributos[i] + "\n";
			i++;
		}
		linea = linea + "\n";
		return linea;
	}

	
	
	
	
}
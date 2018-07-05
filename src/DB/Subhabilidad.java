package DB;

import java.util.ArrayList;

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

	public String getRandomAtrib()
	{
		int rand = (int)(Math.random() * ((atributos.size())));
		return atributos.get(rand);
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
			linea = linea + " atributo " + s + ", cantidad "+ contadorAtributos[i] + "\n";
			i++;
		}
		linea = linea + "\n";
		return linea;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		String[] values = new String[atributos.size() + 1];
		values[0] = nombre;
		for (int i=1; i<atributos.size(); i++)
		{
			values[i] = atributos.get(i-1);
		}
		Subhabilidad sh = new Subhabilidad(values);
		return sh;
	}
	
	public int getAtrbuto(String atributo) {
		System.out.println(atributos.lastIndexOf(atributo));
		System.out.println(atributo);
		return contadorAtributos[atributos.lastIndexOf(atributo)];
	}
	
	public int getPos(String atributo) {
		return atributos.indexOf(atributo);
		
	}
}
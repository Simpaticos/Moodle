package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Participante {
	private String curso;
	private String nombre;
	private String id;
	private String idDiscusion;
	private ArrayList<Subhabilidad> subhabilidades;
	
	public Participante(String curso, String n, String id, String idDiscusion) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileInputStream("SubabilityStructure.txt"));
		subhabilidades = new ArrayList<>();
		Subhabilidad sub;
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] values = line.split(", ");
			sub = new Subhabilidad(values);
			subhabilidades.add(sub);
		}
		scan.close();
		this.curso = curso;
		this.nombre = n;
		this.id = id;
		this.idDiscusion = idDiscusion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdDiscucion() {
		return idDiscusion;
	}
	
	public void setIdDiscucion(String newId) {
		this.idDiscusion = newId;
	}
	
	public void incrementarSubhabilidad(String subhabilidad, String atributo) {
		int pos = subhabilidades.indexOf(new Subhabilidad(subhabilidad));
		subhabilidades.get(pos).sumarAtributo(atributo);
	}

	public boolean equals(Object participant) {
		if (id.equals(((Participante)participant).getId()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Participante" +"\n"+  "[nombre=" + nombre + ", id=" + id + ", idDiscusion=" + "\n" + "idDiscusion" + "subhabilidades="
				+ subhabilidades + "]";
	}

	public int getAtributo(String subhabilidad, String atributo) {
		int posSubhabilidad = getPosSubhabilidad(subhabilidad);		
		return subhabilidades.get(posSubhabilidad).getAtrbuto(atributo);
	}
	
	public int getPosSubhabilidad(String subhabilidad) {
		for (Subhabilidad sh: subhabilidades) {
			if (sh.getNombre().equals(subhabilidad))
				return subhabilidades.indexOf(sh);
		}
		return 0;
	}
	
	public ArrayList<String> getAtributos (String subhabilidad) {
		return subhabilidades.get(getPosSubhabilidad(subhabilidad)).getAtributos();
	}
	public double getParticipacionTotal() {
		double result = 0;
		for(int i=0;i<subhabilidades.size();i++) 
			result += subhabilidades.get(i).getSuma();
		return result;
	}
	public ArrayList<Subhabilidad> getSubHabilidades(){
		return subhabilidades;
	}
}



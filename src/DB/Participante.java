package DB;

import java.util.ArrayList;
import java.util.Arrays;

public class Participante {
	private String curso;
	private String nombre;
	private String id;
	private String idDiscusion;
	private ArrayList<Subhabilidad> subhabilidades;
	
	public Participante(String curso, String n, String id, String idDiscusion, ArrayList<Subhabilidad> subhabilidades) {
		this.curso = curso;
		this.subhabilidades = new ArrayList<>();
		
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

	

	
	
}



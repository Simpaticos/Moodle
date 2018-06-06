package DB;

import java.util.ArrayList;
import java.util.Arrays;

public class Participante {
	private String nombre;
	private String id;
	private String idDiscusion;
	private ArrayList<String> subhabilidades;
	private int[] contSubhabilidades;
	
	public Participante(String n, String id, String idDiscucion) {
		subhabilidades = new ArrayList<>();
		contSubhabilidades = new int[8];
		for (int i=0; i< 8; i++)
			contSubhabilidades[i] = 0;
		subhabilidades.add("Mediacion");
		subhabilidades.add("Argumentacion");
		subhabilidades.add("Motivar");
		subhabilidades.add("Informar");
		subhabilidades.add("Requerir");
		subhabilidades.add("Reconocimiento");
		subhabilidades.add("Mantenimiento");
		subhabilidades.add("Tarea");
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
	
	public void incrementarSubhabilidad(String subhabilidad) {
		int valor = contSubhabilidades[subhabilidades.indexOf(subhabilidad)];
		valor++;
		contSubhabilidades[subhabilidades.indexOf(subhabilidad)] = valor;
	}

	public boolean equals(Object participant) {
		if (id.equals(((Participante)participant).getId()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Participante [nombre=" + nombre + ", id=" + id + ", idDiscusion=" + idDiscusion + ", subhabilidades="
				+ subhabilidades + ", contSubhabilidades=" + Arrays.toString(contSubhabilidades) + "]";
	}
	
	
	
}



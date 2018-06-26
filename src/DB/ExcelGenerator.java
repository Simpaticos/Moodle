package DB;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ExcelGenerator {
	
	private static ArrayList<Participante> participantes;
	
	public ArrayList<Participante> getParticipantes(){
		return participantes;
	}
	
	public void loadParticipantes() {
		try {
			LectorArchivo lector = new LectorArchivo();
			
			participantes = lector.obtenerDatosParticipantes("prueba.txt");
			System.out.println(participantes);
			Participante p = participantes.get(1);
			ArrayList<String> atributos = p.getAtributos("Tarea");
			System.out.println(atributos);
			System.out.println(p.getAtributo("Tarea", atributos.get(0)));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World");

		
		
	}

}

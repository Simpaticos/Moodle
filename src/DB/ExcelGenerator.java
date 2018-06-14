package DB;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ExcelGenerator {

	public static void main(String[] args) {
		System.out.println("Hello World");
		
		try {
			LectorArchivo lector = new LectorArchivo();
			ArrayList<Participante> participantes;
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

}

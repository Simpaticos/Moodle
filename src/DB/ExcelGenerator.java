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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

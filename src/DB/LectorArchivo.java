package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectorArchivo {
	
		
	public static ArrayList<Participante> obtenerDatosParticipantes(String filename ) throws FileNotFoundException{
		ArrayList<Participante> participantes = new ArrayList<>();
		Participante part = new Participante("", "jeje", "", "");
		Scanner scan = new Scanner(new FileInputStream(filename));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			System.out.println("linea: "+ line);
			String[] values = line.split("\t");
			part.setId(values[2]);
			String[] par = values[4].split("/");
			if (!participantes.contains(part))
				participantes.add(new Participante(values[0], values[3], values[2], values[1]));
			participantes.get(participantes.indexOf(part)).incrementarSubhabilidad(par[0], par[1]);	
		}
		scan.close();
		return participantes;
	}

}



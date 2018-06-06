package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectorArchivo {
	
	
	public ArrayList<Participante> obtenerDatosParticipantes(String filename ) throws FileNotFoundException{
		ArrayList<Participante> participantes = new ArrayList<>();
		Participante part = new Participante("jeje", "", "");
		Scanner scan = new Scanner(new FileInputStream(filename));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] values = line.split("\t");
			part.setId(values[2]);
			if (!participantes.contains(part))
				participantes.add(new Participante(values[3], values[2], values[1]));
			participantes.get(participantes.indexOf(part)).incrementarSubhabilidad(values[4]);	
		}
		scan.close();
		return participantes;
	}

}



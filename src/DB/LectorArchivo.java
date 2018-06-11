package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectorArchivo {
	private ArrayList<Subhabilidad> subhabilidades;
	
	public LectorArchivo() throws FileNotFoundException {
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
	}
		
	public ArrayList<Participante> obtenerDatosParticipantes(String filename ) throws FileNotFoundException{
		ArrayList<Participante> participantes = new ArrayList<>();
		Participante part = new Participante("", "jeje", "", "", null);
		Scanner scan = new Scanner(new FileInputStream(filename));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			System.out.println("linea: "+ line);
			String[] values = line.split("\t");
			part.setId(values[2]);
			String[] par = values[4].split("/");
			if (!participantes.contains(part))
				participantes.add(new Participante(values[0], values[3], values[2], values[1], subhabilidades));
			participantes.get(participantes.indexOf(part)).incrementarSubhabilidad(par[0], par[1]);	
		}
		scan.close();
		return participantes;
	}

}



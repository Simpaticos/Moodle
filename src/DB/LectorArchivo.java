package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LectorArchivo {
	
		
	public static ArrayList<Participante> obtenerDatosParticipantes(String filename ) throws FileNotFoundException{
		ArrayList<Participante> participantes = new ArrayList<>();
		Participante part = new Participante("", "jeje", "", "");
		Scanner scan = new Scanner(new FileInputStream(filename));
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			//System.out.println("linea: "+ line);
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


public static void main(String[] args) throws IOException{
	ArrayList<Subhabilidad> subhabilidades;
	int rand;
	int randSubhability;
	PrintWriter pw = new PrintWriter(new FileWriter("pruebaRandom.txt"));
	Scanner scan = new Scanner(new FileInputStream("SubhabilityStructure.txt"));
	subhabilidades = new ArrayList<>();
	Subhabilidad sub;
	String[] nombres = {"juan","herminio","lucas","santi","seba", "nacho", "enzo", "nati", "euge"};
	while(scan.hasNextLine()) {
		String line = scan.nextLine();
		String[] values = line.split(", ");
		sub = new Subhabilidad(values);
		subhabilidades.add(sub);
	}
	scan.close();
	for (int i = 0; i<200; i++)
	{
		randSubhability =(int)(Math.random() * ((7)));
		rand = (int)(Math.random() * ((8)));
		Subhabilidad sb = subhabilidades.get(randSubhability);
        pw.println("Curso1" + "\t" + "disc1" + "\t" + rand + "\t" +  nombres[rand] + "\t" + sb.getNombre() + "/" + sb.getRandomAtrib());
	}
	pw.close();
	System.out.println("Finishh!");
}
}



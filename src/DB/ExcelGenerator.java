package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	public void loadParticipantes(String ruta) {
		try {
			LectorArchivo lector = new LectorArchivo();
			
			participantes = lector.obtenerDatosParticipantes(ruta);
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
		ArrayList<Subhabilidad> subhabilidades;
		int rand;
		int randSubhability;
		PrintWriter pw;
		Scanner scan;
		try {
			pw = new PrintWriter(new FileWriter("pruebaRandom.txt"));
			scan = new Scanner(new FileInputStream("SubhabilityStructure.txt"));
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
				randSubhability =(int)(Math.random() * ((7) + 1));
				rand = (int)(Math.random() * ((8) + 1));
				Subhabilidad sb = subhabilidades.get(randSubhability);
		        pw.println("Curso1" + "\t" + "disc1" + "\t" + rand + "\t" +  nombres[rand] + "\t" + sb.getNombre() + "/" + sb.getRandomAtrib());
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}

}

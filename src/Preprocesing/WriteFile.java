package Preprocesing;
import java.io.*;
import java.util.*;

import DB.*;

public class WriteFile
{
	private ArrayList< ArrayList<String> > lineasDeDatos = new ArrayList< ArrayList<String> >();

	private ArrayList<SubhabilidadConMargenes> subHabilidades;
	
	public void createFile(String relation, List<String> subAbilities) {
		/**
		 * Este metodo crea el archivo arff con los atributos y los datos correspondiente
		 * Es necesario parasar por parametro el nombre de la relacion como asi tambien los atributos (sub habilidades)
		 * proximamente se añadiran los datos**/
    	String aux = "";
    	FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("C:/Facultad/Ingenieria en Sistemas/Ingenieria de Software/Proyecto/Moodle/"+relation+".arff");
            pw = new PrintWriter(fichero);
            
            pw.println("@relations " + relation);
            for (String s: subAbilities)
            	 aux +=", "+s;
            aux = (aux.length() > 0) ? "{" + aux.substring(2, aux.length()) + "}" : ""; 
            pw.println("@atribute Entrenar " + aux);
            
            for (String s: subAbilities)
            	pw.println("@atribute "+ s + " {Bajo, Medio, Alto}");
            pw.println("@data");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
    public static void main(String[] args)
    {
    	WriteFile wf = new WriteFile();
    	String relation = "conflicto";
    	List<String> sh = new ArrayList<>();
    	for (int i = 0; i < 10; i++)
    		sh.add(String.valueOf(i));
    	
    	/**Prueba del metodo**/
    	wf.createFile(relation, sh);
    	
    	System.out.println("Hola");
    }
    
    
    public void loadSubhabilityStructure(File dir)throws Exception {
		BufferedReader reader = new BufferedReader(
									new FileReader(dir));
		ArrayList<SubhabilidadConMargenes> result = new ArrayList<SubhabilidadConMargenes>();
		boolean eOF=false;
		while(!eOF) {
			String linea = reader.readLine();
			if(linea==null)
				eOF = true;
			else {
				String[] splitString = linea.split(":");
				SubhabilidadConMargenes newSubH = new SubhabilidadConMargenes(splitString[0]);
				ArrayList<String> attr = new ArrayList<String>();
				String[] attrStr = splitString[1].split(",");
				for(int i=0;i<attrStr.length;i++)
					attr.add(attrStr[i]);
				newSubH.setAtributos(attr);
				String[] limites = splitString[2].split(",");
				newSubH.SetMinYMax(Double.parseDouble(limites[0]), Double.parseDouble(limites[0]));
				result.add(newSubH);
				}
			}
		subHabilidades = result;
		reader.close();
    }
    
    public void crearLineasDeArchivos(ArrayList<Participante> p) {
    	for(int i=0;i<6;i++) {
    		lineasDeDatos.add(i, new ArrayList<String>());
    		for(int j=0;j<p.size();j++) {
    			ArrayList<SubhabilidadConMargenes> subH = subHabilidadesPorConflicto(nomConflicto(i));
    			String linea = "?,";
    			for(int k=0;k<subH.size();k++) {
    				ArrayList<String> attr = subH.get(k).getAtributos();
    				double cont=0;
    				for(int l=0;l<attr.size();l++) {
    					cont += p.get(j).getAtributo(subH.get(k).getNombre().split("-")[1], attr.get(l));
    				}
    				linea += subH.get(k).calcularValor((cont/(p.get(j).getParticipacionTotal()))*100) + ",";	
    			}
    			linea = linea.substring(0, linea.length()-1);
    			lineasDeDatos.get(i).add(linea);
    		}	
    	}
    }
    public String nomConflicto(int i) {
    	switch(i) {
	    	case 0:return "comunicacion";
	    	case 1:return "evaluacion";
	    	case 2:return "control";
	    	case 3:return "decision";
	    	case 4:return "tension";
	    	case 5:return "reintegracion";
    		}
    	return null;
    }
    public ArrayList<SubhabilidadConMargenes> subHabilidadesPorConflicto(String s){
    	ArrayList<SubhabilidadConMargenes> result = new ArrayList<SubhabilidadConMargenes>();
    	for(int i=0;i<subHabilidades.size();i++) {
    		if(subHabilidades.get(i).getNombre().split("-")[0].equals(s))
    			result.add(subHabilidades.get(i));
    	}
    	return result;
    }
    
    	
}
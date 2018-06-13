package Preprocesing;
import java.io.*;
import java.util.*;

public class WriteFile
{
	public void createFile(String relation, List<String> subAbilities) {
 
    	String aux = "";
    	FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("F:/"+relation+".txt");
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
    	String relation = "demis";
    	List<String> sh = new ArrayList<>();
    	for (int i = 0; i < 10; i++)
    		sh.add(String.valueOf(i));
    	wf.createFile(relation, sh);
    	System.out.println("Hola");
    }
}
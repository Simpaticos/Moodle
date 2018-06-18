package Clasificador;
import java.io.*;
import java.util.*;

import weka.core.DenseInstance;
import weka.core.Instance;
import DB.*;
import RedBayes.RedBayesNet;
import javafx.util.Pair;
import weka.core.Instances;

public class Clasificador
{
	private RedBayesNet[] redes = new RedBayesNet[6];
	private ArrayList<SubhabilidadConMargenes> subHabilidades;
	public Clasificador()throws Exception{
		/*redes[0] = new RedBayesNet("./bayesNetStructure/strConfComunicacion.txt");
		redes[1] = new RedBayesNet("./bayesNetStructure/strConfControl.txt");
		redes[2] = new RedBayesNet("./bayesNetStructure/strConfDecision.txt");
		redes[3] = new RedBayesNet("./bayesNetStructure/strConfEvaluacion.txt");
		redes[4] = new RedBayesNet("./bayesNetStructure/strConfReduccionDeTension.txt");
		redes[5] = new RedBayesNet("./bayesNetStructure/strConfReintegracion.txt");
		redes[0].serializeRed(new File("./bayesNet/confComunicacion.bayesNet"));
		redes[1].serializeRed(new File("./bayesNet/ConfControl.bayesNet"));
		redes[2].serializeRed(new File("./bayesNet/ConfDecision.bayesNet"));
		redes[3].serializeRed(new File("./bayesNet/ConfEvaluacion.bayesNet"));
		redes[4].serializeRed(new File("./bayesNet/ConfReduccionDeTension.bayesNet"));
		redes[5].serializeRed(new File("./bayesNet/ConfReintegracion.bayesNet"));*/
		redes[0] = new RedBayesNet(new File("./bayesNet/confComunicacion.bayesNet"));
		redes[1] = new RedBayesNet(new File("./bayesNet/ConfControl.bayesNet"));
		redes[2] = new RedBayesNet(new File("./bayesNet/ConfDecision.bayesNet"));
		redes[3] = new RedBayesNet(new File("./bayesNet/ConfEvaluacion.bayesNet"));
		redes[4] = new RedBayesNet(new File("./bayesNet/ConfReduccionDeTension.bayesNet"));
		redes[5] = new RedBayesNet(new File("./bayesNet/ConfReintegracion.bayesNet"));
		this.loadSubhabilityStructure(new File("AtrData.txt"));
		
				
	}
	
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
    	try {
    	Clasificador wf = new Clasificador();
    	/*String relation = "conflicto";
    	List<String> sh = new ArrayList<>();
    	for (int i = 0; i < 10; i++)
    		sh.add(String.valueOf(i));*/
    	
    	/**Prueba del metodo**/
    	/*wf.createFile(relation, sh);
    	
    	System.out.println("Hola"); */
    	}catch(Exception e){e.printStackTrace();};
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
				newSubH.SetMinYMax(Double.parseDouble(limites[0]), Double.parseDouble(limites[1]));
				result.add(newSubH);
				}
			}
		subHabilidades = result;
		reader.close();
    }
    
    public void clasificar(ArrayList<Participante> p) throws Exception {
    	for(int i=0;i<6;i++) {
    		for(int j=0;j<p.size();j++) {
    			DenseInstance instance = new DenseInstance(redes[i].getStructure().numAttributes());
    			ArrayList<SubhabilidadConMargenes> subH = subHabilidadesPorConflicto(nomConflicto(i));
    			//String linea = "?,";
    			for(int k=0;k<subH.size();k++) {
    				ArrayList<String> attr = subH.get(k).getAtributos();
    				double cont=0;
    				for(int l=0;l<attr.size();l++) {
    					cont += p.get(j).getAtributo(subH.get(k).getNombre().split("-")[1], attr.get(l));
    				}
    				instance.setValue(k, cont);
    				//linea += subH.get(k).calcularValor((cont/(p.get(j).getParticipacionTotal()))*100) + ",";	
    			}
    			Pair<String,Double> result = redes[i].classifyOne(instance);
    			//linea = linea.substring(0, linea.length()-1);
    			//lineasDeDatos.get(i).add(linea);
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
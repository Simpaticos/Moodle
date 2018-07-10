package Clasificador;
import java.io.*;
import java.util.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

import weka.core.DenseInstance;
import weka.core.Instances;
//import weka.core.Instance;
import DB.*;
import RedBayes.RedBayesNet;
//import javafx.util.Pair;
//import weka.core.Instances;

public class Clasificador
{
	private RedBayesNet[] redes = new RedBayesNet[6];
	private ArrayList<SubhabilidadConMargenes> subHabilidades;
	private Hashtable<Participante,Hashtable<String,ArrayList<Resultado>>> resultados;
	public Hashtable<Participante, Hashtable<String, ArrayList<Resultado>>> getResultados() {
		return resultados;
	}
	public void setResultados(Hashtable<Participante, Hashtable<String, ArrayList<Resultado>>> resultados) {
		this.resultados = resultados;
	}

	public Clasificador()throws Exception{
		String path = new File(".").getAbsolutePath();
		path=path.substring(0, path.length()-2)+"/";
		/*redes[0] = new RedBayesNet(path+"bayesNetStructure/strConfComunicacion.txt");
		redes[1] = new RedBayesNet(path+"bayesNetStructure/strConfControl.txt");
		redes[2] = new RedBayesNet(path+"bayesNetStructure/strConfDecision.txt");
		redes[3] = new RedBayesNet(path+"bayesNetStructure/strConfEvaluacion.txt");
		redes[4] = new RedBayesNet(path+"bayesNetStructure/strConfReduccionDeTension.txt");
		redes[5] = new RedBayesNet(path+"bayesNetStructure/strConfReintegracion.txt");
		redes[0].serializeRed(new File(path+"bayesNet/confComunicacion.bayesNet"));
		redes[1].serializeRed(new File(path+"bayesNet/ConfControl.bayesNet"));
		redes[2].serializeRed(new File(path+"bayesNet/ConfDecision.bayesNet"));
		redes[3].serializeRed(new File(path+"bayesNet/ConfEvaluacion.bayesNet"));
		redes[4].serializeRed(new File(path+"bayesNet/ConfReduccionDeTension.bayesNet"));
		redes[5].serializeRed(new File(path+"bayesNet/ConfReintegracion.bayesNet"));*/

		redes[0] = new RedBayesNet(new File(path+"bayesNet\\confComunicacion.bayesNet"));
		redes[1] = new RedBayesNet(new File(path+"bayesNet\\ConfEvaluacion.bayesNet"));
		redes[2] = new RedBayesNet(new File(path+"bayesNet\\ConfControl.bayesNet"));
		redes[3] = new RedBayesNet(new File(path+"bayesNet\\ConfDecision.bayesNet"));
		redes[4] = new RedBayesNet(new File(path+"bayesNet\\ConfReduccionDeTension.bayesNet"));
		redes[5] = new RedBayesNet(new File(path+"bayesNet\\ConfReintegracion.bayesNet"));
		this.loadSubhabilityStructure(new File(path+"AtrData.txt"));
		
		/*
		 * Al lado del .jar ejecutable deben estar las carpetas:
		 * 				bayesNet
		 * 				bayesNetStructure
		 * y el archivo:
		 * 				AtrData.txt								*/
				
		resultados = new Hashtable<>();
		
				
	}
	public void guardarRedes()throws Exception {
		String path = new File(".").getAbsolutePath();
		path=path.substring(0, path.length()-2)+"/";
		redes[0].serializeRed(new File(path+"bayesNet/confComunicacion.bayesNet"));
		redes[1].serializeRed(new File(path+"bayesNet/ConfEvaluacion.bayesNet"));
		redes[2].serializeRed(new File(path+"bayesNet/ConfControl.bayesNet"));
		redes[3].serializeRed(new File(path+"bayesNet/ConfDecision.bayesNet"));
		redes[4].serializeRed(new File(path+"bayesNet/ConfReduccionDeTension.bayesNet"));
		redes[5].serializeRed(new File(path+"bayesNet/ConfReintegracion.bayesNet"));
	}
	
	
	public List<String> getConflictoPorUsuario(String nombreParticipante, String rutaArchivoResultado) {
		File jsonInputFile = new File(rutaArchivoResultado);
		List<String> listaConflictos = new ArrayList<>();
		InputStream is;
		try {
			is = new FileInputStream(jsonInputFile);
			JsonReader reader = Json.createReader(is);
			JsonArray alumnoArray = reader.readArray(); 
			reader.close();
			for (int i = 0; i < alumnoArray.size(); i++) {
				JsonObject jsonObject = alumnoArray.getJsonObject(i); 
				String nombreJson = jsonObject.getString("name");
				if (nombreJson.equals(nombreParticipante)) {
					Set<String> jsonObjectConflictos = jsonObject.keySet();
					for (String conflicto: jsonObjectConflictos) {
						
						if (!conflicto.equals("name"))
							if (!conflicto.equals("id")){
							System.out.println(conflicto);
							JsonObject aux = jsonObject.getJsonObject(conflicto); 
							Set<String> auxSet = aux.keySet();
							double maximo = 0;
							String habMasAlta = ""; 
							for (String jsonO: auxSet) {
								System.out.println(aux.getJsonObject(jsonO).getJsonNumber("probabilidad").doubleValue());
								if ( aux.getJsonObject(jsonO).getJsonNumber("probabilidad").doubleValue() >= maximo ) {
									habMasAlta = jsonO;
									maximo = aux.getJsonObject(jsonO).getJsonNumber("probabilidad").doubleValue();
								}
							}
							JsonObject jsonHabMasAlta = aux.getJsonObject(habMasAlta);
							if (!habMasAlta.equals("Nada"))
								if (jsonHabMasAlta.getJsonNumber("porcentaje").doubleValue() > jsonHabMasAlta.getJsonNumber("porcentaje_ideal_max").doubleValue())
									listaConflictos.add(conflicto + " - " + habMasAlta + " - Reducir Incidencias");
								else if (jsonHabMasAlta.getJsonNumber("porcentaje").doubleValue() < jsonHabMasAlta.getJsonNumber("porcentaje_ideal_min").doubleValue())
									listaConflictos.add(conflicto + " - " + habMasAlta + " - Aumentar Incidencias");
						}
					}

					
					
					
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listaConflictos; 
		
	}
	
    public static void main(String[] args){
    	try {
    		Clasificador clasificador = new Clasificador();
    		//args[0] direccion de los datos extraidos de la base de datos
    		//args[1] directorio a donde se escriben los resultados en formato .json
    		ArrayList<Participante> participantes = LectorArchivo.obtenerDatosParticipantes(args[0]);
	    	clasificador.clasificar(participantes);
	    	String dirRes = args[1];
	    	if((dirRes.charAt(dirRes.length()-1)!='/')||(dirRes.charAt(dirRes.length()-1)!='\\'))
	    		dirRes +="/";
	    	JsonWriter.setDir(dirRes);
	    	JsonWriter.dataToJSON(participantes);
	    	JsonWriter.resultToJson(clasificador.getResultados());
	    	clasificador.guardarRedes();
    	}catch(Exception e){e.printStackTrace();}; 

}
    
    public static void runEntrenamiento() {
    	
    	
    	Clasificador cl;
		try {
			cl = new Clasificador();
			cl.entrenar();
			cl.guardarRedes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("termino");
    	
    	
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
				//System.out.println("- "+newSubH.getNombre());
				}
			}
		subHabilidades = result;
		reader.close();
    }
    
    public void entrenar()throws Exception {
    	for(int i=0;i<6;i++) {
	    	Instances structure = redes[i].getStructure();
	    	
	    	ArrayList<SubhabilidadConMargenes> subH = subHabilidadesPorConflicto(nomConflicto(i));
	    	for (int j=0;j<subH.size();j++) {
	    		DenseInstance instance1 = new DenseInstance(redes[i].getStructure().numAttributes());
		    	DenseInstance instance2 = new DenseInstance(redes[i].getStructure().numAttributes());
			    instance1.setDataset(structure);
			    instance2.setDataset(structure);
	    		instance1.setValue(j+1,"Alto");
	    		instance2.setValue(j+1,"Bajo");
	    		for(int k=0;k<subH.size();k++) {
	    			if(k!=j) {
	    				instance1.setValue(k+1,"Medio");
	    	    		instance2.setValue(k+1,"Medio");
	    			}
	    		}
	    		String subHabilidad = subH.get(j).getNombre().split("-")[1];
	    		instance1.setClassValue(subHabilidad);
	    		instance2.setClassValue(subHabilidad);
	    		for(int k=0;k<100;k++) {
		    		redes[i].trainWithOne(instance1);
		    		redes[i].trainWithOne(instance2);
	    		}

	    	}
    	
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
    
    public void clasificar(ArrayList<Participante> p) throws Exception {
    	for(int i=0;i<6;i++) {
    		for(int j=0;j<p.size();j++) {
    			Instances structure = redes[i].getStructure();
    			DenseInstance instance = new DenseInstance(redes[i].getStructure().numAttributes());
    			instance.setDataset(structure);
    			ArrayList<SubhabilidadConMargenes> subH = subHabilidadesPorConflicto(nomConflicto(i));
    			Double[] valores = new Double[subH.size()];
    			for(int k=0;k<subH.size();k++) {
    				ArrayList<String> attr = subH.get(k).getAtributos();
    				double cont=0;
    				for(int l=0;l<attr.size();l++) {
    					String subhabilidad = subH.get(k).getNombre().split("-")[1];
    					if((subhabilidad.equals("Reconocimiento1"))||(subhabilidad.equals("Reconocimiento2")))
    						subhabilidad = "Reconocimiento";
    					cont += p.get(j).getAtributo(subhabilidad, attr.get(l));
    				}
    				cont = (cont/(p.get(j).getParticipacionTotal()))*100;
    				valores[k] = new Double(cont);
    				System.out.println("-"+k);
    				System.out.println("+"+instance.numAttributes());
    				System.out.println("--"+redes[i].getStructure().numAttributes()+"++"+i);
    				System.out.println(nomConflicto(i));
    				instance.setValue(k+1, subH.get(k).calcularValor(cont));
    			}
    			ArrayList<Resultado> result = redes[i].classifyOne(instance);
    			int indMaxProb=0;
    			Double maxProb= new Double(0);
    			for(int r=0;r<result.size();r++)
    				if(result.get(r).getProbabilidad().compareTo(maxProb)>0) {
    					maxProb=result.get(r).getProbabilidad();
    					indMaxProb=r;
    				};
    			instance.setClassValue(indMaxProb);
    			redes[i].trainWithOne(instance);
    			for(int k=0;k<subH.size();k++) {
    				int indice = getIndiceForHability(result.get(k).getHabilidadAEntrenar(), subH);
    				result.get(k).setPorcentaje(valores[indice]);
        			result.get(k).setValorIdealMinimo(subH.get(indice).getMin());
        			result.get(k).setValorIdealMaximo(subH.get(indice).getMax());
    			}
    			
    			if(!resultados.containsKey(p.get(j))) {
    				resultados.put(p.get(j), new Hashtable<>());
    			}
    			resultados.get(p.get(j)).put(nomConflicto(i),result);
    		}	
    	}
    }
    public static String nomConflicto(int i) {
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
    public int getIndiceForHability(String s, ArrayList<SubhabilidadConMargenes> lista) {
    	int result=0;
    	for(int i=0;i<lista.size();i++) {
    		if(lista.get(i).getNombre().split("-")[1].equals(s));
    		result=i;
    	}
    	return result;
    }
    	
}
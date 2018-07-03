package Clasificador;
import java.io.*;
import java.util.*;

import weka.core.DenseInstance;
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
		String path = new File(".").getAbsolutePath();
		path=path.substring(0, path.length()-2)+"/";
		redes[0] = new RedBayesNet(new File(path+"bayesNet\\confComunicacion.bayesNet"));
		redes[1] = new RedBayesNet(new File(path+"bayesNet\\ConfControl.bayesNet"));
		redes[2] = new RedBayesNet(new File(path+"bayesNet\\ConfDecision.bayesNet"));
		redes[3] = new RedBayesNet(new File(path+"bayesNet\\ConfEvaluacion.bayesNet"));
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
	
    public static void main(String[] args){
    	try {
    		Clasificador clasificador = new Clasificador();
    		/*args[0] direccion de los datos extraidos de la base de datos*/
    		/*args[1] directorio a donde se escriben los resultados en formato .json*/
    		ArrayList<Participante> participantes = LectorArchivo.obtenerDatosParticipantes(args[0]);
	    	clasificador.clasificar(participantes);
	    	String dirRes = args[1];
	    	if((dirRes.charAt(dirRes.length()-1)!='/')||(dirRes.charAt(dirRes.length()-1)!='\\'))
	    		dirRes +="/";
	    	JsonWriter.setDir(dirRes);
	    	JsonWriter.dataToJSON(participantes);
	    	JsonWriter.resultToJson(clasificador.getResultados());
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
    			Double[] valores = new Double[subH.size()];
    			for(int k=0;k<subH.size();k++) {
    				ArrayList<String> attr = subH.get(k).getAtributos();
    				double cont=0;
    				for(int l=0;l<attr.size();l++) {
    					cont += p.get(j).getAtributo(subH.get(k).getNombre().split("-")[1], attr.get(l));
    				}
    				cont = (cont/(p.get(j).getParticipacionTotal()))*100;
    				valores[k] = new Double(cont);
    				instance.setValue(k, subH.get(k).calcularValor(cont));
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
    public int getIndiceForHability(String s, ArrayList<SubhabilidadConMargenes> lista) {
    	int result=0;
    	for(int i=0;i<lista.size();i++) {
    		if(lista.get(i).getNombre().split("-")[1].equals(s));
    		result=i;
    	}
    	return result;
    }
    	
}
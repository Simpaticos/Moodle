package Clasificador;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DB.Participante;
import DB.Subhabilidad;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javafx.util.*;

public class JsonWriter {
	
	private static String dir;
	public static void resultToJson(Hashtable<Participante,Hashtable<String,ArrayList<Resultado>>> resultados) {
    	JSONArray arr = new JSONArray();
		for(Participante p: resultados.keySet()) {
    		JSONObject json = oneResultToJSON(p, resultados.get(p));
    		arr.add(json);
		}
        try {writeJson(dir+"Result.json",arr);} catch (IOException e) {e.printStackTrace();}
	}
	
	private static JSONObject oneResultToJSON(Participante p,Hashtable<String,ArrayList<Resultado>> results) {
		//Guardo resultado en el participante
		JSONObject json=new JSONObject();
		json.put("name", p.getNombre());
		json.put("id", p.getId());
		for(int i=0;i<6;i++) {
			String c = Clasificador.nomConflicto(i);
			JSONObject conf = new JSONObject();
			ArrayList<Resultado> r = results.get(c);
			for(Resultado res : r) {
				JSONObject val = new JSONObject();
				val.put("probabilidad", res.getProbabilidad());
				val.put("porcentaje", res.getPorcentaje());
				val.put("porcentaje_ideal_min",res.getValorIdealMinimo());
				val.put("porcentaje_ideal_max",res.getValorIdealMaximo());
				conf.put(res.getHabilidadAEntrenar(), val);
			}
			json.put(c,conf);
		}
		return json;
	}
	
	private static void writeJson(String dir,JSONArray arr)throws IOException {
			FileWriter file = new FileWriter(dir);
            file.write(arr.toJSONString());
            file.flush();
	}
	
	public static void dataToJSON(ArrayList<Participante> participantes){
    	JSONArray arr = new JSONArray();
    	for(Participante p : participantes) {
    		JSONObject json = oneParticipantToJSON(p);
    		arr.add(json);
    	}
        try {writeJson(dir+"SubHabilities.json",arr);} catch (IOException e) {e.printStackTrace();}
	}

	public static JSONObject oneParticipantToJSON(Participante p) {
		JSONObject json=new JSONObject();
		json.put("name", p.getNombre());
		json.put("id", p.getId());
		for(Subhabilidad s : p.getSubHabilidades()) {
			Double porcentaje = (s.getSuma()/p.getParticipacionTotal())*100;
			json.put(s.getNombre(), porcentaje);
		}
		return json;
	}

	
	public static String getDir() {
		return dir;
	}


	public static void setDir(String d) {
		dir = d;
	}
}

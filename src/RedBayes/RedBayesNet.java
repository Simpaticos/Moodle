package RedBayes;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import Clasificador.*;
//import javafx.util.Pair;
import weka.classifiers.bayes.BayesNet;

public class RedBayesNet extends RedBayes {
	
	//constructor de red nueva
	public RedBayesNet(String structureDir) throws Exception{
		this.red = new BayesNet();
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(structureDir));
		structure = loader.getStructure();
		structure.setClassIndex(0);
		red.buildClassifier(structure);
	}
	
	//constructor que carga una red guardada en un archivo luego de haber sido serializada
	public RedBayesNet(File serializedRed)throws Exception{
		FileInputStream fi = new FileInputStream(serializedRed);
		ObjectInputStream oi = new ObjectInputStream(fi);
		this.red = (BayesNet)oi.readObject();
		this.structure = new Instances(((BayesNet)red).m_Instances,0);
		//System.out.println(((BayesNet)red).m_Instances.toString());
		((BayesNet)red).m_Instances.setClassIndex(0);
		oi.close();
		fi.close();
	}
	

	public void training(Instances instances) throws Exception {
		for(int i=0;i<instances.numInstances();i++) {
			((BayesNet)red).updateClassifier(instances.instance(i));}
	}
	public void trainWithOne(Instance instance)throws Exception {
		((BayesNet)red).updateClassifier(instance);
	}


	public void classifyInstances(Instances instances, String resultDir) throws Exception {
		Instances results = new Instances(instances);
		for(int i=0;i<instances.numInstances();i++) {
			double[] clsLabel = ((BayesNet)red).distributionForInstance(instances.instance(i));
			double aux=0;
			int ind=0;
			for(int j=0;j<clsLabel.length;j++)
				if(clsLabel[j]>aux) {
					aux = clsLabel[j];
					ind = j;
				}
			results.instance(i).setClassValue(structure.classAttribute().value(ind));
			}
		BufferedWriter writer = new BufferedWriter(
									new FileWriter(resultDir));
		writer.write(results.toString());
		writer.newLine();
		writer.flush();
		writer.close();
	}

	@Override
	public ArrayList<Resultado> classifyOne(Instance instance) throws Exception {
		double[] clsLabel = ((BayesNet)red).distributionForInstance(instance);
		ArrayList<Resultado> result = new ArrayList<Resultado>();
		for(int i=0;i<clsLabel.length;i++) {
			result.add(new Resultado(structure.classAttribute().value(i),new Double(clsLabel[i])));
		}
		return result;
	}
}

package RedBayes;

//import weka.classifiers.bayes.NaiveBayesUpdateable;
//import weka.core.Instance;
import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
//import javafx.util.Pair;
//import weka.classifiers.bayes.BayesNet;
//import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
//import weka.estimators.DiscreteEstimator;
//import weka.estimators.Estimator;
import weka.core.converters.ArffLoader;
import Clasificador.*;
import java.io.*;
import java.util.ArrayList;

public abstract class RedBayes {
	protected AbstractClassifier red;
	protected Instances structure;
	
	public RedBayes() {};
	

	
	//serializa la red y a guarda en un archivo
	public void serializeRed(File serializedRed)throws Exception{
		FileOutputStream f = new FileOutputStream(serializedRed);
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(this.red);
		o.close();
		f.close();
	}
	public AbstractClassifier getRed() {
		return this.red;
	}
	
	//"entrena"(suma numeros, nada mas) la red con unas Instancias dadas
	public abstract void training(Instances instances) throws Exception;
	
	//"entrena"(suma numeros, nada mas) la red desde un archivo .arff
	public void trainingFromArffFile(String dir)throws Exception {
		Instances instances = loadFromArffFile(dir);
		this.training(instances);
	}
	
	//metodo interno, carga desde un archivo .arff las instancias, se puede llamar desde afuera (es estatico)
	public static Instances loadFromArffFile(String dir)throws Exception{
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(dir));
		Instances instances = loader.getDataSet();
		instances.setClassIndex(0);
		return instances;
	}
	
	//clasifica las instancias dadas y las guarda en la direccion indicada
	public abstract void classifyInstances(Instances instances,String resultDir) throws Exception;
	
	//clasifica las instancias desde la primera direccion dada y las guarda en la segunda direccion dada
	public void classifyFromArffFile(String instancesDir, String resultDir)throws Exception{
		Instances instances = loadFromArffFile(instancesDir);
		this.classifyInstances(instances, resultDir);
	}
	
	public Instances getStructure() {
		return this.structure;
	}
	public abstract ArrayList<Resultado> classifyOne(Instance instance) throws Exception;
}
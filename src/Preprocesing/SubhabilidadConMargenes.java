package Preprocesing;

import DB.Subhabilidad;

public class SubhabilidadConMargenes extends Subhabilidad {

	private double min;
	private double max;
	public SubhabilidadConMargenes(String nombre) {
		super(nombre);
		min=0;
		max=100;
	}
	public void SetMinYMax(double min,double max) {
		this.min=min;
		this.max=max;
	}

	public String calcularValor(double valor) {
		if(valor<min)
			return "bajo";
		else
			if((valor>=min)&&(valor<max))
				return "medio";
			else
				if(valor>=max)
					return "alto";
		return null;
	}
}

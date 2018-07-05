package Clasificador;

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
	public double getMin() {
		return min;
	}
	public double getMax() {
		return max;
	}

	public String calcularValor(double valor) {
		if(valor<min)
			return "Bajo";
		else
			if((valor>=min)&&(valor<max))
				return "Medio";
			else
				if(valor>=max)
					return "Alto";
		return null;
	}
}

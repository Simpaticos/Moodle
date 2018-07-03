package Clasificador;

public class Resultado {

	private String habilidadAEntrenar;
	private Double probabilidad;
	private Double porcentaje;
	private Double valorIdealMinimo;
	private Double valorIdealMaximo;
	public Resultado(String s, Double p) {
		habilidadAEntrenar=s;
		probabilidad=p;
	};
	public Resultado() {};
	public String getHabilidadAEntrenar() {
		return habilidadAEntrenar;
	}
	public void setHabilidadAEntrenar(String habilidadAEntrenar) {
		this.habilidadAEntrenar = habilidadAEntrenar;
	}
	public Double getProbabilidad() {
		return probabilidad;
	}
	public void setProbabilidad(Double probabilidad) {
		this.probabilidad = probabilidad;
	}
	public Double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public Double getValorIdealMinimo() {
		return valorIdealMinimo;
	}
	public void setValorIdealMinimo(Double valorIdealMinimo) {
		this.valorIdealMinimo = valorIdealMinimo;
	}
	public Double getValorIdealMaximo() {
		return valorIdealMaximo;
	}
	public void setValorIdealMaximo(Double valorIdealMaximo) {
		this.valorIdealMaximo = valorIdealMaximo;
	}
}

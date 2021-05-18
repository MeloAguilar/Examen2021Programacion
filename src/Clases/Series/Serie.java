package Clases.Series;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Serie implements Serializable, Comparable<Serie> {

    //Atributos
    private String nombre;
    private LocalDate fechaEstreno;
    private double valoracion;
    private int numeroCapitulos;
    public static final long serialVersionUID = 2L;


    //Métodos

    public Serie(String nombre, LocalDate fechaEstreno, int numeroCapitulos) {
        this.nombre = nombre;
        this.fechaEstreno = fechaEstreno;
        this.valoracion = 5;
        this.numeroCapitulos = numeroCapitulos;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public int getNumeroCapitulos() {
        return numeroCapitulos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Serie)) return false;
        Serie serie = (Serie) o;
        return Objects.equals(nombre, serie.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }




    @Override
    public int compareTo(Serie o) {
        int comp = 0;
       if((comp = this.nombre.compareTo(o.nombre)) > 0)
           comp += comp * 2 ;
       if((comp = this.nombre.compareTo(o.nombre)) < 0)
           comp -= comp*2;
        return comp;
    }


    /**
     * <h2>getIndiceRepercusion()</h2>
     *
     * Método que, gracias a getValorEspecifico, calcula el índice de repercusión de la serie
     * @return
     */
    public double getIndiceRepercusion(){
        return (valoracion * 1.5 + this.getValorEspecifico()) * 4;
    }

    /**
     * <h2>getValorEspecifico()</h2>
     *
     * Método que calcula el valor específico de una serie
     * @return
     */
    public double getValorEspecifico(){
        return this.numeroCapitulos * 3;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "nombre='" + nombre + '\'' +
                ", fechaEstreno=" + fechaEstreno +
                ", valoracion=" + valoracion +
                ", numeroCapitulos=" + numeroCapitulos;
    }
}

package Clases.Series;

import java.io.Serializable;
import java.time.LocalDate;

public class SerieDePago extends Serie implements Serializable, Comparable<Serie> {

    //Atributos
    private double precio;
    public static final long serialVersionUID = 1L;

    public SerieDePago(String nombre, LocalDate fechaEstreno, int numeroCapitulos, double precio) {
        super(nombre, fechaEstreno, numeroCapitulos);
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }



    @Override
    public double getValorEspecifico(){
        return this.precio * 2;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", precio=" + precio +
                '}';
    }
}

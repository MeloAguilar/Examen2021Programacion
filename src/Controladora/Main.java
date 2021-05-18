package Controladora;

import Clases.Series.Serie;
import Clases.Series.SerieDePago;
import Clases.SeriesDataAccess.FileDataAccess;
import Menu.Menu;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        FileDataAccess fileDataAccess = new FileDataAccess();
        boolean exit = false;
        do {
            switch (Menu.pedirDato(sc, Menu.MENSAJEINICIAL)) {
                case "1" -> {
                    switch (Menu.pedirDato(sc, Menu.MENSAJEIMPRESION)) {
                        case "1" -> {
                            //Impresion de las series de un año dado

                            imprimirSeriesDeUnMismoAnio(fileDataAccess);
                        }
                    }
                }
                //Menu calculos
                case "2" -> {
                    switch (Menu.pedirDato(sc, Menu.MENSAJECALCULOS)) {
                        //Sumatorio de precio de series de pago
                        case "1" -> {
                            mostrarPrecioSumadoCatalogo(fileDataAccess);
                        }
                        //Mostrar datos de la serie con mayor indice de repercusion de el archivo
                        case "2" -> {
                            mostrarDatosMaxRepercusion(fileDataAccess);
                        }

                    }
                }
                case "3" -> {
                    switch (Menu.pedirDato(sc, Menu.MENSAJEGESTION)) {
                        //Añadir serie nueva
                        case "1" -> {
                            introducirSerieNueva(fileDataAccess);
                        }
                        //Borrar una serie
                        case "2" -> {
                            if (borrarSerieDeFichero(fileDataAccess)) {
                                System.out.println("La serie se eliminó del fichero");
                            } else {
                                System.out.println("No se pudo borrar la serie");
                            }
                        }
                    }
                }
                case "4" -> {
                    exit = true;
                }
            }
        }
        while (!exit);
    }


    /**
     * <h2>imprimirSeriesDeUnMismoAnio(int, FileDataAccess)</h2>
     *
     * Método que recorre el archivo buscando las series en las que el año coincida
     * y las muestra por pantalla. Pide al usuario un entero que, si coincide con una o varias series,
     * mostrará los datos de esta por pantalla.



     * Precondiciones: Se debe introducir un año válido y fileDataAccess debe haber sido construido
     * con datos previamente
     * Postcondiciones:Mostrará por pantalla una lista de series que tengan el mismo año en su fechaEstreno
     * Entrada:int anioSalida es un entero que coincide con el año de salida de las series que queremos mostrar.
     * Salida:Nada

     */
    private static void imprimirSeriesDeUnMismoAnio( FileDataAccess fileDataAccess) {

    }


    private static void mostrarPrecioSumadoCatalogo(FileDataAccess fileAccess) {
        System.out.println("El precio total del catálogo de Series de pago es --> " + fileAccess.getSumatorioPrecios() + "€");
    }

    private static void mostrarDatosMaxRepercusion(FileDataAccess fileDataAccess) {
        Serie serie = fileDataAccess.getDatosSerieConMasRepercusion();
        System.out.println("Nombre --> " + serie.getNombre());
        System.out.println("Índice de repercusión --> " + serie.getIndiceRepercusion());
        System.out.println("Fecha de Estreno --> " + serie.getFechaEstreno());
        System.out.println("Número de capítulos --> " + serie.getNumeroCapitulos());
        System.out.println("Valoración --> " + serie.getValoracion());
    }


    private static Serie introducirSerieNueva(FileDataAccess fileDataAccess) {
        Serie serieAintroducir = null;
        String nombre = "";
        LocalDate fechaSalida = null;
        double valoracion = 5;
        int numeroCapitulos = 0;

        //Establecemos los datos comunes.
        nombre = Menu.pedirDato(sc, " el nombre de la serie a introducir");
        do {
            String fechaString = Menu.pedirDato(sc, "la fecha de salida de la serie en formato yyyy-MM-dd");
            fechaSalida = Menu.validarFecha(fechaString);
        }while(fechaSalida == null);
        do{
            String numeroCapitulosString = Menu.pedirDato(sc, "la cantidad de capítulos de la serie en números");
            numeroCapitulos = (int)Menu.parseDouble(numeroCapitulosString);

        }while(numeroCapitulos < 1);


        //Comprobamos que lo si quiere introducir el usuario es una serie o una serie de pago
        switch (Menu.pedirDato(sc, "\n1 --> Si la serie es de pago\n2 --> Si la serie es gratuita")) {
            case "1" -> {
                double precio = 0;
                do{
                    //Se introducen los datos de la serie de pago
                    String precioString = Menu.pedirDato(sc, "el precio de la serie");
                    precio = Menu.parseDouble(precioString);
                }while(precio < 1);
                serieAintroducir = new SerieDePago(nombre,fechaSalida,numeroCapitulos,precio);
                System.out.println("Se creó la serie " + serieAintroducir.getNombre());
            }
            case "2" -> {
                serieAintroducir = new Serie(nombre, fechaSalida, numeroCapitulos);
                System.out.println("Se creó la serie " + serieAintroducir.getNombre());
            }
            default -> {
                System.out.println("Introduzca un dato válido");
            }
        }
        return serieAintroducir;
    }


    private static boolean borrarSerieDeFichero(FileDataAccess fileDataAccess) {
        boolean success = true;
        Serie serie = fileDataAccess.rescatarSerieDeFichero(Menu.pedirDato(sc, " el nombre de la serie a elminiar"));
        List<Serie> series = fileDataAccess.eliminarSerieDeFichero(serie);
        for (Serie serieq : series) {
            if (series.contains(serie))
                success = false;
            System.out.println(serieq);
        }
        return success;
    }


    private static void mostrarLista(List<Serie> series) {
        for (Serie serie : series) {
            System.out.println(serie);
        }
    }


}

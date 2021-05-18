package Test.MainParaCreacionAutomatica;

import Clases.Series.Serie;
import Clases.Series.SerieDePago;
import Clases.SeriesDataAccess.FileDataAccess;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        switch (sc.nextLine()) {
            case "1" -> {
                FileDataAccess fileAcces = new FileDataAccess();
                crearEIntroducirParametrosDePrueba(fileAcces);
            }
            default -> {

            }
        }

    }


    private static List<Serie> crearParametrosDePrueba() {
        Serie s = new Serie("Shingeki no Kioyin", LocalDate.of(2018, 05, 12), 80);
        Serie s2 = new Serie("La casa de papel", LocalDate.of(2019, 03, 12), 20);
        SerieDePago s3 = new SerieDePago("Boku no Hero", LocalDate.of(2016, 07, 23), 60, 1.45);
        SerieDePago s4 = new SerieDePago("Arrayan", LocalDate.of(1996, 10, 10), 500, 0.05);
        List<Serie> series = new LinkedList<>();
        series.add(s);
        series.add(s2);
        series.add(s3);
        series.add(s4);
        return series;
    }

    private static void crearEIntroducirParametrosDePrueba(FileDataAccess fileDataAccess) {
        List<Serie> series = crearParametrosDePrueba();
        mostrarLista(series);
        introducirSeriesEnFichero(series, fileDataAccess);
        System.out.println("************************************\n          Lista Actualizada           \n************************************");
        LinkedList<Serie> listaSeries = (LinkedList<Serie>) fileDataAccess.ordenarArchivoEnLista();
        mostrarLista(listaSeries);
        introducirSeriesEnFichero(listaSeries, fileDataAccess);
        mostrarLista(listaSeries);
    }

    private static void mostrarLista(List<Serie> series) {
        for (Serie serie : series) {
            System.out.println(serie);
        }
    }

    private static void introducirSeriesEnFichero(List<Serie> series, FileDataAccess dataAccess) {
        for (Serie serie : series) {
            dataAccess.introducirSerieEnFichero(serie);
        }
    }

}

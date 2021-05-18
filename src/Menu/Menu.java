package Menu;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
    public static final String MENSAJEINICIAL = "\n1 --> Menu de impresión\n2 --> Menu de cálculos" +
            "\n3 --> Menu de Gestion\n4 --> salir";
public static final String MENSAJEIMPRESION = "\n1 --> imprimir el listado de las series estrenadas en un año dado\n2 --> salir";
public static final String MENSAJECALCULOS = "\n1 --> sumatorio de las series de pago de la plataforma\n2 ->> mostrar datos de la serie con mayor indice de repercusión";
public static final String MENSAJEGESTION = "\n1 --> Añadir serie al archivo\n2 --> Borrar serie del archivo";
    /**
     * <h2>validarFecha(String)</h2>
     *
     * Método que valida una fecha introducida como String
     * precondiciones:
     * postcondiciones:
     * @param fechaString
     * @return
     */
    public static LocalDate validarFecha(String fechaString){
        LocalDate fechaBuena;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
        try{
            fechaBuena = LocalDate.parse (fechaString, formato);
        }catch(DateTimeParseException e){
            fechaBuena = null;
        }
        return fechaBuena;
    }


    /**
     *<h2>pedirDato(Scanner, String)</h2>
     *
     * Método que devuelve un String introducido por teclado
     *
     * Precondiciones: ninguna
     * Postcondiciones: ninguna
     * @param sc
     * @param datoAIntroducir
     * @return
     */
    public static String pedirDato(Scanner sc, String datoAIntroducir){
        String mensaje = "Introduzca aquí ";
        System.out.println (mensaje + datoAIntroducir );
        return sc.nextLine ();
    }


    private static boolean validateDouble(String doubleSt){
        boolean success = true;
        try{
            Double.parseDouble(doubleSt);
        }catch(NumberFormatException nfe){
            success = false;
        }
        return success;
    }

    /**
     *
     * @param doubleSt
     * @return
     */
    public static double parseDouble(String doubleSt){
        double numero = 0;
        if(validateDouble(doubleSt)){
            numero = Double.parseDouble(doubleSt);
        }
        return numero;
    }

}

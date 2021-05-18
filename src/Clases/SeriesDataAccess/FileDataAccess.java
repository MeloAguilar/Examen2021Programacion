package Clases.SeriesDataAccess;

import Clases.Series.Serie;
import Clases.Series.SerieDePago;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileDataAccess {

    public static final String PATH = ".\\src\\Catalogo.dat";
    private File ficheroCatalogo;

    public FileDataAccess() {
        this.ficheroCatalogo = new File(PATH);

    }


    /**
     * Método que se encarga de cerrar el flujo de un Autocloseable
     *
     * @param cl
     */
    private static void cerrarFlujo(AutoCloseable cl) {
        try {
            cl.close();
        } catch (Exception e) {

        }
    }


    /**
     * <h2>validarSerieUnica(Serie)</h2>
     * <p>
     * Método que valida que una serie no se encuentre ya en el fichero
     * y devuelve un booleano que será true en caso de que la serie no exista en el fichero
     * y false y esta ya se había introducido.
     * Precondiciones: serieAValidar debe ser una serie ya construida
     * Postcondiciones: ninguna
     * Entradas: serieAValidar
     * Salidas: <ol>
     * Booleano: Success
     * <li>true --> La serie es única y no se encuentra en el fichero</li>
     * <li>false --> La serie no es única, por lo que se encuentra ya dentro del fichero</li>
     * </ol>
     *
     * @param serieAValidar
     * @return
     */
    public boolean validarSerieUnica(Serie serieAValidar) {
        ObjectInputStream objectInputStream = null;
        boolean success = true;//Booleano de salida
        boolean exit = false;//Booleano de validación del bucle while
        Serie serieDelArchivo = null;
        try {
            //Creación del lector de objetos
            objectInputStream = new ObjectInputStream(new FileInputStream(this.ficheroCatalogo));
            //Mientras siga leyendo objetos y no se active el booleano exit
            while ((serieDelArchivo = (Serie) objectInputStream.readObject()) != null && !exit) {
                //Si la serie a validar es igual a la recogida del archivo
                if (serieAValidar.equals(serieDelArchivo)) {
                    //Saldremos del bucle
                    exit = true;
                }
            }
        } catch (EOFException f) {
            //no hacer nada
            success = false;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            success = false;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } finally {
            if (objectInputStream != null)
                cerrarFlujo(objectInputStream);
        }
        return success;

    }

    /**
     * <h2>introducirSerieEnFichero(Serie)</h2>
     * <p>
     * Método que introduce una serie en el fichero que las contiene
     * Precondiciones:
     * PostCondiciones:
     * Entrada:
     * Salida:
     *
     * @param serieAIntroducir
     * @return
     */
    public boolean introducirSerieEnFichero(Serie serieAIntroducir) {
        ObjectOutputStream myObjectOutputStream = null;
        boolean success = true;
        //Si el método de validación devuelve true, intentará introducir la serie
        try {

            if (this.ficheroCatalogo.length() < 1) {
                myObjectOutputStream = new ObjectOutputStream(new FileOutputStream(this.ficheroCatalogo));
                myObjectOutputStream.writeObject(serieAIntroducir);
            } else {
                myObjectOutputStream = new MyObjectOutputStream(new FileOutputStream(this.ficheroCatalogo, true));
                myObjectOutputStream.writeObject(serieAIntroducir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myObjectOutputStream != null)
                cerrarFlujo(myObjectOutputStream);
        }
        return success;
    }


    /**
     * <h2>rescatarSerieDeFichero(String)</h2>
     * <p>
     * Método que reconstruye una Serie mediante un String introducido como parámetro
     * <p>
     * Precondiciones:
     * Postcondiciones:
     * Entrada:
     * Salida:
     */
    public Serie rescatarSerieDeFichero(String nombre) {
        ObjectInputStream objectInputStream = null;
        Serie serie = null;//Serie a devolver
        boolean success = false;//Booleano de salida del bucle while
        try {
            //Se abre el flujo
            objectInputStream = new ObjectInputStream(new FileInputStream(this.ficheroCatalogo));
            //Mientras no se active el booleano de salida y la serie obtenida del flujo no sea null.
            while (!success && ((serie = (Serie) objectInputStream.readObject()) != null)) {
                //Si el nombre de la serie obtenida del ObjectInputStream es igual al String introducido por parámetro.
                if (serie.getNombre().equals(nombre)) {
                    //Salida del bucle
                    success = true;
                }
            }
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null)
                cerrarFlujo(objectInputStream);
        }
        return serie;
    }


    /**
     * @return
     */
    public List<Serie> rescatarSeriesEnLista() {
        List<Serie> series = new LinkedList<>();
        ObjectInputStream objectInputStream = null;
        Serie serie = null;
        try {
            //Se abre el flujo para leer objetos
            objectInputStream = new ObjectInputStream(new FileInputStream(this.ficheroCatalogo));
            //Si el objeto obtenido es diferente de null
            while ((serie = (Serie) objectInputStream.readObject()) != null) {
                //Se añade la serie a la lista
                series.add(serie);
            }
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return series;
    }


    /**
     * Método utilizado para
     * @param serieAEliminar
     * @return
     */
    public List<Serie> eliminarSerieDeFichero(Serie serieAEliminar) {
        List<Serie> series = rescatarSeriesEnLista();
        boolean exit = false;//Booleano de salida si se encuentra la serie a borrar
        //Si el método de validación arroja false
        if (!validarSerieUnica(serieAEliminar)) {
            //Mientras que
            for (int i = 0; i < series.size() && !exit; i++) {
                if (series.get(i).equals(serieAEliminar)) {
                    //Se elimina la serie de la lista
                    series.remove(i);
                    //Se sale del bucle
                    exit = true;
                }
            }
        }
        return series;
    }

    /**
     * <h2>ordenarArchivoEnLista()</h2>
     * <p>
     * Método que ordena los Productos de un archivo en una lista y lo devuelve para ser utilizada en otro
     * método que ordenará el archivo auxiliar
     *
     * @return
     */
    public List<Serie> ordenarArchivoEnLista() {
        List<Serie> series = new LinkedList<>();
        Serie serie;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(this.ficheroCatalogo));
            while ((serie = (Serie) objectInputStream.readObject()) != null) {
                series.add(serie);
            }
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        } catch (IOException e) {
            cerrarFlujo(objectInputStream);
        }
        //La ordenamos en orden descendente
        series.sort(Serie::compareTo);
        recrearFicheroOrdenado(series);
        return series;
    }

    /**
     * Método privado que actualiza el fichero despues de ser ordenado en el método
     * ordenarArchivoEnLista
     *
     * @param series
     * @return
     */
    private boolean recrearFicheroOrdenado(List<Serie> series) {
        boolean success = true;
        this.ficheroCatalogo.delete();
        ObjectOutputStream output = null;
        try {
            for (Serie serie : series) {
                if (this.ficheroCatalogo.length() == 0) {
                    output = new ObjectOutputStream(new FileOutputStream(this.ficheroCatalogo));
                    output.writeObject(serie);
                } else {
                    output = new MyObjectOutputStream(new FileOutputStream(this.ficheroCatalogo, true));
                    output.writeObject(serie);
                }
            }
        } catch (IOException e) {
            success = false;
        } finally {
            if (output != null)
                cerrarFlujo(output);
        }

        return success;
    }






    public Serie getDatosSerieConMasRepercusion(){
        Serie serieConMasRepercusion = null;
        Serie serieAcomparar = null;
        ObjectInputStream objectInput= null;
        double repercusion = 0;
        try{
            objectInput = new ObjectInputStream(new FileInputStream(this.ficheroCatalogo));
            while((serieAcomparar = (Serie)objectInput.readObject()) != null){

                    if(serieAcomparar.getIndiceRepercusion() > repercusion){
                       serieConMasRepercusion = serieAcomparar;
                    }

            }
        }catch(EOFException f){
        //no hacer nada
        }catch(ClassNotFoundException r){
            r.printStackTrace();
        }
        catch(IOException e){
        e.printStackTrace();
        }
    return serieConMasRepercusion;
    }


    public double getSumatorioPrecios(){
        ObjectInputStream inputStream= null;
        Serie serie = null;
        double precioTotal = 0;

        try{
            inputStream = new ObjectInputStream(new FileInputStream(this.ficheroCatalogo));
            while((serie = (Serie) inputStream.readObject()) != null){
                if(serie instanceof SerieDePago){
                    precioTotal += ((SerieDePago) serie).getPrecio();
                }
            }
        }catch(EOFException f){

        }catch(ClassNotFoundException c){
            c.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }finally{
            if(inputStream != null)
                cerrarFlujo(inputStream);
        }
        return precioTotal;
    }

}

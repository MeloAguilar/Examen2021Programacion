package Clases.SeriesDataAccess;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Clase ObjectOutputStream modificada para que no escriba cabeceras.
 */
public class MyObjectOutputStream extends ObjectOutputStream {

    @Override
    protected void writeStreamHeader() throws IOException {
        //No hacer nada
    }

    public MyObjectOutputStream() throws IOException{
        super();
    }

    public MyObjectOutputStream(OutputStream out) throws IOException{
        super(out);
    }

}
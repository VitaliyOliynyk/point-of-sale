package eu.vitaliy.pointofsale.io;

import eu.vitaliy.pointofsale.domain.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 14:50
 */
public class LCD implements OutputDevice{

    private OutputStream outputStream;

    public LCD(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(Product product) {
        try {
            outputStream.write(String.format(Locale.US, "Product '%s' price is: %.2f", product.getName(), product.getPrice()).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void writeError(String message){
        try {
            writeErrorImpl(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeErrorImpl(String error) throws IOException {
        outputStream.write(error.getBytes());
    }
}

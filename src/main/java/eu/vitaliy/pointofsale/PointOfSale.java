package eu.vitaliy.pointofsale;

import eu.vitaliy.pointofsale.dao.ProductRepository;
import eu.vitaliy.pointofsale.domain.Product;
import eu.vitaliy.pointofsale.exceptions.InvalideBarCodeException;
import eu.vitaliy.pointofsale.exceptions.ProductNotFoundException;
import eu.vitaliy.pointofsale.io.OutputDevice;

import java.util.LinkedList;
import java.util.List;

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 14:49
 */
public class PointOfSale {

    private ProductRepository productRepository;

    private List<OutputDevice> outputDevices = new LinkedList<OutputDevice>();

    public PointOfSale(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addOutputDevice(OutputDevice outputDevice) {
        outputDevices.add(outputDevice);
    }

    public void readCode(String code) {
        try{
            Product product = productRepository.read(code);
            handleProduct(product);
        }catch(ProductNotFoundException ex){
            handleError("Product not found");
        }catch(InvalideBarCodeException ex){
            handleError("Invalid bar-code");
        }
    }

    private void handleProduct(Product product) {
        for (OutputDevice outputDevice : outputDevices) {
            outputDevice.write(product);
        }
    }

    private void handleError(String message) {
        for (OutputDevice outputDevice : outputDevices) {
            outputDevice.writeError(message);
        }
    }
}

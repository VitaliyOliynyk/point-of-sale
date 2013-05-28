package eu.vitaliy.pointofsale

import eu.vitaliy.pointofsale.dao.ProductRepository
import eu.vitaliy.pointofsale.domain.Product
import eu.vitaliy.pointofsale.exceptions.InvalideBarCodeException
import eu.vitaliy.pointofsale.exceptions.ProductNotFoundException
import eu.vitaliy.pointofsale.io.LCD
import eu.vitaliy.pointofsale.io.OutputDevice
import groovy.util.logging.Log
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 14:42
 */
@Log
class PointOfSaleSpecification extends Specification {

    final static String EXISTS_PRODUCT_CODE = "00001"
    final static List PRODUCTS = [
            new Product("00001", "product 1", 12.34f),
            new Product("00002", "product 2", 56.78f),
            new Product("00003", "product 3", 90.12f),
    ]

    final static Map PRODUCT_MAP = PRODUCTS.collectEntries{
         [it.code, it]
    }
    final static String NOT_EXISTS_CODE = "NOT_EXISTS_CODE"

    @Unroll
    def 'Test Product scan (code: [#code], message: "#expectMessage")'() {
       given:
            ProductRepository productRepository = Mock(ProductRepository)
            productRepository.read(_) >> { String code ->
                if(code== null || code.isEmpty())
                    throw new InvalideBarCodeException()
                else if(PRODUCT_MAP[code] == null)
                    throw new ProductNotFoundException()
                PRODUCT_MAP[code]
            }

            PointOfSale pointOfSale = new PointOfSale(productRepository)
            ByteArrayOutputStream bos = new ByteArrayOutputStream()
            OutputDevice lcd = new LCD(bos)
            pointOfSale.addOutputDevice(lcd)

            pointOfSale.readCode(code)
            String outputMessage = new String(bos.toString())
            log.info(outputMessage)
        expect:
            outputMessage == expectMessage
        where:
            code             | expectMessage
            PRODUCTS[0].code | "Product '${PRODUCTS[0].name}' price is: ${PRODUCTS[0].price}"
            PRODUCTS[1].code | "Product '${PRODUCTS[1].name}' price is: ${PRODUCTS[1].price}"
            PRODUCTS[2].code | "Product '${PRODUCTS[2].name}' price is: ${PRODUCTS[2].price}"
            NOT_EXISTS_CODE  | "Product not found"
            ""               | "Invalid bar-code"
            null             | "Invalid bar-code"
    }
}

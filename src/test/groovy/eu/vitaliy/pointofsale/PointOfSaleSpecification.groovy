package eu.vitaliy.pointofsale

import eu.vitaliy.pointofsale.dao.ProductRepository
import eu.vitaliy.pointofsale.domain.Product
import eu.vitaliy.pointofsale.io.LCD
import eu.vitaliy.pointofsale.io.OutputDevice
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 14:42
 */
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

    @Unroll
    def "if the product is found in products database than it's name and price is printed on LCD display (code:#code)"() {
       given:
            ProductRepository productRepository = Mock(ProductRepository)
            productRepository.read(_) >> { String code ->
                PRODUCT_MAP[code]
            }
            PointOfSale pointOfSale = new PointOfSale(productRepository)
            ByteArrayOutputStream bos = new ByteArrayOutputStream()
            OutputDevice lcd = new LCD(bos)
            pointOfSale.addOutputDevice(lcd)

            pointOfSale.readCode(code)
            String outputMessage = new String(bos.toString())
            String expectMessage = "Product '${name}' price is: ${price}"
        expect:
            outputMessage == expectMessage
        where:
            code             | name             | price
            PRODUCTS[0].code | PRODUCTS[0].name | PRODUCTS[0].price
            PRODUCTS[1].code | PRODUCTS[1].name | PRODUCTS[1].price
            PRODUCTS[2].code | PRODUCTS[2].name | PRODUCTS[2].price
    }
}

package eu.vitaliy.pointofsale.dao;

import eu.vitaliy.pointofsale.domain.Product;

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 15:08
 */
public interface ProductRepository {
    Product read(String code);
}

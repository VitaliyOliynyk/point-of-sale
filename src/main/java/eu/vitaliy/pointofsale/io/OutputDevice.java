package eu.vitaliy.pointofsale.io;

import eu.vitaliy.pointofsale.domain.Product;

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 14:51
 */
public interface OutputDevice {
      void write(Product outputData);
}

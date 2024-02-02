package mvpproject.crmbaseservice.util;

import mvpproject.crmbaseservice.erorr.SalesCreateException;
import mvpproject.crmbaseservice.model.dto.SalesDTO;

public class NullFieldCheck {

    public static void checkFields(SalesDTO sales) {
        if (sales == null) {
            throw new SalesCreateException("Sales object is null", "sales");
        }
        if (sales.getClientId() == null) {
            throw new SalesCreateException("Failed to create sales: clientId field is null", "clientId");
        }
        if (sales.getProductId() == null) {
            throw new SalesCreateException("Failed to create sales: productId field is null", "productId");
        }
        if (sales.getQuantity() == null) {
            throw new SalesCreateException("Failed to create sales: quantity field is null", "quantity");
        }
        if (sales.getSalesDate() == null) {
            throw new SalesCreateException("Failed to create sales: salesDate field is null", "salesDate");
        }
    }
}

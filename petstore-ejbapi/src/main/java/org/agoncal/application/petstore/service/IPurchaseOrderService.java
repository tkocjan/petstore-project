package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.CreditCard;
import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.model.PurchaseOrder;
import org.agoncal.application.petstore.model.ShoppingCartItem;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Tom on 4/13/2017.
 */
@Remote
public interface IPurchaseOrderService extends IAbstractService<PurchaseOrder> {
    PurchaseOrder createOrder(@NotNull Customer customer, @NotNull CreditCard creditCard, List<ShoppingCartItem> cartItems);

    PurchaseOrder findOrder(@NotNull Long orderId);

    List<PurchaseOrder> findAllOrders();

    void removeOrder(@NotNull PurchaseOrder order);
}

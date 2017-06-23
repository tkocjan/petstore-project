package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.exceptions.ValidationException;
import org.agoncal.application.petstore.model.CreditCard;
import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.model.OrderLine;
import org.agoncal.application.petstore.model.PurchaseOrder;
import org.agoncal.application.petstore.model.ShoppingCartItem;
import org.agoncal.application.petstore.util.Loggable;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@LocalBean
@Loggable
@Default
public class PurchaseOrderService extends AbstractService<PurchaseOrder>
   implements IPurchaseOrderService {

   // ======================================
   // =             Attributes             =
   // ======================================


   // ======================================
   // =              Public Methods        =
   // ======================================

   @Override
   public PurchaseOrder createOrder(@NotNull Customer customer, @NotNull CreditCard creditCard, final List<ShoppingCartItem> cartItems)
   {

      // OMake sure the object is valid
      if (cartItems == null || cartItems.size() == 0)
         throw new ValidationException("Shopping cart is empty"); // TODO exception bean validation

      // Creating the order
      PurchaseOrder order = new PurchaseOrder(entityManager.merge(customer), creditCard, customer.getHomeAddress());

      // From the shopping cart we create the order lines
      Set<OrderLine> orderLines = new HashSet<>();

      for (ShoppingCartItem cartItem : cartItems)
      {
         orderLines.add(new OrderLine(cartItem.getQuantity(), entityManager.merge(cartItem.getItem())));
      }
      order.setOrderLines(orderLines);

      // Persists the object to the database
      entityManager.persist(order);

      return order;
   }

   @Override
   public PurchaseOrder findOrder(@NotNull Long orderId)
   {
      return entityManager.find(PurchaseOrder.class, orderId);
   }

   @Override
   public List<PurchaseOrder> findAllOrders()
   {
      TypedQuery<PurchaseOrder> typedQuery = entityManager.createNamedQuery(PurchaseOrder.FIND_ALL, PurchaseOrder.class);
      return typedQuery.getResultList();
   }

   @Override
   public void removeOrder(@NotNull PurchaseOrder order)
   {
      entityManager.remove(entityManager.merge(order));
   }

   // ======================================
   // =         Protected methods          =
   // ======================================

   @Override
   protected Predicate[] getSearchPredicates(Root<PurchaseOrder> root, PurchaseOrder example)
   {
      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String street1 = example.getCustomer().getHomeAddress().getStreet1();
      if (street1 != null && !"".equals(street1))
      {
         predicatesList.add(builder.like(builder.lower(root.<String>get("street1")), '%' + street1.toLowerCase() + '%'));
      }
      String street2 = example.getCustomer().getHomeAddress().getStreet2();
      if (street2 != null && !"".equals(street2))
      {
         predicatesList.add(builder.like(builder.lower(root.<String>get("street2")), '%' + street2.toLowerCase() + '%'));
      }
      String city = example.getCustomer().getHomeAddress().getCity();
      if (city != null && !"".equals(city))
      {
         predicatesList.add(builder.like(builder.lower(root.<String>get("city")), '%' + city.toLowerCase() + '%'));
      }
      String state = example.getCustomer().getHomeAddress().getState();
      if (state != null && !"".equals(state))
      {
         predicatesList.add(builder.like(builder.lower(root.<String>get("state")), '%' + state.toLowerCase() + '%'));
      }
      String zipcode = example.getCustomer().getHomeAddress().getZipcode();
      if (zipcode != null && !"".equals(zipcode))
      {
         predicatesList.add(builder.like(builder.lower(root.<String>get("zipcode")), '%' + zipcode.toLowerCase() + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }
}
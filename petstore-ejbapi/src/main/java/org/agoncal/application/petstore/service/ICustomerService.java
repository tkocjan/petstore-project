package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Country;
import org.agoncal.application.petstore.model.Customer;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Tom on 4/19/2017.
 */
@Remote
public interface ICustomerService extends IAbstractService<Customer> {
    boolean doesLoginAlreadyExist(@NotNull String login);

    Customer createCustomer(@NotNull Customer customer);

    Customer findCustomer(@NotNull String login);

    Customer findCustomer(@NotNull String login, @NotNull String password);

    List<Customer> findAllCustomers();

    Customer updateCustomer(@NotNull Customer customer);

    void removeCustomer(@NotNull Customer customer);

    Country findCountry(@NotNull Long countryId);
}

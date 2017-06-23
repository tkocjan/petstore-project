package org.agoncal.application.petstore.security;

import org.agoncal.application.petstore.model.Customer;
import org.agoncal.application.petstore.service.ICustomerService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

/**
 * @author blep
 *         Date: 12/02/12
 *         Time: 11:59
 */

public class SimpleLoginModule implements LoginModule
{

   // ======================================
   // =             Attributes             =
   // ======================================

   private CallbackHandler callbackHandler;

   @EJB
   private ICustomerService customerService;

   @Override
   public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> stringMap, Map<String, ?> stringMap1)
   {
      this.callbackHandler = callbackHandler;
   }

   @Override
   public boolean login() throws LoginException
   {

      NameCallback nameCallback = new NameCallback("Name : ");
      PasswordCallback passwordCallback = new PasswordCallback("Password : ", false);
      try
      {
         callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
         String username = nameCallback.getName();
         String password = new String(passwordCallback.getPassword());
         nameCallback.setName("");
         passwordCallback.clearPassword();
         Customer customer = customerService.findCustomer(username, password);

         if (customer == null)
         {
            throw new LoginException("Authentication failed");
         }

         return true;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new LoginException(e.getMessage());
      }
   }

   @Override
   public boolean commit() throws LoginException
   {
      return true;
   }

   @Override
   public boolean abort() throws LoginException
   {
      return true;
   }

   @Override
   public boolean logout() throws LoginException
   {
      return true;
   }
}

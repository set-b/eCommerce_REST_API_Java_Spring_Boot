package com.example.ecommerce.controllers;

import static com.example.ecommerce.constants.StringConstants.CONTEXT_CUSTOMERS;
import static com.example.ecommerce.constants.StringConstants.DELETE_REQUEST;
import static com.example.ecommerce.constants.StringConstants.POST_REQUEST;
import static com.example.ecommerce.constants.StringConstants.QUERY_REQUEST;
import static com.example.ecommerce.constants.StringConstants.UPDATE_REQUEST;

import com.example.ecommerce.models.Customer;
import com.example.ecommerce.services.CustomerService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller takes methods from the CustomerServiceImpl class through CustomerService, and
 * uses these methods to manipulate Customer Objects/Entities. It handles requests about Customer
 * information, which the user can send via URL.
 */
@RestController
@RequestMapping(CONTEXT_CUSTOMERS)
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Autowired
  private CustomerService customerService;

  /**
   * Queries Customers.
   *
   * @param customer the Customer(s) matching the user's supplied information.
   * @return a list of Customers, containing Objects which match the information supplied, or a list
   * of all Customers if the query is empty.
   */
  @GetMapping
  public ResponseEntity<List<Customer>> queryCustomers(Customer customer) {
    logger.info(new Date() + QUERY_REQUEST + customer.toString());

    return new ResponseEntity<>(customerService.queryCustomers(customer), HttpStatus.OK);
  }

  /**
   * Retrieves the Customer tht has the given id.
   *
   * @param id the id of the Customer to be retrieved.
   * @return a Customer Object with the given id, if it exists.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    logger.info(new Date() + QUERY_REQUEST + "customer with id " + id);

    return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
  }

  /**
   * Saves a Customer Object with the data/state provided by the user to the database.
   *
   * @param customer the Customer to be saved.
   * @return the dta of the Customer that was saved to the database, if successful.
   */
  @PostMapping
  public ResponseEntity<Customer> postCustomer(@Valid @RequestBody Customer customer) {
    logger.info(new Date() + POST_REQUEST + "customer");

    return new ResponseEntity<Customer>(customerService.addCustomer(customer), HttpStatus.CREATED);
  }

  /**
   * Finds and replaces a Customer Object with another, based on the id and data/state supplied by
   * the user.
   *
   * @param id       the id of the Customer to be updated.
   * @param customer the Customer data which will replace the old Customer data.
   * @return the successfully updated Customer
   */
  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomerById(@PathVariable Long id,
      @Valid @RequestBody Customer customer) {
    logger.info(new Date() + UPDATE_REQUEST + "customer with id " + id);

    return new ResponseEntity<Customer>(customerService.updateCustomerById(id, customer),
        HttpStatus.OK);
  }

  /**
   * Finds and deletes a Customer from the database with the id given by the user.
   *
   * @param id the id of the Customer to be deleted.
   * @return a deleted status, if the id exists in the database.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Customer> deleteCustomerById(@PathVariable Long id) {
    logger.info(new Date() + DELETE_REQUEST + "customer with id " + id);

    customerService.deleteCustomerById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

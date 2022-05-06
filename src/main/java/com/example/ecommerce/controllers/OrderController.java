package com.example.ecommerce.controllers;

import static com.example.ecommerce.constants.StringConstants.CONTEXT_ORDERS;
import static com.example.ecommerce.constants.StringConstants.DELETE_REQUEST;
import static com.example.ecommerce.constants.StringConstants.POST_REQUEST;
import static com.example.ecommerce.constants.StringConstants.QUERY_REQUEST;
import static com.example.ecommerce.constants.StringConstants.UPDATE_REQUEST;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.services.OrderService;
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
 * This controller takes methods from the OrderServiceImpl class through OrderService, and uses
 * these methods to manipulate Order Objects/Entities. It handles requests about Order information,
 * which the user can send via URL.
 */
@RestController
@RequestMapping(CONTEXT_ORDERS)
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private OrderService orderService;

  /**
   * Queries Orders.
   *
   * @param order the Order(s) matching the user's supplied information.
   * @return a list of Orders, containing Objects which match the information supplied, or a list of
   * all Orders if the query is empty.
   */
  @GetMapping
  public ResponseEntity<List<Order>> queryOrders(Order order) {
    logger.info(new Date() + QUERY_REQUEST + order.toString());

    return new ResponseEntity<>(orderService.queryOrders(order), HttpStatus.OK);
  }

  /**
   * Retrieves the Order tht has the given id.
   *
   * @param id the id of the Order to be retrieved.
   * @return a Order Object with the given id, if it exists.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    logger.info(new Date() + QUERY_REQUEST + "order with id " + id);

    return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
  }

  /**
   * Saves a Order Object with the data/state provided by the user to the database.
   *
   * @param order the Order to be saved.
   * @return the dta of the Order that was saved to the database, if successful.
   */
  @PostMapping
  public ResponseEntity<Order> postOrder(@Valid @RequestBody Order order) {
    logger.info(new Date() + POST_REQUEST + "order");

    return new ResponseEntity<Order>(orderService.addOrder(order), HttpStatus.CREATED);
  }

  /**
   * Finds and replaces a Order Object with another, based on the id and data/state supplied by the
   * user.
   *
   * @param id    the id of the Order to be updated.
   * @param order the Order data which will replace the old Order data.
   * @return the successfully updated Order
   */
  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrderById(@PathVariable Long id,
      @Valid @RequestBody Order order) {
    logger.info(new Date() + UPDATE_REQUEST + "order with id " + id);

    return new ResponseEntity<Order>(orderService.updateOrderById(id, order),
        HttpStatus.OK);
  }

  /**
   * Finds and deletes a Order from the database with the id given by the user.
   *
   * @param id the id of the Order to be deleted.
   * @return a deleted status, if the id exists in the database.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Order> deleteOrderById(@PathVariable Long id) {
    logger.info(new Date() + DELETE_REQUEST + "order with id " + id);

    orderService.deleteOrderById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

package com.example.ecommerce.controllers;

import static com.example.ecommerce.constants.StringConstants.CONTEXT_USERS;
import static com.example.ecommerce.constants.StringConstants.DELETE_REQUEST;
import static com.example.ecommerce.constants.StringConstants.POST_REQUEST;
import static com.example.ecommerce.constants.StringConstants.QUERY_REQUEST;
import static com.example.ecommerce.constants.StringConstants.UPDATE_REQUEST;

import com.example.ecommerce.models.User;
import com.example.ecommerce.services.UserService;
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
 * This controller takes methods from the UserServiceImpl class through UserService, and uses these
 * methods to manipulate User Objects/Entities. It handles requests about User information, which
 * the user can send via URL.
 */
@RestController
@RequestMapping(CONTEXT_USERS)
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  /**
   * Queries Users.
   *
   * @param user the User(s) matching the user's supplied information.
   * @return a list of Users, containing Objects which match the information supplied, or a list of
   * all Users if the query is empty.
   */
  @GetMapping
  public ResponseEntity<List<User>> queryUsers(User user) {
    logger.info(new Date() + QUERY_REQUEST + user.toString());

    return new ResponseEntity<>(userService.queryUsers(user), HttpStatus.OK);
  }

  /**
   * Retrieves the User tht has the given id.
   *
   * @param id the id of the User to be retrieved.
   * @return a User Object with the given id, if it exists.
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    logger.info(new Date() + QUERY_REQUEST + "user with id " + id);

    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  /**
   * Saves a User Object with the data/state provided by the user to the database.
   *
   * @param user the User to be saved.
   * @return the dta of the User that was saved to the database, if successful.
   */
  @PostMapping
  public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
    logger.info(new Date() + POST_REQUEST + "user");

    return new ResponseEntity<User>(userService.addUser(user), HttpStatus.CREATED);
  }

  /**
   * Finds and replaces a User Object with another, based on the id and data/state supplied by the
   * user.
   *
   * @param id   the id of the User to be updated.
   * @param user the User data which will replace the old User data.
   * @return the successfully updated User
   */
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUserById(@PathVariable Long id,
      @Valid @RequestBody User user) {
    logger.info(new Date() + UPDATE_REQUEST + "user with id " + id);

    return new ResponseEntity<User>(userService.updateUserById(id, user),
        HttpStatus.OK);
  }

  /**
   * Finds and deletes a User from the database with the id given by the user.
   *
   * @param id the id of the User to be deleted.
   * @return a deleted status, if the id exists in the database.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
    logger.info(new Date() + DELETE_REQUEST + "user with id " + id);

    userService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

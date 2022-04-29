package com.example.restgreeting.controllers;

import static com.example.restgreeting.constants.StringConstants.CONTEXT_GREETINGS;
import static com.example.restgreeting.constants.StringConstants.DELETE_REQUEST;
import static com.example.restgreeting.constants.StringConstants.POST_REQUEST;
import static com.example.restgreeting.constants.StringConstants.QUERY_REQUEST;
import static com.example.restgreeting.constants.StringConstants.UPDATE_REQUEST;

import com.example.restgreeting.exceptions.BadDataResponse;
import com.example.restgreeting.models.Greeting;
import com.example.restgreeting.services.GreetingService;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

@RestController
@RequestMapping(CONTEXT_GREETINGS)
public class GreetingController {

  private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

  @Autowired
  private GreetingService greetingService;

  //todo: design unit tests,
  // use log, doc, readme
  @GetMapping
  public ResponseEntity<List<Greeting>> queryGreetings(Greeting greeting) {
    logger.info(new Date() + QUERY_REQUEST + greeting.toString());

    return new ResponseEntity<>(greetingService.queryGreetings(greeting), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Greeting> getGreetingById(@PathVariable Long id) {
    logger.info(new Date() + QUERY_REQUEST + "greeting with id " + id);

    return new ResponseEntity<>(greetingService.getGreetingById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Greeting> postGreeting(@Valid @RequestBody Greeting greeting) {
    logger.info(new Date() + POST_REQUEST + "greeting");

    String pattern = "[^A-Za-z\\s\\?!.]";
    Pattern notAGreeting = Pattern.compile(pattern);
    Matcher matcher = notAGreeting.matcher(greeting.getText());
    if (matcher.find()) {
      throw new BadDataResponse("Greeting text must only contain letters and punctuation");
    }
    return new ResponseEntity<Greeting>(greetingService.postGreeting(greeting), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Greeting> updateGreetingById(@PathVariable Long id,
      @Valid @RequestBody Greeting greeting) {
    logger.info(new Date() + UPDATE_REQUEST + "greeting with id " + id);

    return new ResponseEntity<Greeting>(greetingService.updateGreetingById(id, greeting),
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Greeting> deleteGreetingById(@PathVariable Long id) {
    logger.info(new Date() + DELETE_REQUEST + "greeting with id " + id);

    greetingService.deleteGreetingById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

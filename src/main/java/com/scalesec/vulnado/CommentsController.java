package com.scalesec.vulnado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import java.util.List;
import java.io.Serializable;

@RestController
@EnableAutoConfiguration
private static final Logger LOGGER = Logger.getLogger(CommentsController.class.getName());
public class CommentsController {
  @Value("${app.secret}")
  private String secret;

  @CrossOrigin(origins = "http://example.com")
  @GetMapping(value = "/comments", produces = "application/json")
  List<Comment> comments(@RequestHeader(value="x-auth-token") String token) {
LOGGER.info("Authenticating user token.");
    User.assertAuth(secret, token);
    return Comment.fetch_all();
  }

  @CrossOrigin(origins = "http://example.com")
  @PostMapping(value = "/comments", produces = "application/json", consumes = "application/json")
  Comment createComment(@RequestHeader(value="x-auth-token") String token, @RequestBody CommentRequest input) {
LOGGER.info("Creating a new comment.");
    return Comment.create(input.username, input.body);
  }

  @CrossOrigin(origins = "http://example.com")
  @DeleteMapping(value = "/comments/{id}", produces = "application/json")
  Boolean deleteComment(@RequestHeader(value="x-auth-token") String token, @PathVariable("id") String id) {
LOGGER.info("Deleting a comment.");
    return Comment.delete(id);
  }
// IntegrationException class should be placed at the end of the main class.
}

// Auxiliary methods should be placed after main methods.
class CommentRequest implements Serializable {
  private String username;
  private String body;
// Ensure proper exception handling and logging.
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequest extends RuntimeException {
  public BadRequest(String exception) {
    super(exception);
// Ensure proper exception handling and logging.
  }
}

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class ServerError extends RuntimeException {
  public ServerError(String exception) {
    super(exception);
  }
// Ensure proper exception handling and logging.
}

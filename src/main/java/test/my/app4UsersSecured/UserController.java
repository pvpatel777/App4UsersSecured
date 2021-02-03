package test.my.app4UsersSecured;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.istack.NotNull;

@RestController
@Validated
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepo;

	@CrossOrigin("*")
	@RequestMapping(path = "users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers(
			@AuthenticationPrincipal Jwt jwt) {
		logger.debug("getUser");

		String format = String.format(
				"Resource accessed by: %s (with subjectId: %s)",
				jwt.getClaims().get("user_name"), jwt.getSubject());

		logger.debug("token:" + format);
		return ResponseEntity.status(HttpStatus.OK).body(userRepo.findAll());
	}

	@CrossOrigin("*")
	@RequestMapping(path = "users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable @NotNull Integer id) {
		logger.debug("getUser");
		return ResponseEntity.status(HttpStatus.OK)
				.body(userRepo.findById(id).orElse(null));
	}

	@CrossOrigin("*")
	@RequestMapping(path = "users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable @NotNull Integer id) {
		logger.debug("deleteUser");
		if (id != null && userRepo.existsById(id)) {
			userRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@CrossOrigin("*")
	@RequestMapping(path = "users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		logger.debug("createUser");
		if (user != null && user.getId() == null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(userRepo.save(user));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@CrossOrigin("*")
	@RequestMapping(path = "users", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		logger.debug("updateUser");
		if (user != null && user.getId() != null
				&& userRepo.existsById(user.getId())) {
			user = userRepo.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

}

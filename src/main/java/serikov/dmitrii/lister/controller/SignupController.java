package serikov.dmitrii.lister.controller;

import java.util.function.Function;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import reactor.core.publisher.Mono;
import serikov.dmitrii.lister.model.UserProfile;
import serikov.dmitrii.lister.service.UserProfileService;

/**
 *
 * @author Dmitrii Serikov
 */
@RestController
@Api(tags = "signup")
public class SignupController {

	private static final XLogger log = XLoggerFactory.getXLogger(SignupController.class);

	@Autowired
	private UserProfileService userProfileService;

	/**
	 * Handles user registration process.
	 */
	@PostMapping(value = "/signup", produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "Register a new user within the system", nickname = "signup")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "201", description = "User registered successfully" //
//			content = @Content( //
//					mediaType = "text/plain", //
//					schema = @Schema(implementation = Void.class)) //
	)
	@ApiResponse(responseCode = "400", description = "Bad Request")
	@ApiResponse(responseCode = "401", description = "Unauthorized")
	@ApiResponse(responseCode = "403", description = "Access forbidden")
	@ApiResponse(responseCode = "409", description = "Validation Failed: User already exists")
	public Mono<ResponseEntity<Void>> signup(@RequestBody UserProfile userProfile) {
		if (UserProfile.isValid(userProfile)) {
			final String username = userProfile.getUsername();
			if (username != null && !username.isEmpty()) {
				// check if user exists in DB
				return userProfileService.isUserPresent(username).flatMap(mapToResponseEntity(userProfile));
			}
		}

		return Mono.just(ResponseEntity.badRequest().build());
	}

	private Function<? super Boolean, ? extends Mono<? extends ResponseEntity<Void>>> mapToResponseEntity(
			UserProfile userProfile) {
		return isUserPresent -> {
			final String username = userProfile.getUsername();
			log.info("User[{}] {}found", username, Boolean.TRUE.equals(isUserPresent) ? "" : "NOT ");
			if (Boolean.TRUE.equals(isUserPresent)) {
				// user exists, send an error
				return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
			} else {
				log.info("Creating a new user with the name [{}]", username);
				// user doesn't exist, create a new user profile
				// automatically log user in after regestering
				userProfile.setLoggedIn(true);

				return userProfileService.save(userProfile).map(savedUserProfile -> {
					final boolean isUserSaved = savedUserProfile.getUsername() != null;
					return ResponseEntity.status(isUserSaved ? HttpStatus.CREATED : HttpStatus.CONFLICT).build();
				});
			}
		};
	}
}

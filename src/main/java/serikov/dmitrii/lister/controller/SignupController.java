package serikov.dmitrii.lister.controller;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
	 * 
	 * @throws JsonProcessingException
	 */
	@PostMapping(value = "/signup", produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value = "/signup", nickname = "signup")
	@Operation( //
			summary = "Register a new user within the system", //
			responses = { //
					@ApiResponse(responseCode = "200", description = "User registered successfully", //
							content = @Content( //
									mediaType = "text/plain", //
									schema = @Schema(implementation = Boolean.class))), //
					@ApiResponse(responseCode = "409", description = "Validation Failed: User already exists"), //
			} //
	)
	public Mono<ResponseEntity<Boolean>> signup(@RequestBody UserProfile userProfile) {
		if (UserProfile.isValid(userProfile)) {
			final String username = userProfile.getUsername();
			if (username != null && !username.isEmpty()) {
				// check if user exists in DB
				return userProfileService.isUserPresent(username) //
						.flatMap(isUserPresent -> {
							log.info("User[{}] {}found", username, Boolean.TRUE.equals(isUserPresent) ? "" : "NOT ");
							if (Boolean.TRUE.equals(isUserPresent)) {
								// user exists, send an error
								return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
							} else {
								// user doesn't exist, create a new user profile
								// automatically log user in after regestering
								userProfile.setLoggedIn(true);

								return userProfileService.save(userProfile).map(
										savedUserProfile -> ResponseEntity.ok(savedUserProfile.getUsername() != null));
							}
						});
			}
		}

		return Mono.just(ResponseEntity.ok(false));
	}

}

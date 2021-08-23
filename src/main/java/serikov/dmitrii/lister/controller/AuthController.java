package serikov.dmitrii.lister.controller;

import java.security.Principal;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import reactor.core.publisher.Mono;
import serikov.dmitrii.lister.model.UserProfile;

/**
 *
 * @author Dmitrii Serikov
 */
@RestController
@Api(tags = "login")
public class AuthController {

	private static final XLogger log = XLoggerFactory.getXLogger(AuthController.class);

	/**
	 * Handles user login process.
	 */
	@PostMapping(value = "/login")
	@ApiOperation(value = "User login and logout", nickname = "auth")
	@ApiResponse(responseCode = "200", description = "User logged in successfully", //
			content = @Content( //
					mediaType = "text/plain", //
					schema = @Schema(implementation = Boolean.class)))
	@ApiResponse(responseCode = "400", description = "Unable to log in")
	public Mono<ResponseEntity<UserProfile>> login(@RequestBody(required = false) UserProfile userProfile,
			final Principal user) {
		log.info("Hello " + user.getName());
		log.info("User profile: {}", userProfile);
		// check if user already logged in using a token
		if (userProfile == null) {
			// check JWT token
			// return user profile
		} else {
			// authenticate user based on username and password
		}
		return Mono.just(ResponseEntity.ok(new UserProfile()));
	}

	/**
	 * Handles user logout.
	 */
	@GetMapping("/logout")
	// @ApiOperation(value = "/logout", nickname = "auth")
	public Mono<ResponseEntity<Boolean>> login() {
		return Mono.just(ResponseEntity.ok(true));
	}

//
//	/**
//	 * Handles the HTTP <code>POST</code> method.
//	 *
//	 * @param request
//	 *            servlet request
//	 * @param response
//	 *            servlet response
//	 */
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
//		try {
//			// set response type to JSON
//			response.setContentType("application/json");
//			// get UserProfile from request body
//			UserProfile userProfile = (UserProfile) Utils.fromJson(LoginServlet.class.getName(), request,
//					UserProfile.class);
//			// get username and password from th request body
//			String username = userProfile.getUsername();
//			String password = userProfile.getPassword();
//			// get session object and set session relative attributes
//			HttpSession session = request.getSession(false);
//			if (session == null) {
//				session = request.getSession(true);
//			}
//			// get session timeout in seconds
//			int sessionTimeout = session.getMaxInactiveInterval();
//			// connect to database
//			if (DBUtils.connect()) {
//				// check if user with the right credentials from the request
//				// exists
//				if (DBUtils.checkCreds(username, password)) {
//					// credentials are correct
//					// create a new session
//					request.getSession(false).invalidate();
//					session = request.getSession();
//					//
//					if (session.isNew()) {
//						logger.info("New session [" + session.getId() + "] was created for user ["
//								+ request.getRemoteAddr() + "]");
//					}
//					//
//					userProfile = DBUtils.getUserProfile(username);
//					List<String> lists = DBUtils.getLists(username);
//					userProfile.setLoggedIn(true);
//					userProfile.setListTitles(lists);
//					userProfile.setTimeout(sessionTimeout);
//					session.setAttribute("RemoteIP", new String(request.getRemoteAddr()));
//					session.setAttribute("Username", new String(username));
//					session.setAttribute("Data", userProfile);
//					// send user profile back to client
//					logger.info(
//							"User [" + request.getRemoteAddr() + "] is logged in in session [" + session.getId() + "]");
//					Utils.sendResponse(LoginServlet.class.getName(), response, userProfile);
//				} // wrong credentials
//				else {
//					Utils.sendResponse(LoginServlet.class.getName(), response, "Username or password is incorrect");
//				}
//				// close the connection
//				DBUtils.disconnect();
//			} else {
//				response.sendError(HttpServletResponse.SC_CONFLICT,
//						"ServerError: LoginServlet cannot connect to the database");
//			}
//		} catch (Exception e) {
//			try {
//				response.sendError(HttpServletResponse.SC_CONFLICT, "ServerError: " + e.getMessage());
//			} catch (IOException ie) {
//				logger.error(Utils.errorMesage(ie));
//			}
//			logger.error(Utils.errorMesage(e));
//		}
//	}
//
//	/**
//	 * Returns a short description of the servlet.
//	 *
//	 * @return a String containing servlet description
//	 */
//	@Override
//	public String getServletInfo() {
//		return "LoginServlet is responsible for user authentification process";
//	}
}

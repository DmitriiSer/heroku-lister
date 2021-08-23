package serikov.dmitrii.lister.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "data")
@RestController
public class DataController {

	private static final XLogger log = XLoggerFactory.getXLogger(DataController.class);

	@GetMapping("/data")
	@CrossOrigin("http://localhost:4200")
	public Map<String, Object> get(final Principal principal) {
		String username = null;
		final Map<String, Object> attrs = new HashMap<>();
		if (principal instanceof OAuth2AuthenticationToken) {
			log.info("Principal is OAuth2AuthenticationToken");
			final OAuth2User user = ((OAuth2AuthenticationToken) principal).getPrincipal();
			attrs.putAll(user.getAttributes());
			username = Arrays.asList("login", "name").stream() //
					.filter(key -> attrs.get(key) != null) //
					.map(k -> attrs.get(k).toString()) //
					.findFirst() //
					.orElse(null);
		} else if (principal instanceof JwtAuthenticationToken) {
			log.info("Principal is JwtAuthenticationToken");
			JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
			attrs.putAll(token.getTokenAttributes());
			username = attrs.get("sub").toString();
		}
		log.info("User attributes = {}", Arrays.toString(attrs.entrySet().toArray()));		
		log.info("Username = {}", username);

		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("message", "OK");
		return map;
	}

}

package serikov.dmitrii.lister.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class Oauth2ConfigurerAdapter extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v3 (OpenAPI)
			"/swagger-resources", //
			"/swagger-resources/**", //
			"/v3/api-docs/**", //
			"/swagger-ui/**" //
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.cors().and() //
				.authorizeRequests() //
				.antMatchers(AUTH_WHITELIST).permitAll() // whitelist Swagger UI resources
				// .antMatchers("/v3/api-docs").permitAll() //
				// .antMatchers("/swagger-ui/**").permitAll() //
				// .antMatchers("/**")
				.antMatchers("/**").authenticated()
				// .anyRequest().authenticated() //
				.and() //
				.oauth2ResourceServer().jwt();
	}

}

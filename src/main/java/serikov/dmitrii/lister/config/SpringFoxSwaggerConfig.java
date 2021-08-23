package serikov.dmitrii.lister.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
//@EnableOpenApi
public class SpringFoxSwaggerConfig {

	@Value("${app.name}")
	private String appName;

	@Value("${swagger.ui.title}")
	private String swaggerUiTitle;

	@Value("${swagger.ui.descriotion}")
	private String swaggerUiDescriotion;

	@Value("${swagger.ui.version}")
	private String swaggerUiVersion;

	@Value("${okta.oauth2.clientId}")
	private String clientId;

	@Value("${okta.oauth2.clientSecret}")
	private String clientSecret;

	@Value("${okta.oauth2.issuer}")
	private String oktaIssuer;

	@Bean
	public Docket api(TypeResolver typeResolver) {
		return new Docket(DocumentationType.OAS_30) //
				.apiInfo(apiInfo()) //
				.securitySchemes(Collections.singletonList(apiKey())) //
				.securityContexts(Collections.singletonList(securityContext())) //
				.enable(true) //
				.useDefaultResponseMessages(false) //
				.select() //
				.apis(RequestHandlerSelectors.any()) //
				.paths(Predicate.not(PathSelectors.regex("/error.*")))// <6>, regex must be in double quotes.
				.paths(PathSelectors.any()) //
				.build() //
				// .tags(getFirstTag(), getRestTags()) //
				// .globalRequestParameters(Collections.emptyList()) //
				.globalResponses(HttpMethod.GET, Arrays.asList( //
						new ResponseBuilder().code("401").description("Unauthorized").build(), //
						new ResponseBuilder().code("403").description("Access forbidden").build() //
				)) //
				.useDefaultResponseMessages(false) //
//				.additionalModels( //
//						typeResolver.resolve(UserProfile.class)) //
				.forCodeGeneration(true);
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "api_key", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder() //
				.securityReferences(defaultAuth()) //
				.operationSelector(os -> true) //
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder() //
				.deepLinking(true) //
				.displayOperationId(true) //
				.defaultModelsExpandDepth(1) //
				.defaultModelExpandDepth(1) //
				.defaultModelRendering(ModelRendering.EXAMPLE) //
				.displayRequestDuration(false) //
				.docExpansion(DocExpansion.NONE) //
				.filter(false) //
				// .maxDisplayedTags(null) //
				.operationsSorter(OperationsSorter.ALPHA) //
				.showExtensions(false) //
				.showCommonExtensions(false) //
				.tagsSorter(TagsSorter.ALPHA) //
				.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS) //
				.validatorUrl(null) //
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder() //
				.title(swaggerUiTitle) //
				.description(swaggerUiDescriotion) //
				.version(swaggerUiVersion) //
				.build();
	}

}

package serikov.dmitrii.lister.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import serikov.dmitrii.lister.model.UserProfile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	@Bean
	public Docket api(TypeResolver typeResolver) {
		return new Docket(DocumentationType.OAS_30) //
				.apiInfo(apiInfo()) //
				.enable(true) //
				.useDefaultResponseMessages(false) //
				.select() //
				.apis(RequestHandlerSelectors.any()) //
				.paths(PathSelectors.any()) //
				.build() //
				.tags(getFirstTag(), getRestTags()) //
				.globalRequestParameters(Collections.emptyList()) //
				.useDefaultResponseMessages(false) //
				.additionalModels( //
						typeResolver.resolve(UserProfile.class)) //
				.forCodeGeneration(true);
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
				.title("Lister API") //
				.description("Lister API Documentation") //
				.version("1.0.0") //
				.build();
	}

	private Tag[] getTags() {
		return new Tag[] { //
				new Tag("signup", "Registering new users") //
		};
	}

	private Tag getFirstTag() {
		if (getTags().length > 0) {
			return getTags()[0];
		}
		return null;
	}

	private Tag[] getRestTags() {
		final Tag[] allTags = getTags();
		if (allTags.length > 1) {
			return Arrays.copyOfRange(allTags, 1, allTags.length);
		}
		return new Tag[0];
	}

}

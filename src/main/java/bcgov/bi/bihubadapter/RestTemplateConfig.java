package bcgov.bi.bihubadapter;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The Class RestTemplateConfig.
 * @author HLiang
 */
@Configuration
public class RestTemplateConfig {
	
	@Value("${service.connection.timeout.ms}")
    int serviceConnectionTimeoutInMS;
	
	@Value("${service.read.timeout.ms}")
    int serviceReadTimeoutInMS;
	
	/**
	 * Rest template.
	 * Configure the bean only if service.connection.timeout.ms property is present.
	 *
	 * @param builder the builder
	 * @return the rest template
	 */
	@Bean
	@ConditionalOnProperty(value = "service.connection.timeout.ms", matchIfMissing = false)
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(serviceConnectionTimeoutInMS))
                .setReadTimeout(Duration.ofMillis(serviceReadTimeoutInMS))
                .build();
    }
}

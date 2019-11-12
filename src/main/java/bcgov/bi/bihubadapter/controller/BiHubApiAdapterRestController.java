package bcgov.bi.bihubadapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BiHubApiAdapterRestController {
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(BiHubApiAdapterRestController.class);
	
	/** The hubBiApiURL . */
	@Value("${hub.bi.api.url}")
    String hubBiApiURL;
	
	@Autowired(required = false)
    RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String home() {
		return "{\"message\" : \"Welcome\"}";
	}
	
	/**
     * Home.
     *
     * @return the string
     */
	@GetMapping(path = "/event-webhook", produces = MediaType.TEXT_PLAIN_VALUE)
	@PostMapping(path = "/event-webhook", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eventHook(){
		logger.info("Event received from JH-ETK hub.");
		
		String url = hubBiApiURL + "/v3/issuance/lookup";
		ResponseEntity<String> response = null;
		//send request to Hub api to request eTK event KPI
	    try {
	    	logger.info("Sending request to Hub API {} to request eTK event KPI...", url);
	    	
	    	// sleep for 5 seconds
			Thread.sleep(5000);
	    	
	    	response = restTemplate.exchange(url, HttpMethod.GET, getRequest(null, MediaType.APPLICATION_JSON), String.class);
	    	logger.debug("Request sent");
	    	if (HttpStatus.OK.equals(response.getStatusCode()) || HttpStatus.CREATED.equals(response.getStatusCode())) {
	    		logger.info("Request successfully sent, code: {}", response.getStatusCode());
	    		logger.debug("Response body: \n{}", response.getBody());
	    		return response.getBody();
			} else {
				logger.debug("Response failed, code: {}", response.getStatusCode());
			}
	    	
		} catch (Exception e) {
			logger.error("Exception occurred requesting eTK event KPI from Hub: {}", e.toString() + "; " + e.getMessage()); 
    	}
		
        return "{\"message\" : \"Failed requesting eVT KPI from the Hub\"}";
    }
	
	protected HttpEntity<Object> getRequest(final Object payload, final MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);

        if (payload != null) {
            return new HttpEntity<>(payload, headers);
        } else {
            return new HttpEntity<>(headers);
        }

    }
}

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import bcgov.jh.etk.jhetkcommon.model.PathConst;

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
	@PostMapping(path = "/event-webhook", produces = MediaType.TEXT_PLAIN_VALUE)
    public String eventHook(){
		logger.info("event received from JH-ETK hub");
		
		String url = hubBiApiURL + "/issuance/v3/lookup";
		ResponseEntity<String> response = null;
		//send request to Hub api to request eTK event KPI
	    try {
	    	logger.info("Send request to Hub API {} to request eTK event KPI", url);
	    	response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
	    	logger.debug("Request sent");
	    	if (HttpStatus.OK.equals(response.getStatusCode()) || HttpStatus.CREATED.equals(response.getStatusCode())) {
	    		logger.info("Response successfully, code: {}, body: {}", response.getStatusCode(), response.getBody());
	    		return response.getBody();
			} else {
				logger.debug("Response failed, code: {}", response.getStatusCode());
			}
	    	
		} catch (Exception e) {
			logger.error("Exception occurred requesting eTK event KPI from Hub: {}", e.toString() + "; " + e.getMessage()); 
    	}
		
        return "{\"message\" : \"Failed requesting eVT KPI from the Hub\"}";
    }
}

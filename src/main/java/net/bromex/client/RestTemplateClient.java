package net.bromex.client;

import feign.Request;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.util.Map;

@AllArgsConstructor
@Log4j2
@Service
public class RestTemplateClient<T, R> {

    private final RestTemplate restTemplate;

    public ResponseEntity<R> httpGet(String uri, Map<String, Object> params, Class<R> responseType) {
        log.debug("Start httpGet");
        return httpCall(uri, HttpMethod.GET, params, responseType);
    }

    private ResponseEntity<R> httpCall(final String uri, final HttpMethod httpMethod, Class<R> responseType) {
        return httpCall(uri, httpMethod, null, responseType);
    }

    private ResponseEntity<R> httpCall(final String uri, final HttpMethod httpMethod, Map<String, Object> payload, Class<R> responseType) {
        try {
            HttpEntity request = new HttpEntity(payload);
            ResponseEntity<R> response = restTemplate.exchange(uri, httpMethod, request, responseType);
            return response;

        } catch (RestClientResponseException e) {
            log.error("Fail to call uri:", uri);
            throw e;
        }
    }

}

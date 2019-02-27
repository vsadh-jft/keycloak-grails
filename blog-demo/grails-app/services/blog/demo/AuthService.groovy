package blog.demo

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Transactional
class AuthService {

    GrailsApplication grailsApplication

    RestTemplate restTemplate;

    String getTokenForUser(LoginCO loginCO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map =
                new LinkedMultiValueMap<String, String>();

        map.add("grant_type", grailsApplication.config.getProperty('keyclock.grant_type'));
        map.add("client_id", grailsApplication.config.getProperty('keyclock.client_id'));
        map.add("username", loginCO.userName);
        map.add("password", loginCO.password);
        map.add("client_secret", grailsApplication.config.getProperty('keyclock.client_secret'));

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String token = null
        try {
            ResponseEntity<AuthResponse> response = restTemplate.postForEntity(grailsApplication.config.getProperty('keyclock.auth.url'),
                    entity,
                    AuthResponse.class);
            token = response.getBody().accessToken
        } catch (Exception e) {
            log.error("Error occurred during fetch token for user : ${loginCO.userName}", e)
        }

        return token
    }
}

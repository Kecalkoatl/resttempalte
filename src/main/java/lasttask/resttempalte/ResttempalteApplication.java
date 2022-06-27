package lasttask.resttempalte;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lasttask.resttempalte.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ResttempalteApplication {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static List<String> sessionId;




    public static void main(String[] args) {
        SpringApplication.run(ResttempalteApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<String> entity = restTemplate.getForEntity(URL, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        HttpHeaders headers = entity.getHeaders();
        String msg = entity.getBody();
       sessionId = headers.get("Set-cookie");
        System.out.println(statusCode);
        System.out.println(headers);
        System.out.println(msg);


        User newUser = new User(3L, "Thomas", "Shelby", (byte)34);
        User user = new User(3L, "James", "Brown", (byte)34);


        HttpHeaders customHeaders = new HttpHeaders();
        customHeaders.setContentType(MediaType.APPLICATION_JSON);
        customHeaders.set(HttpHeaders.COOKIE, sessionId.get(0));





        HttpEntity<User> request = new HttpEntity<>(user, customHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        System.out.println(request.getHeaders());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());



        HttpEntity<User> requestPut = new HttpEntity<>(newUser, customHeaders);
        ResponseEntity<String> responsePut = restTemplate.exchange(URL, HttpMethod.PUT, requestPut, String.class);
        System.out.println(responsePut.getBody());
        System.out.println(responsePut.getHeaders());


        HttpEntity<User> requestDelete = new HttpEntity<>(newUser, customHeaders);
        Map < String, String > params = new HashMap <> ();
        ResponseEntity<String> responseDelete = restTemplate.exchange(URL + "/" + newUser.getId(), HttpMethod.DELETE, requestDelete, String.class);
        System.out.println(responseDelete.getBody());
        System.out.println(responseDelete.getHeaders());














    }


}

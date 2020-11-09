package controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

	@LocalServerPort
	private int port;

	@Test
	public void testSurveyRetrieveSurveyQuestion() {
		System.out.println("Random port: " + port);
		String url = "http://localhost:" + port + "/surveys/Survey1/questions/Question1";
		TestRestTemplate restTemplate = new TestRestTemplate();
//		String output = restTemplate.getForObject(url, String.class);
//		System.out.println("Response: " + output); 
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

//		System.out.println("Response: " + response); 

		assertTrue(response.getBody().contains("\"id\":\"Question1\""));
		assertTrue(response.getBody().contains("\"description\":\"Largest Country in the World\""));

		String expected = "{id:Question1,description:Largest Country in the World,correctAnswer:Russia}";
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void testJSONAssert() {
		JSONAssert.assertEquals("{id:1}", "{id:1, name:john}", false);
	}

}
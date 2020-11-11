package controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.Application;
import com.springboot.model.Question;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Before
	public void before() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));	
	}
	
	@Test
	public void SurveyRetrieveSurveyQuestion() {
		String url = createURLWithPort("/surveys/Survey1/questions/Question1");

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		assertTrue(response.getBody().contains("\"id\":\"Question1\""));
		assertTrue(response.getBody().contains("\"description\":\"Largest Country in the World\""));

		String expected = "{id:Question1,description:Largest Country in the World,correctAnswer:Russia}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void JSONAssert() {
		JSONAssert.assertEquals("{id:1}", "{id:1, name:john}", false);
	}

	@Test
	public void retrieveAllSurveyQuestions() throws Exception {
		ResponseEntity<List<Question>> response = restTemplate.exchange(
				createURLWithPort("/surveys/Survey1/questions"),
				HttpMethod.GET, 
				new HttpEntity<String>(null, headers),
				new ParameterizedTypeReference<List<Question>>() {}
		);

		Question sampleQuestion = new Question("Question1", "Largest Country in the World", "Russia",
				Arrays.asList("India", "Russia", "United States", "China"));

		assertTrue(response.getBody().contains(sampleQuestion));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void addQuestion() {
		Question body = new Question("", "Test question", "Test correct answer",
				Arrays.asList("test option 1", "test option 2"));

		HttpEntity<Question> entity = new HttpEntity<Question>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/surveys/Survey1/questions"), 
				HttpMethod.POST, 
				entity, 
				String.class
		);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

		assertTrue(actual.contains("surveys/Survey1/questions/"));
	}
}
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class TodoItemStepDefinitions {

    private RequestSpecification request;
    private Response response;

    @Given("the Todo Microservice is up and running")
    public void the_todo_microservice_is_up_and_running() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @When("I send a POST request to {string} with a valid {string} and {string}")
    public void i_send_a_post_request_with_a_valid_title_and_description(String endpoint, String title, String description) {
        request = RestAssured.given().contentType("application/json");
        request.body(String.format("{\"title\":\"%s\",\"description\":\"%s\"}", title, description));
        response = request.post(endpoint);
    }

    @Then("I expect a {int} Created response with the created todo item details")
    public void i_expect_a_created_response_with_the_created_todo_item_details(int statusCode) {
        Assert.assertEquals("Expected status code does not match!", statusCode, response.getStatusCode());
        Assert.assertNotNull("Created todo item should have details!", response.getBody().jsonPath().get("id"));
    }

    @When("I send a POST request to {string} with an invalid {string} or {string}")
    public void i_send_a_post_request_with_an_invalid_title_or_description(String endpoint, String title, String description) {
        request = RestAssured.given().contentType("application/json");
        request.body(String.format("{\"title\":\"%s\",\"description\":\"%s\"}", title, description));
        response = request.post(endpoint);
    }

    @When("I send a POST request to {string} with no {string} or {string}")
    public void i_send_a_post_request_with_no_title_or_description(String endpoint, String title, String description) {
        // Assuming that 'no title or description' means an empty JSON payload
        request = RestAssured.given().contentType("application/json");
        request.body("{}");
        response = request.post(endpoint);
    }

    @Then("I expect a {int} Bad Request response")
    public void i_expect_a_bad_request_response(int statusCode) {
        Assert.assertEquals("Expected status code does not match!", statusCode, response.getStatusCode());
    }
}

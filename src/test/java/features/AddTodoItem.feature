Feature: Todo item creation

  Scenario: Create a new todo item with valid title and description
    Given the Todo Microservice is up and running
    When I send a POST request to '/api/todoitems' with a valid 'title' and 'description'
    Then I expect a '201 Created' response with the created todo item details

  Scenario: Attempt to create a new todo item with invalid title or description
    Given the Todo Microservice is up and running
    When I send a POST request to '/api/todoitems' with an invalid 'title' or 'description'
    Then I expect a '400 Bad Request' response

  Scenario: Attempt to create a new todo item with no title or description
    Given the Todo Microservice is up and running
    When I send a POST request to '/api/todoitems' with no 'title' or 'description'
    Then I expect a '400 Bad Request' response

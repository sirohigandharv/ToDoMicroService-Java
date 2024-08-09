Feature: Create Todo Item

  As a user, I want to be able to add a new todo item so that I can keep track of tasks I need to accomplish.

  Background:
    Given the todo service is up and running

  Scenario: Successfully creating a todo item
    When I send a POST request to /api/todoitems with a valid title and description
    Then the response status code should be 201
    And the response should contain the created todo item with a unique id

  Scenario: Creating a todo item with an invalid request body
    When I send a POST request to /api/todoitems with an invalid body
    Then the response status code should be 400

  Scenario: Creating a todo item without a title
    When I send a POST request to /api/todoitems with a missing title
    Then the response status code should be 400

  Scenario: Creating a todo item without a description
    When I send a POST request to /api/todoitems with a missing description
    Then the response status code should be 400
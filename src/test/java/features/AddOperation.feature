Feature: Todo Items Creation

  Scenario Outline: Create a new todo item with valid title and description
    Given the user has a valid title and description for a todo item
    When the user makes a POST request to "/api/todoitems" with the title and description
    Then the response status code should be <Status>
    And the response should include the todo item with an ID, title, and description

    Examples:
      | Status |
      | 201    |
      | 200    |

  Scenario: Attempt to create a new todo item with invalid title or description
    Given the user has an invalid title or description for a todo item
    When the user makes a POST request to "/api/todoitems" with the invalid data
    Then the response status code should be 400

  Scenario: Check response content after creating a new todo item
    Given the user has submitted a new todo item with a valid title and description
    When the todo item is successfully created
    Then the response should contain the ID, title, and description of the created todo item
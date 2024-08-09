Feature: SwiftRe Premium Accounting and CREW Record Search

  Background:
    Given I log onto Swift Re with Company <Company>
    And I click '<Premium accounting>' button

  Scenario Outline: Verify Premium Accounting and CREW Record Search
    When I click on 'Settlement' tab
    And I search for '$(Risk name)'
    Then the grid should contain the columns
      | Entry date |
      | Risk name / Reference No |
      | SwiftRe Ref. no |
      | Accounting period |
      | Type of business |
      | Type of transaction |
      | Status |
      | Due date |
      | Debit |
      | Credit |
    And the grid should have 1 row
    When I select the record
    Then 'Selected balance', 'Selected debit' and 'Selected credit' values in the page header are populated with values from the grid record
    When I click 'Settle' button
    Then I am on 'Advice payment' page
    And the record selected in the previous steps is displayed on the page
    And 'Total Debit Amount' matches the value of the selected record
    When I click 'Advise Payment' button
    Then I am on 'Premium accounting' page
    When I open CREW application
    And the user searches for the previously selected record using the filters
      | Induction method| Received date |
      | SwiftRe         | Today         |

    Examples:
      | Company |
      | Company1 |
      | Company2 |

  Test Steps reused from Given:
  - Given I log onto Swift Re with Company <Company> [1]

  Test Steps reused from When:
  - When I click '<test data>' button [2]
  - When I search for '$(Risk name)' [5]
  - When I select the record [5]

  Test Steps reused from Then:
  - Then the grid should have 1 row [5]
  - Then I am on '<test data>' page [2]

  Test Steps reused from And:
  - And I click '<test data>' button [3]
@coinMarket
Feature: Coinmarket user journeys

  Scenario: Verify correct results are displayed on homepage
    Given I open coinmarket home page
    Then page displays 100 coins

@wip
  Scenario: Verify correct historical data is displayed
    Given I open coinmarket home page
    And from dropdown I select historical data for one of the currencies
#    And I select date range for "1 week"
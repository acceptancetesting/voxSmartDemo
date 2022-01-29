@salesJourney
Feature: Adding products to cart

  Scenario: Customer is able to add and view products in cart
    Given I open planIT jupiter home page
    And I click Shop
    And I click to buy button 2 times on "Funny Cow"
    And I click to buy button 1 times on "Fluffy Bunny"
    When I click view cart
    Then cart has following items
      | title        | quantity |
      | Funny Cow    | 2        |
      | Fluffy Bunny | 1        |
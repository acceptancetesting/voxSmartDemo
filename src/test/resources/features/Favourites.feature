@addingFavourites
Feature: User can select favourite crypto currencies

  Scenario: New user is able to sign up and add favourite cryptos
    Given I open voxSmart home page
    And I click Try Free Demo
    Then I sign up as new user
    And on landing page I open trading menu
    And I select crypto link from menu
    And select 5 cryptos as favourite
    When I open my favourites on trading page
    Then new favourites are listed in all favourites
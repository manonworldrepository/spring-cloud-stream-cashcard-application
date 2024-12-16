Feature: File Created Feature
  Checks when a user submits a request with specific data whether it will be saved into a CSV file with the specific
  directory

    Background: I have all of my system components ready
      When My source application is ready
      Then My enricher application should be ready
      And My sink application should be ready

    Scenario: User submits a valid cashcard data
      Given A user has the following cashcard
        | Owner | Amount requested for authorization |
        | Test  |                               3.14 |
      When A user submits data
      Then No error should be returned
      And Data should be saved in a CSV file

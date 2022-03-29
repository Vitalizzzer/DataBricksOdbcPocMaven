@ODBC
Feature: Verify data in database
  CRUD commands are executed and results are verified

  Background:
    Given table with name employee

  @1 @TestCaseKey=TGTB-T13
  Scenario Outline: Insert and verify data
    When insert user with first name <FirstName> and last name <LastName>
    Then correct data with <FirstName> and <LastName> appears

    Examples:
      | FirstName | LastName |
      | John      | Smith    |

########################################################################################################################
  @2 @TestCaseKey=TGTB-T12
  Scenario Outline: Update and verify data
    When insert user with first name <FirstName> and last name <LastName>
    Then correct data with <FirstName> and <LastName> appears
    When update data with <NewFirstName> and <NewLastName>
    Then correct data with <NewFirstName> and <NewLastName> appears

    Examples:
      | FirstName | LastName | NewFirstName | NewLastName |
      | John      | Smith    | Andrew       | Spark       |
#
#########################################################################################################################
#  @3
#  Scenario Outline: Delete data by name and verify data
#    When insert user with first name <FirstName> and last name <LastName>
#    Then correct data with <FirstName> and <LastName> appears
#    When delete data where first name = <FirstName> and last name = <LastName>
#    Then data is absent with <FirstName> and <LastName>
#
#    Examples:
#      | FirstName | LastName |
#      | John      | Smith    |
#
#########################################################################################################################
#  @4
#  Scenario Outline: Delete data by id and verify data
#    When insert user with first name <FirstName> and last name <LastName>
#    Then correct data with <FirstName> and <LastName> appears
#    When delete data by id
#    Then data is absent with <FirstName> and <LastName>
#
#    Examples:
#      | FirstName | LastName |
#      | John      | Smith    |
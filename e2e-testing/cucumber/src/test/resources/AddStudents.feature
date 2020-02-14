# language: en
# encoding: iso-8859-1
Feature: Agregar nuevos estudiantes
  Como usuario del aplicativo quiero agregar estudiantes nuevos

  Scenario: El usuario agrega los estudiantes
    Given que ingreso al API "http://192.168.99.100:8912" para agregar
    And se ubico en la ruta "/api/students" para agregar
    And mediante el metodo "POST" para agregar
    And tengo la siguiente lista de estudiantes para agregar
    | Name | LastName | Email | Gender |
    | Maria | Rosas | mariarosas@mitocode.edu | FEMALE |
    | Pedro | Rosas | pedrorosas@mitocode.edu | MALE |
    | Juan | Rosas | juanrosas@mitocode.edu | MALE |
    When envio su peticion para agregar
    Then se insertaron los usuarios
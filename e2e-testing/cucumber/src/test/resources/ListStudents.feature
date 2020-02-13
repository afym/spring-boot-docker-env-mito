# language: en
# encoding: iso-8859-1
Feature: Listar estudiantes registrados
  Como usuario del aplicativo quiere ver el listado de estudiantes

  Scenario: El usuario lista los estudiantes
    Given que ingreso al API "http://localhost:8912"
    And se ubico en la ruta "/api/students"
    And mediante el metodo "GET"
    When envio su peticion
    Then Obtuvo la lista de usuarios
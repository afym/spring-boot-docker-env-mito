package features;

import config.ConfigEnv;
import entity.Student;
import http.ListStudentHttp;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ListStudents {
    private ListStudentHttp listStudentHttp;

    @Before
    public void init() {
        this.listStudentHttp = new ListStudentHttp();
    }

    @Given("que ingreso al API {string}")
    public void que_ingreso_al_API(String string) {
        this.listStudentHttp.setUrl(ConfigEnv.getAPIUrl(string));
    }

    @Given("se ubico en la ruta {string}")
    public void se_ubico_en_la_ruta(String string) {
        this.listStudentHttp.setPath(string);
    }

    @Given("mediante el metodo {string}")
    public void mediante_el_metodo(String string) {
        this.listStudentHttp.setMethod(string);
    }

    @When("envio su peticion")
    public void envio_su_peticion() throws IOException {
        this.listStudentHttp.make();
    }

    @Then("obtuvo la lista de usuarios")
    public void obtuvo_la_lista_de_usuarios() throws IOException {
        List<Student> students = this.listStudentHttp.getStudents();
        assertTrue(students.size() == 3);
    }
}

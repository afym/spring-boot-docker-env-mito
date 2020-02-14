package features;

import entity.Student;
import http.AddStudentHttp;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddStudents {
    private AddStudentHttp addStudentHttp;

    @Before
    public void init() {
        this.addStudentHttp = new AddStudentHttp();
    }

    @Given("que ingreso al API {string} para agregar")
    public void que_ingreso_al_API_para_agregar(String string) {
        this.addStudentHttp.setUrl(string);
    }

    @Given("se ubico en la ruta {string} para agregar")
    public void se_ubico_en_la_ruta_para_agregar(String string) {
        this.addStudentHttp.setPath(string);
    }

    @Given("mediante el metodo {string} para agregar")
    public void mediante_el_metodo_para_agregar(String string) {
        this.addStudentHttp.setMethod(string);
    }

    @Given("tengo la siguiente lista de estudiantes para agregar")
    public void tengo_la_siguiente_lista_de_estudiantes_para_agregar(DataTable dataTable) throws IOException {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);

        for (int i = 0; i < list.size(); i++) {
            Student student = new Student();
            student.setFirstName(list.get(i).get("Name"));
            student.setLastName(list.get(i).get("LastName"));
            student.setEmail(list.get(i).get("Email"));
            student.setGender(list.get(i).get("Gender"));
            this.addStudentHttp.addStudent(student);
        }
    }

    @When("envio su peticion para agregar")
    public void envio_su_peticion_para_agregar() throws IOException {
        this.addStudentHttp.make();
    }

    @Then("se insertaron los usuarios")
    public void se_insertaron_los_usuarios() {
        List<Integer> codes = this.addStudentHttp.getResponseStatusCodes();

        for (int i = 0; i < codes.size(); i++) {
            assertTrue(codes.get(0) == 200);
        }
    }
}

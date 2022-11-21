package be.ucll.bmi.ui.steps;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.pages.AddExaminationPage;
import be.ucll.bmi.pages.PatientDetailsPage;
import be.ucll.bmi.pages.RegisterPatientPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterPatientUISteps extends UISteps {

    @Given("Martha has chosen to add Sara as a patient")
    public void martha_has_chosen_to_add_sara_as_a_patient() {
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.open();
    }
    @When("Martha provides Sara's personal details:")
    public void martha_provides_sara_s_personal_details(io.cucumber.datatable.DataTable dataTable) {
        // //parse the data table into a list of Patient objects, using the data table type defined in TypeRegistry
        List<Patient> data = dataTable.asList(Patient.class);
        ////obtain the first & only Patient object from the list
        Patient patient = data.get(0);
        context.setPatient(patient);
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.fillInSocialSecurityNumber(patient.getSocialSecurityNumber());
        registerPatientPage.fillInBirthDate(patient.getBirthDate());
        registerPatientPage.selectGender(patient.getGender());
    }
    @When("Martha provides Sara's first examination details:")
    public void martha_provides_sara_s_first_examination_details(io.cucumber.datatable.DataTable dataTable) {
        // //parse the data table into a list of Patient objects, using the data table type defined in TypeRegistry
        List<Examination> data = dataTable.asList(Examination.class);
        ////obtain the first & only Patient object from the list
        Examination examination = data.get(0);
        context.setExamination(examination);
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.fillInLength(examination.getLength());
        registerPatientPage.fillInWeight(examination.getWeight());
        registerPatientPage.fillInExaminationDate(examination.getExaminationDate());


    }
    @When("Martha tries to add Sara")
    public void martha_tries_to_add_sara() {
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.registerPatient();
    }
    @Then("Sara should be registered as a patient")
    public void sara_should_be_registered_as_a_patient() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());
        Patient patient = context.getPatient();

        assertEquals(patient.getSocialSecurityNumber(), patientDetailsPage.getSocialSecurityNumber());
        assertEquals(patient.getBirthDate(), patientDetailsPage.getBirthDate());
        assertEquals(patient.getGender(), patientDetailsPage.getGender());
    }

    @When("Martha does not correctly provide all details for the registration of Sara")
    public void martha_does_not_correctly_provide_all_details_for_the_registration_of_sara() {

        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.registerPatient();

    }
    @Then("Martha should be given an error message explaining that all details need to be provided correctly")
    public void martha_should_be_given_an_error_message_explaining_that_all_details_need_to_be_provided_correctly() {
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        assertTrue(registerPatientPage.isOpen());

        assertEquals(6, registerPatientPage.getErrors().size());
    }


}

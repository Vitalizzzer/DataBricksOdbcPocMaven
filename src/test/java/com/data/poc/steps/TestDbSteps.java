package com.data.poc.steps;

import com.data.poc.model.User;
import com.data.poc.repository.SqliteConnection;
import com.data.poc.repository.SqliteRepository;
import com.data.poc.service.DataService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.UUID;

@Slf4j
public class TestDbSteps {
    private UUID uuid;
    private String tableName;

    SqliteConnection connection = new SqliteConnection();
    DataService dataService = new DataService(new SqliteRepository(connection));
    SoftAssert softAssert = new SoftAssert();

    @Given("table with name {}")
    public void table_with_name(String tableName) {
        this.tableName = tableName;
        dataService.createDataTable(tableName);
    }

    @When("insert user with first name {} and last name {}")
    public void insert_user_with_first_name_last_name_into_table(String firstName, String lastName) {
        uuid = UUID.randomUUID();
        User user = getUser(uuid, firstName, lastName);
        dataService.insertData(tableName, user);
    }

    @Then("correct data with {} and {} appears")
    public void correct_data_appears_in_the_table(String firstName, String lastName) {
        List<User> users = dataService.selectAllUsers(tableName);

        users.forEach(
                u -> {
                    softAssert.assertEquals(firstName, u.getFirstName(), "Incorrect firstName is found");
                    softAssert.assertEquals(lastName, u.getLastName(), "Incorrect lastName is found");
                    softAssert.assertAll();
                });
    }

    @When("update data with {} and {}")
    public void update_data_in_table(String newFirstName, String newLastName) {
        User user = getUser(uuid, newFirstName, newLastName);
        dataService.updateUser(tableName, user);
    }

    @When("delete data where first name = {} and last name = {}")
    public void delete_data_from_table_where_first_name_and_last_name(String firstName, String lastName) {
        dataService.deleteDataByName(tableName, firstName, lastName);
    }

    @When("delete data by id")
    public void delete_data_from_table_by_id() {
        dataService.deleteDataById(tableName, uuid.toString());
    }

    @Then("data is absent with {} and {}")
    public void data_is_absent_in_table( String firstName, String lastName){
        User user = dataService.selectSingleUser(tableName, firstName, lastName);
        log.info("Result after deletion {}", user);
        softAssert.assertNull(user.getId());
        softAssert.assertNull(user.getFirstName());
        softAssert.assertNull(user.getLastName());
        softAssert.assertAll();

    }

    private User getUser(UUID id, String firstName, String lastName) {
        User user = new User();
        user.setId(id.toString());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}

package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Integer userId = USER_ID;
        Meal created = service.create(newMeal, userId);
        Integer newId = created.getId();
        newMeal.setId(created.getId());

        assertMatch(created, newMeal);
        assertMatch(service.get(newId, userId), newMeal);
    }

    @Test
    public void create_whenDuplicateDateTime() throws Exception {
        Meal newMeal = new Meal(meal1.getDateTime(), "another description", 100);
        assertThrows(DataAccessException.class, () -> service.create(newMeal, USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void update_WhenMealDoesNotExist() throws Exception {
        Meal updated = getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void update_WhenMealDoesNotBelongToUser() throws Exception {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, NOT_FOUND));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void delete_WhenMealDoesNotExist() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void delete_WhenMealDoesNotBelongToUser() {
        assertThrows(DataAccessException.class, () -> service.delete(MEAL_ID, NOT_FOUND));
    }

    @Test
    public void getAll() {
        assertMatch(meals, service.getAll(USER_ID));
    }

    @Test
    public void get_WhenDoesNotExist() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void get_WhenMealDoesNotBelongToUser() {
        assertThrows(DataAccessException.class, () -> service.get(MEAL_ID, NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(
                LocalDate.of(2021, 4, 1),
                LocalDate.of(2021, 4, 1),
                USER_ID);
        assertMatch(meals, Arrays.asList(meal3, meal2, meal1));
    }
}

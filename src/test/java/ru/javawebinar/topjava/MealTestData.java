package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.of;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;
    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(MEAL_ID, of(2021, 4, 1, 9, 0), "Завтрак", 90);
    public static final Meal meal2 = new Meal(MEAL_ID + 1, of(2021, 4, 1, 15, 0), "Обед", 150);
    public static final Meal meal3 = new Meal(MEAL_ID + 2, of(2021, 4, 1, 20, 0), "Ужин", 120);
    public static final Meal meal4 = new Meal(MEAL_ID + 3, of(2021, 4, 2, 9, 0), "Завтрак", 100);
    public static final Meal meal5 = new Meal(MEAL_ID + 4, of(2021, 4, 2, 15, 0), "Обед", 140);
    public static final Meal meal6 = new Meal(MEAL_ID + 5, of(2021, 4, 2, 20, 0), "Ужин", 110);

    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID, of(2021, 04, 1, 9, 0), "Завтрак", 100);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID + 1, of(2021, 04, 1, 9, 0), "Обед", 80);

    public static final List<Meal> meals = Arrays.asList(meal6, meal5, meal4, meal3, meal2, meal1);

    public static Meal getNew() {
        return new Meal(null, "description", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setCalories(330);
        updated.setDescription("UpdatedDescription");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}

package ru.praktikum.burgers.util;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum.burgers.model.User;

public class UserGenerator {

    public static User getUser() {
        String name = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@new.ru";
        String password = RandomStringUtils.randomAlphabetic(8);
        return new User(name, email, password);
    }

    public static User getIncorrectPasswordUser() {
        String name = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8) + "@new.ru";
        String password = RandomStringUtils.randomAlphabetic(5);
        return new User(name, email, password);
    }
}
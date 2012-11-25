package org.jabox.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class UserTest {

    private User _user;

    @Before
    public void before() {
        _user = new User();
    }

    @Test
    public void testLogin() {
        String login = "userLogin";
        _user.setLogin(login);
        Assert.assertEquals(login, _user.getLogin());
    }

    @Test
    public void testFirstName() {
        String firstName = "first Name Example";
        _user.setFirstName(firstName);
        Assert.assertEquals(firstName, _user.getFirstName());
    }

    @Test
    public void testLastName() {
        String lastName = "Last Name Example";
        _user.setLastName(lastName);
        Assert.assertEquals(lastName, _user.getLastName());
    }

    @Test
    public void testPassword() {
        String password = "Password Example";
        _user.setPassword(password);
        Assert.assertEquals(password, _user.getPassword());
    }

    @Test
    public void testEmail() {
        String email = "Email Example";
        _user.setEmail(email);
        Assert.assertEquals(email, _user.getEmail());
    }

    @Test
    public void testToString() {
        String login = "Login example";
        _user.setLogin(login);
        Assert.assertEquals(login, _user.toString());
    }
}

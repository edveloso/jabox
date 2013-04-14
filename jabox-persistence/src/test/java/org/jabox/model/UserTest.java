package org.jabox.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

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
    public void testPasswordHash() {
        String password = "Password Example";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        _user.setPasswordHash(hashed);
        boolean result = BCrypt.checkpw(password, _user.getPasswordHash());
        Assert.assertTrue(result);
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

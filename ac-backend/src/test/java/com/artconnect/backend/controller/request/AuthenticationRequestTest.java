import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthenticationRequestTest {

    @Test
    public void testConstructorAndGetters() {
        String email = "test@example.com";
        String password = "password123";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    public void testSetterAndGetters() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        String email = "test@example.com";
        String password = "password123";

        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);

        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    public void testNotBlankEmail() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setEmail("");

        Assertions.assertThrows(IllegalArgumentException.class, authenticationRequest::getEmail);
    }

    @Test
    public void testNotBlankPassword() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setPassword("");

        Assertions.assertThrows(IllegalArgumentException.class, authenticationRequest::getPassword);
    }

    @Test
    public void testValidEmailFormat() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        authenticationRequest.setEmail("invalid-email-format");

        Assertions.assertThrows(IllegalArgumentException.class, authenticationRequest::getEmail);
    }
}

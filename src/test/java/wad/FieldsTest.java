package wad;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.UUID;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Points("50")
public class FieldsTest extends FluentTest {

    public WebDriver webDriver = new HtmlUnitDriver(true);

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;

    private String MESSAGES_URI;
    private String LOGOUT_URI;

    @Before
    public void setUp() {
        MESSAGES_URI = "http://localhost:" + port + "/messages";
        LOGOUT_URI = "http://localhost:" + port + "/logout";
    }

    @Test
    public void pageShouldNotBeDirectlyAccessible() {
        goTo(MESSAGES_URI);
        assertFalse(hasText("Jeff Davis"));
    }

    @Test
    public void shouldSeeLoginPageOnAccessingMessages() {
        goTo(MESSAGES_URI);
        assertFalse(find(By.name("username")).isEmpty());
        assertFalse(find(By.name("password")).isEmpty());
    }

    @Test
    public void noAuthOnWrongPassword() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("Onni", "v123");
        assertFalse(hasText("Jeff Davis"));
    }

    @Test
    public void authSuccessfulAsUser() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("user", "password");
        assertTrue(hasText("Jeff Davis"));
    }

    @Test
    public void logoutSuccessful() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("user", "password");
        assertTrue(hasText("Jeff Davis"));
        find(By.name("logout")).click();
        goTo(MESSAGES_URI);
        assertFalse(hasText("Jeff Davis"));
    }

    @Test
    public void userDoesNotSeeForm() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("user", "password");
        assertFalse(hasText("Pow!"));
        boolean found = false;
        try {
            find(By.name("content")).click();
            found = true;
        } catch (NoSuchElementException t) {

        }

        assertFalse(found);
    }

    @Test
    public void postmanSeesForm() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("postman", "pat");
        assertTrue(hasText("Pow!"));
    }

    @Test
    public void postmanCanSubmitForm() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("postman", "pat");
        assertTrue(hasText("Pow!"));

        String msg = UUID.randomUUID().toString().substring(0, 8);
        fill(find(By.name("content"))).with(msg);
        find(By.name("content")).submit();

        assertTrue(pageSource(), hasText(msg));
    }

    @Test
    public void userCantHackForm() {
        goTo(MESSAGES_URI);
        enterDetailsAndSubmit("user", "password");
        String message = "trololo";

        // might be buggy :(
        createFormAndPost(message);

        assertFalse(hasText(message));
    }

    private void enterDetailsAndSubmit(String username, String password) {
        fill(find(By.name("username"))).with(username);
        fill(find(By.name("password"))).with(password);
        find(By.name("password")).submit();
    }

    private boolean hasText(String text) {
        return pageSource().toLowerCase().contains(text.toLowerCase());
    }

    private void createFormAndPost(String message) {
        String csrf = find(By.name("_csrf")).getText();

        String postForm = "<form method='post' action='http://localhost:" + port + "/messages'>"
                + "<input type='text' name='content' value='" + message + "'>"
                + "<input type='hidden' name='_csrf' value='" + csrf + "'>"
                + "<input type=submit>"
                + "</form>";
        String js = "var el = document.createElement('div');"
                + "el.innerHTML=\"" + postForm + "\"; document.body.appendChild(el);";
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript(js);

        find(By.name("content")).submit();
    }
}

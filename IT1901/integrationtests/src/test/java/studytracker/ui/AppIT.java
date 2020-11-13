package studytracker.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.print.DocFlavor.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppIT extends ApplicationTest {

  private Controller controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("fxApp_it.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setupItems() throws URISyntaxException {
    try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("it_semester.json"))) {
      String port = System.getProperty("studytracker.port");
      System.out.println(this.controller.getCourseName1().getText());
      assertNotNull(port, "No studytracker.port system property set");
      URI baseUri = new URI("http://localhost:" + port + "/studytracker/");
      System.out.println("Base RemoteTodoModelAcces URI: " + baseUri);
      this.controller.setRemoteSemesterAccess(new RemoteSemesterAccess(baseUri));
      System.out.println(this.controller.getCourseName1().getText());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }
}
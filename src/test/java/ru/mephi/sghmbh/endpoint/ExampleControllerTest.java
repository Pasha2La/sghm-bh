package ru.mephi.sghmbh.endpoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mephi.sghmbh.model.ExampleModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExampleControllerTest {
    @Autowired
    private ExampleController exampleController;

    @Test
    public void exampleGetTest() {
        final String name = "Pasha";
        String result = exampleController.exampleGet(name);
        assertNotNull(result);
        assertEquals(String.format("HELLO, %s!", name), result);
    }

    @Test
    public void examplePostTest() {
        final ExampleModel model = new ExampleModel("Pasha");
        String result = exampleController.examplePost(model);
        assertNotNull(result);
        assertEquals(String.format("Received name: %s", model.getName()), result);
    }

    @Test
    public void exampleWithServiceTest() {
        String result = exampleController.exampleWithService();
        assertNotNull(result);
        assertEquals("SUCCESS", result);
    }
}

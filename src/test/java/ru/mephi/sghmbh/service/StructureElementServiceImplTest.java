package ru.mephi.sghmbh.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mephi.sghmbh.model.StructureElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StructureElementServiceImplTest {
    @Autowired
    private StructureElementService service;

    @Test
    public void getByVirtualTableIdTest() {
        List<StructureElement> result = service.getByVirtualTableId("4e938f2a-9145-11eb-b903-97e42ad2b3c4");
        assertNotNull(result);

    }
}

package ru.mephi.sghmbh.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mephi.sghmbh.model.dto.StructureElementDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StructureElementRepositoryTest {
    @Autowired
    private StructureElementRepository repository;

    @Test
    public void getByVirtualTableIdTest() {
        List<StructureElementDto> result = repository.getByVirtualTableId("850109d0-98c2-4cd8-a283-b0d2c085bdf6");
        assertNotNull(result);
        assertEquals(5, result.size());
    }
}

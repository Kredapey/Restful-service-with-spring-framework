package aston.bootcamp.repository;

import aston.bootcamp.config.repository.RepositoryTestConfig;
import aston.bootcamp.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Optional;

@Testcontainers
@SpringJUnitConfig(classes = RepositoryTestConfig.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class TypeRepositoryTest {
    private static final String CREATE_SQL = "sql/schema.sql";

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeEach
    void beforeEach() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, CREATE_SQL);
    }

    @Test
    void save() {
        String savingTypeName = "custom";
        Type typeForSave = new Type(null, savingTypeName, new ArrayList<>());
        typeForSave = typeRepository.save(typeForSave);
        Optional<Type> insertedType = typeRepository.findById(typeForSave.getId());
        Assertions.assertTrue(insertedType.isPresent());
        Assertions.assertEquals(insertedType.get().getType(), savingTypeName);
    }

    @Test
    void deleteById() {
        int expectedSize = typeRepository.findAll().size();
        Type tempType = new Type(null, "custom", new ArrayList<>());
        tempType = typeRepository.save(tempType);
        typeRepository.deleteById(tempType.getId());
        int typeSize = typeRepository.findAll().size();
        Assertions.assertEquals(expectedSize, typeSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void findById(Long id, Boolean expectedResult) {
        Optional<Type> type = typeRepository.findById(id);
        Assertions.assertEquals(expectedResult, type.isPresent());
        type.ifPresent(ty -> Assertions.assertEquals(id, ty.getId()));
    }

    @Test
    void findAll() {
        Long expectedSize = 6L;
        int resultSize = typeRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void existsById(Long id, Boolean expectedResult) {
        Boolean result = typeRepository.existsById(id);
        Assertions.assertEquals(result, expectedResult);
    }
}

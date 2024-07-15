package aston.bootcamp.repository;

import aston.bootcamp.config.repository.RepositoryTestConfig;
import aston.bootcamp.model.Brand;
import org.junit.jupiter.api.*;
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
public class BrandRepositoryTest {
    private static final String CREATE_SQL = "sql/schema.sql";

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeEach
    void beforeEach() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, CREATE_SQL);
    }

    @Test
    void save() {
        String expectedBrandName = "Jawa";
        Brand brand = new Brand(null, expectedBrandName, new ArrayList<>());
        brand = brandRepository.save(brand);
        Optional<Brand> insertedBrand = brandRepository.findById(brand.getId());
        Assertions.assertTrue(insertedBrand.isPresent());
        Assertions.assertEquals(expectedBrandName, insertedBrand.get().getBrand());
    }

    @Test
    void deleteById() {
        int expectedSize = brandRepository.findAll().size();
        Brand tempBrand = new Brand(null, "GR", new ArrayList<>());
        tempBrand = brandRepository.save(tempBrand);
        brandRepository.deleteById(tempBrand.getId());
        int brandSize = brandRepository.findAll().size();
        Assertions.assertEquals(expectedSize, brandSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void findById(Long id, Boolean expectedResult) {
        Optional<Brand> brand = brandRepository.findById(id);
        Assertions.assertEquals(expectedResult, brand.isPresent());
        brand.ifPresent(br -> Assertions.assertEquals(id, br.getId()));
    }

    @Test
    void findAll() {
        Long expectedSize = 8L;
        int resultSize = brandRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void existsById(Long id, Boolean expectedResult) {
        Boolean result = brandRepository.existsById(id);
        Assertions.assertEquals(result, expectedResult);
    }

}

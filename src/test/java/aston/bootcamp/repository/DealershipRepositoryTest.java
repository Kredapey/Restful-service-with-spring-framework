package aston.bootcamp.repository;

import aston.bootcamp.config.repository.RepositoryTestConfig;
import aston.bootcamp.model.Dealership;
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

import java.util.Optional;

@Testcontainers
@SpringJUnitConfig(classes = RepositoryTestConfig.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class DealershipRepositoryTest {
    private static final String CREATE_SQL = "sql/schema.sql";

    @Autowired
    private DealershipRepository dealershipRepository;
    @Autowired
    private JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeEach
    void beforeEach() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, CREATE_SQL);
    }

    @Test
    void save() {
        String savingCity = "Omsk";
        String savingStreet = "Lenina";
        Long savingNum = 25L;
        Dealership dealershipForSave = new Dealership(null, savingCity, savingStreet, savingNum, null);
        dealershipForSave = dealershipRepository.save(dealershipForSave);
        Optional<Dealership> insertedDealership = dealershipRepository.findById(dealershipForSave.getId());
        Assertions.assertTrue(insertedDealership.isPresent());
        Assertions.assertEquals(insertedDealership.get().getCity(), savingCity);
        Assertions.assertEquals(insertedDealership.get().getStreet(), savingStreet);
        Assertions.assertEquals(insertedDealership.get().getHouseNum(), savingNum);
    }

    @Test
    void deleteById() {
        int expectedSize = dealershipRepository.findAll().size();
        Dealership tempDealership = new Dealership(null, "Omsk", "Lenina", 25L, null);
        tempDealership = dealershipRepository.save(tempDealership);
        dealershipRepository.deleteById(tempDealership.getId());
        int dealershipSize = dealershipRepository.findAll().size();
        Assertions.assertEquals(expectedSize, dealershipSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void findById(Long id, Boolean expectedResult) {
        Optional<Dealership> dealership = dealershipRepository.findById(id);
        Assertions.assertEquals(expectedResult, dealership.isPresent());
        dealership.ifPresent(d -> Assertions.assertEquals(id, d.getId()));
    }

    @Test
    void findAll() {
        Long expectedSize = 4L;
        int resultSize = dealershipRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void existsById(Long id, Boolean expectedResult) {
        Boolean result = dealershipRepository.existsById(id);
        Assertions.assertEquals(result, expectedResult);
    }

}

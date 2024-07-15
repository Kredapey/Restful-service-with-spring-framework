package aston.bootcamp.repository;

import aston.bootcamp.config.repository.RepositoryTestConfig;
import aston.bootcamp.model.Bike;
import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class BikeRepositoryTest {
    private static final String CREATE_SQL = "sql/schema.sql";
    private static final Logger LOGGER = LoggerFactory.getLogger(BikeRepositoryTest.class);
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private TypeRepository typeRepository;
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
        Type savingType = typeRepository.findById(1L).get();
        Brand savingBrand = brandRepository.findById(1L).get();
        String savingModel = "modelExample";
        Long savingCost = 900000L;
        Bike bikeForSave = new Bike(null, savingType, savingBrand, savingModel, savingCost, new ArrayList<>());
        bikeForSave = bikeRepository.save(bikeForSave);
        Optional<Bike> insertedBike = bikeRepository.findById(bikeForSave.getId());
        Assertions.assertTrue(insertedBike.isPresent());
        Assertions.assertEquals(insertedBike.get().getType(), savingType);
        Assertions.assertEquals(insertedBike.get().getBrand(), savingBrand);
        Assertions.assertEquals(insertedBike.get().getModel(), savingModel);
        Assertions.assertEquals(insertedBike.get().getCost(), savingCost);
    }

    @Test
    void deleteById() {
        int expectedSize = bikeRepository.findAll().size();
        Type tempType = typeRepository.findById(4L).get();
        Brand tempBrand = brandRepository.findById(5L).get();
        String tempModel = "DS 650 X";
        Long tempCost = 800000L;
        Bike tempBike = new Bike(null, tempType, tempBrand, tempModel, tempCost, new ArrayList<>());
        tempBike = bikeRepository.save(tempBike);
        bikeRepository.deleteById(tempBike.getId());
        int bikeSize = bikeRepository.findAll().size();
        Assertions.assertEquals(expectedSize, bikeSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void findById(Long id, Boolean expectedResult) {
        Optional<Bike> bike = bikeRepository.findById(id);
        Assertions.assertEquals(expectedResult, bike.isPresent());
        bike.ifPresent(b -> Assertions.assertEquals(id, b.getId()));
    }

    @Test
    void findAll() {
        Long expectedSize = 19L;
        int resultSize = bikeRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "2; true",
            "50; false"
    }, delimiter = ';')
    void existsById(Long id, Boolean expectedResult) {
        Boolean result = bikeRepository.existsById(id);
        Assertions.assertEquals(result, expectedResult);
    }

}

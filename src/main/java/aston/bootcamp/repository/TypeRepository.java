package aston.bootcamp.repository;

import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}

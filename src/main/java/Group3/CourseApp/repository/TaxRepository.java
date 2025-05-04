package Group3.CourseApp.repository;

import Group3.CourseApp.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String>, JpaSpecificationExecutor<Tax> {
    Boolean existsByName(String s);
}

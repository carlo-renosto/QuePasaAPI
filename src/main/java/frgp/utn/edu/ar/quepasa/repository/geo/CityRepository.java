package frgp.utn.edu.ar.quepasa.repository.geo;

import frgp.utn.edu.ar.quepasa.model.geo.City;
import frgp.utn.edu.ar.quepasa.model.geo.SubnationalDivision;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE (c.name LIKE %:query% OR c.subdivision.iso3 LIKE %:query%) AND c.active = :active")
    Page<City> search(@NotNull String query, @NotNull Pageable pageable, boolean active);

    @Query("SELECT c FROM City c WHERE c.active")
    Page<City> findAllActive(Pageable pageable);

    @Query("SELECT c FROM City c WHERE c.id = :id AND c.active")
    Optional<City> findByIdActive(long id);

    @Query(value = "SELECT c.id, c.name, c.subdivision, c.active FROM cities c INNER JOIN countries country ON country.iso3 = :iso3 WHERE c.active = true", countQuery = "SELECT COUNT(*) FROM cities c INNER JOIN countries country ON country.iso3 = :iso3 WHERE c.active = true", nativeQuery = true)
    Page<City> findByCountry(String iso3, Pageable pageable);

    Page<City> findBySubdivision(SubnationalDivision subdivision, Pageable pageable);
}

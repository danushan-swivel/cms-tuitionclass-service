package cms.tuitionclass.service.repository;

import cms.tuitionclass.service.domain.entity.TuitionClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TuitionClassRepository extends JpaRepository<TuitionClass, String> {
    @Query(value = "SELECT * FROM tuition_class d WHERE d.tuition_class_id = ?1 AND d.is_deleted=false", nativeQuery = true)
    Optional<TuitionClass> findByTuitionClassId(String tuitionClassId);

    @Query(value = "SELECT * FROM tuition_class d WHERE d.is_deleted=false", nativeQuery = true)
    Page<TuitionClass> findAll(Pageable pageable);

    boolean existsByLocationNameAndIsDeletedFalse(String locationName);

    boolean existsByLocationNameAndTuitionClassIdNotAndIsDeletedFalse(String locationName, String tuitionClassId);
}

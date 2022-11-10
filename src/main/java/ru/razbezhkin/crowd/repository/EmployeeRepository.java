package ru.razbezhkin.crowd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.razbezhkin.crowd.domain.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("from Employee e where concat(e.lastname, ' ', e.firstname) like concat('%',:name,'%') ")
    List<Employee> findByName(@Param("name") String name);

    Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber);

    Optional<Employee> findEmployeeByLogin(String login);
}

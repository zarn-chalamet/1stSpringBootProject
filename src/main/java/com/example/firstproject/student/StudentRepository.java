package com.example.firstproject.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {

    @Query(value = "Select * from student where email=?1",nativeQuery = true)
    public Student checkByEmail(String email);
    @Query(value = "Select * from student where email=?1 and password=?2",nativeQuery = true)
    public Student checkLogin(String email, String password);
}

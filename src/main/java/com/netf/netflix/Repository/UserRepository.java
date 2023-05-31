package com.netf.netflix.Repository;

import com.netf.netflix.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

//    @Query("SELECT u.name FROM Member m JOIN m.users u WHERE u.email = :email")
//    List<String> findMatchingUserNames(@Param("email") String email);

    @Query("SELECT u.name FROM User u WHERE u.email = :email")
    List<String> findMatchingNames(@Param("email") String email);

    User findById(long id);

}

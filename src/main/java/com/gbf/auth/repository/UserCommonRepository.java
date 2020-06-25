package com.gbf.auth.repository;

import com.gbf.auth.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@NoRepositoryBean
public interface UserCommonRepository<T extends User, Long> extends CrudRepository<T, Long> {

    @Query("select u from #{#entityName} u where u.mail=:mail")
    Optional<User> findByMail(@Param("mail")String mail);

    Optional<User> findByLogin(String login);

}

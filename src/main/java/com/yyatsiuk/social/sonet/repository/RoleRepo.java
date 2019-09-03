package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByName(String name) throws DataAccessException;

}

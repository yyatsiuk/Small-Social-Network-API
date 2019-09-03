package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Group;
import com.yyatsiuk.social.sonet.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {

    List<Group> findByCreator (User user);

    List<Group> findByName(String name);

    @Query("SELECT g FROM Group g WHERE g.status = 'ACTIVE' and g.name like %:name%")
    Page<Group> findByNameAndStatus(String name, Pageable pageable);

   @Query("SELECT g FROM Group g WHERE g.members IN :userId")
    Page<Group> findAllCurrentUserGroups(Long userId, Pageable pageable);

    @Query("SELECT g FROM Group g WHERE g.status = 'ACTIVE' order by g.creationTime desc")
    Page<Group> findAllByStatus(Pageable pageable);
}

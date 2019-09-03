package com.yyatsiuk.social.sonet.repository;


import com.yyatsiuk.social.sonet.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {

}

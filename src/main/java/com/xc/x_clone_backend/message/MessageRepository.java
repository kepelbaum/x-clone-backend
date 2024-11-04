package com.xc.x_clone_backend.message;

import com.xc.x_clone_backend.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}

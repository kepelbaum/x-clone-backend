package com.xc.x_clone_backend.message;

import com.xc.x_clone_backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getMessages() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return messageService.getMessagesByUsername(username);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message Message) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Message.setSender(username);
        Message.setDate(new Date());
        Message createdMessage = messageService.addMessage(Message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    //    @PutMapping
//    public ResponseEntity<Message> updateMessage(@RequestBody Message Message) {
//        Message resultMessage = MessageService.updateMessage(Message);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteMessage(@PathVariable Integer id) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        String Messagename = MessageService.getMessageById(id).getUsername();
//        if (username.equals(Messagename)) {
//            MessageService.deleteMessage(id);
//            List<Message> replies = MessageService.getRepliesById(id);
//            for (Message reply : replies) {
//                MessageService.deleteMessage(reply.getMessage_id());
//            }
//            return new ResponseEntity<>("Message deleted", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
//        }
//    }
}

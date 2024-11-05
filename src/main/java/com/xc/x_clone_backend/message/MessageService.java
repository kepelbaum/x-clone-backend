package com.xc.x_clone_backend.message;

import com.xc.x_clone_backend.user.User;
import com.xc.x_clone_backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public MessageService( MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

//    public List<Message> getMessages() {
//        return MessageRepository.findAll().stream;
//    }

    public List<Message> getMessagesByUsername(String username) {
//        User user = userService.getUserById(username);
        return messageRepository.findAll().stream()
                .filter(Message -> username.equals(Message.getSender()) || username.equals(Message.getRecipient()))
                .collect(Collectors.toList());
    }

//    public Message getMessageById(Integer id) {
//        return MessageRepository.findAll().stream()
//                .filter(Message -> id.equals(Message.getMessage_id()))
//                .findFirst().orElse(null);
//    }

//    public List<Message> getRepliesByTwoUsers(User user, User other) {
//        return messageRepository.findAll().stream()
//                .filter(Message -> user.equals(Message.getSender()) || user.equals(Message.getRecipient()))
//                .filter(Message -> other.equals(Message.getSender()) || other.equals(Message.getRecipient()))
//                .collect(Collectors.toList());
//    }

    public Message addMessage(Message Message) {
        messageRepository.save(Message);
        return Message;
    }

    //    public Message updateMessage(Message updatedMessage) {
//        Optional<Message> existingMessage = MessageRepository.findByMessageId(updatedMessage.getMessage_id());
//
//        if (existingMessage.isPresent()) {
//            Message MessageToUpdate = existingMessage.get();
//            MessageToUpdate.setUsername(updatedMessage.getUsername());
//
//        }
//    }
//    public void deleteMessage(Integer id) {
//        messageRepository.deleteById(id);
//    }
}

package com.xc.x_clone_backend.message;

import com.xc.x_clone_backend.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/message")
public class MessageController {
    private final MessageService messageService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public MessageController(MessageService messageService, CloudinaryService cloudinaryService) {
        this.messageService = messageService;
        this.cloudinaryService = cloudinaryService;
    }


    @GetMapping
    public List<Message> getMessages() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return messageService.getMessagesByUsername(username);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createMessage(
            @RequestParam("recipient") String recipient,
            @RequestParam("content") String content,
            @RequestPart(value = "media", required = false) MultipartFile media) {

        Message message = new Message();
        message.setRecipient(recipient);
        message.setContent(content);

        if (media != null && !media.isEmpty()) {
            if (media.getSize() > 30 * 1024 * 1024) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceeds limit");
            }

            String contentType = media.getContentType();
            if (!isValidMediaType(contentType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid media type");
            }

            String mediaUrl = cloudinaryService.uploadFile(media, "messages");
            message.setMedia_url(mediaUrl);
            message.setMedia_type(determineMediaType(contentType));
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        message.setSender(username);
        message.setDate(new Date());

        Message createdMessage = messageService.addMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    private boolean isValidMediaType(String contentType) {
        if (contentType == null) return false;
        return contentType.startsWith("image/") || contentType.startsWith("video/");
    }

    private String determineMediaType(String contentType) {
        if (contentType.startsWith("image/")) return "image";
        if (contentType.startsWith("video/")) return "video";
        return null;
    }
}

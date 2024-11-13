import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;              
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;                                
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        String message = ex.getMessage();
        
        if (message.contains("Password")) {
            errors.put("password", message);
        } else if (message.contains("Username")) {
            errors.put("username", message);
        } else if (message.contains("Display name")) {
            errors.put("displayname", message);
        } else {
            errors.put("error", message);
        }
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
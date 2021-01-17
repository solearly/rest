package rest.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rest.helpers.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@ControllerAdvice
public class InternalExceptionHandler {

    @Autowired
    Log log;

    class ExceptionMessage {
        private String message;
        public ExceptionMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        String path = request.getMethod() + " " + request.getRequestURL();
        log.writeLog("Got exception: ", path, false);
        log.writeLog("Exception message: ", ex.getLocalizedMessage(), false);
        String result = "";
        log.writeLog("",
                Arrays.stream(ex.getStackTrace())
                        .map(o -> o.toString())
                        .reduce(result, (o, i) -> o + "\n" + i),
                false);
        ExceptionMessage message = new ExceptionMessage(ex.getLocalizedMessage());
        return new ResponseEntity<Object>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

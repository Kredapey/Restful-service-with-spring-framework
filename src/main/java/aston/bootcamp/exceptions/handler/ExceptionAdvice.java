package aston.bootcamp.exceptions.handler;

import aston.bootcamp.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Response> handleNotFoundException(NotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Response> handleSqlException(SQLException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundException(EntityNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

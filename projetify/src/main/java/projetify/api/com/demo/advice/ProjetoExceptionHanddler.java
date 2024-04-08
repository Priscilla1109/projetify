package projetify.api.com.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projetify.api.com.demo.exception.ExistentProjectException;
import projetify.api.com.demo.exception.InvalidDataException;

import java.util.NoSuchElementException;

//Classe responsável por lidar com exceções lançadas pela Controller
@ControllerAdvice
@RestControllerAdvice
public class ProjetoExceptionHanddler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> noSuchElementExeception(NoSuchElementException e){ //uso do Object por ser mais genérico
        return new ResponseEntity<>("Id não foi encontrado na busca: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistentProjectException.class)
    public ResponseEntity<Object> existentProjectException(ExistentProjectException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> invalidDataException(InvalidDataException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

package projetify.api.com.demo.advice;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import projetify.api.com.demo.advice.ProjetoExceptionHanddler;
import projetify.api.com.demo.exception.ExistentProjectException;
import projetify.api.com.demo.exception.InvalidDataException;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjetoExceptionHanddlerTest {
    @Mock
    ProjetoService projetoService;
    @Mock
    ProjetoRequest projetoRequest;

    @InjectMocks
    ProjetoExceptionHanddler projetoExceptionHanddler;

    @Test
    public void testNoSuchElementException(){
        //mock para lançar a exceção
        when(projetoService.buscarProjetoId(1L)).thenThrow(NoSuchElementException.class);

        //chamada do método que vai ser testado
        ResponseEntity<Object> responseEntity = projetoExceptionHanddler.noSuchElementExeception(new NoSuchElementException());

        //verifica se os retornos estão corretos
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Id não foi encontrado na busca: null", responseEntity.getBody());
    }

    @Test
    public void testExistentProjectException(){
        when(projetoService.criarProjeto(projetoRequest)).thenThrow(ExistentProjectException.class);

        ResponseEntity<Object> responseEntity = projetoExceptionHanddler.existentProjectException(new ExistentProjectException("O projeto com esse ID já existe!"));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("O projeto com esse ID já existe!", responseEntity.getBody());
    }

    @Test
    public void testInvalidDataException(){
        when(projetoService.criarProjeto(projetoRequest)).thenThrow(InvalidDataException.class);

        ResponseEntity<Object> responseEntity = projetoExceptionHanddler.invalidDataException(new InvalidDataException("A data de início não pode ser maior do que a data fim!"));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("A data de início não pode ser maior do que a data fim!", responseEntity.getBody());
    }
}

package projetify.api.com.demo.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import projetify.api.com.demo.controller.ProjetoController;
import projetify.api.com.demo.exception.ExistentProjectException;
import projetify.api.com.demo.exception.InvalidDataException;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ProjetoController.class)
public class ProjetoExceptionsTest {
    @Mock
    //cria uma instancia simulada da classe
    private ProjetoService projetoService;

    @InjectMocks
    //injeta as dependências da classe durante a execução do teste
    private ProjetoController projetoController;
    @Test
    public void testNoSuchElementException(){
        try{
            projetoService.buscarProjetoId(1L);
        } catch (NoSuchElementException e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    }

    @Test
    public void testExistentElementException(){
        try {
            ProjetoRequest projetoRequest = new ProjetoRequest();
            projetoRequest.setId(1L);
            projetoRequest.setNome("Projeto Teste");
            projetoRequest.setDescricao("testando projeto1");
            projetoRequest.setDataInicio("2024-03-31");
            projetoRequest.setDataFim("2024-04-04");

            projetoService.criarProjeto(projetoRequest);

            ProjetoRequest projetoRequestRepitido = new ProjetoRequest();
            projetoRequest.setId(1L);
            projetoRequest.setNome("Projeto Teste");
            projetoRequest.setDescricao("testando projeto1");
            projetoRequest.setDataInicio("2024-03-31");
            projetoRequest.setDataFim("2024-04-04");

            projetoService.criarProjeto(projetoRequestRepitido);
        } catch (ExistentProjectException e){
            assertEquals(ExistentProjectException.class, e.getClass());
        }
    }

    @Test
    public void testInvalidDataException(){
        InvalidDataException exception = new InvalidDataException("Data inválida!");

        assertEquals("Data inválida!", exception.getMessage());
    }
}

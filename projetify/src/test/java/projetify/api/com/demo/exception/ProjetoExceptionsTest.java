package projetify.api.com.demo.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import projetify.api.com.demo.controller.ProjetoController;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;
import projetify.api.com.demo.util.ProjetoRequestFixture;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            ProjetoRequest projetoRequest = ProjetoRequestFixture.get().withRandomData().build();
            projetoRequest.setId(1L);
            projetoRequest.setNome("Projeto Teste");
            projetoRequest.setDescricao("testando projeto1");

            projetoService.criarProjeto(projetoRequest);

            ProjetoRequest projetoRequestRepitido = ProjetoRequestFixture.get().withRandomData().build();
            projetoRequest.setId(1L);
            projetoRequest.setNome("Projeto Teste");
            projetoRequest.setDescricao("testando projeto1");

            projetoService.criarProjeto(projetoRequestRepitido);
        } catch (ExistentProjectException e){
            assertEquals(ExistentProjectException.class, e.getClass());
        }
    }

    @Test
    public void testInvalidDataException() {
        try {
            ProjetoRequest projetoRequest = ProjetoRequestFixture.get().withRandomData_ErrorData().build();
            projetoService.criarProjeto(projetoRequest);
        } catch (InvalidDataException e) {
            assertEquals(InvalidDataException.class, e.getClass());
        }
    }
}

package controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import projetify.api.com.demo.controller.ControllerProjeto;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ControllerProjeto.class)
public class ProjetoControllerTest {
    @Mock
    //cria uma instancia simulada da classe
    private ProjetoService projetoService;

    @InjectMocks
    //injeta as dependências da classe durante a execução do teste
    private ControllerProjeto controllerProjeto;

    @Test
    public void testCriarProjeto(){
        //Objeto de projeto ficticio para criação
        ProjetoRequest projetoRequest = new ProjetoRequest();

        //Objeto ficticio retornado pelo serviço
        Projeto projetoCriado = new Projeto();

        //Simula a criação do projeto
        when(projetoService.criarProjeto(any(ProjetoRequest.class))).thenReturn(projetoCriado);

        ResponseEntity<String> resposta = controllerProjeto.criarProjeto(projetoRequest);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("Projeto criado com sucesso!", resposta.getBody());
    }

    @Test
    public void atualizarProjeto(){
    ProjetoRequest projetoAntigo = new ProjetoRequest();
    projetoAntigo.setNome("Projeto Antigo");
    projetoAntigo.setDescricao("Descrição antiga");
    projetoAntigo.setDataInicio("2024-04-02");
    projetoAntigo.setDataFim("2024-04-10");
    Projeto projetoDomain = MapperProjeto.toDomain(projetoAntigo);
    projetoDomain.setId(1L);

    ResponseEntity<String> resposta = controllerProjeto.atualizarProjeto(1L, projetoDomain);

    verify(projetoService, times(1)).atualizarProjeto(1L, projetoDomain);
    assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    assertEquals("Projeto atualizado com sucesso!", resposta.getBody());
    }
}

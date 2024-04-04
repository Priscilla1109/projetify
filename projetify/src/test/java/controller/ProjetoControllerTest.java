package controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import projetify.api.com.demo.controller.ProjetoController;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoPageResponse;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {
    @Mock
    //cria uma instancia simulada da classe
    private ProjetoService projetoService;

    @InjectMocks
    //injeta as dependências da classe durante a execução do teste
    private ProjetoController projetoController;

    @Test
    public void testCriarProjeto(){
        //Objeto de projeto ficticio para criação
        ProjetoRequest projetoRequest = new ProjetoRequest();

        //Objeto ficticio retornado pelo serviço
        Projeto projetoCriado = new Projeto();

        //Simula a criação do projeto
        when(projetoService.criarProjeto(any(ProjetoRequest.class))).thenReturn(projetoCriado);

        ResponseEntity<String> resposta = projetoController.criarProjeto(projetoRequest);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("Projeto criado com sucesso!", resposta.getBody());
    }

    @Test
    public void testListarProjetos(){
        //simulação da criação da lista de projetos
        List<Projeto> projetos = new ArrayList<>();
        projetos.add(new Projeto());
        projetos.add(new Projeto());
        Page<Projeto> projetoPage = new PageImpl<>(projetos);

        //Simula comportamento do método
        when(projetoService.listarProjetosPaginados(0,10)).thenReturn(projetoPage);

        //Chama o método da controller
        ResponseEntity<ProjetoPageResponse> responseEntity = projetoController.listarProjetos(0,10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projetos.size(), responseEntity.getBody().getProjetos().size());
    }

    @Test
    public void atualizarProjeto(){
    ProjetoRequest projetoAntigo = new ProjetoRequest();
    projetoAntigo.setNome("Projeto Antigo");
    projetoAntigo.setDescricao("Descrição antiga");
    projetoAntigo.setDataInicio("2024-04-02");
    projetoAntigo.setDataFim("2024-04-10");
    Projeto projetoDomain = ProjetoMapper.toDomain(projetoAntigo);
    projetoDomain.setId(1L);

    ResponseEntity<String> resposta = projetoController.atualizarProjeto(1L, projetoDomain);

    verify(projetoService, times(1)).atualizarProjeto(1L, projetoDomain);
    assertEquals(HttpStatus.OK, resposta.getStatusCode());
    assertEquals("Projeto atualizado com sucesso!", resposta.getBody());
    }

    @Test
    public void testaBuscarPorId(){
        ProjetoRequest projetoRequest = new ProjetoRequest();
        projetoRequest.setNome("Projeto Teste");
        projetoRequest.setDescricao("testando projeto1");
        projetoRequest.setDataInicio("2024-03-31");
        projetoRequest.setDataFim("2024-04-04");
        Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest);

        //Comportamento esperado do mock
        when(projetoService.buscarProjetoId(1L)).thenReturn(projetoDomain);

        //Chama o método de busca
        ResponseEntity<String> resposta = projetoController.buscarProjetoId(1L);

        //Verifica se o resultado retornado é igual ao esperado
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Projeto encontrado!", resposta.getBody());
    }

    /*@Test(expected = NoSuchElementException.class)
    public void testBuscaPorIdInexistetente (){
        //Criando id inexistente
        Long idInexistente = 0L;

        //Comportamento esperado do mock
        when(projetoService.buscarProjetoId(idInexistente)).thenReturn(Optional.empty());

        //Chama o método de busca
        ResponseEntity<String> resultadoBusca = projetoController.buscarProjetoId(idInexistente);

        //Verifica se o resultado retornado é igual ao esperado
        assertEquals(HttpStatus.NOT_FOUND, resultadoBusca.getStatusCode());
        assertNull(resultadoBusca.getBody());
    }*/

    @Test
    public void testDeletarProjetoExistente() {
        ProjetoRequest projetoRequest = new ProjetoRequest();
        projetoRequest.setNome("Projeto Teste");
        projetoRequest.setDescricao("testando projeto1");
        projetoRequest.setDataInicio("2024-03-31");
        projetoRequest.setDataFim("2024-04-04");
        Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest);
        projetoDomain.setId(1L);

        //Comportamento esperado do mock
        when(projetoService.deletarProjeto(1L)).thenReturn(true);

        //Ação do método
        ResponseEntity<String> resultado = projetoController.deteletarProjeto(1l);

        //Verifica se o resultado retornado é igual ao esperado
        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        assertEquals("Projeto deletado com sucesso!", resultado.getBody());
    }
}

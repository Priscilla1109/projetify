package controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import projetify.api.com.demo.controller.ProjetoController;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    //cria uma instancia simulada da classe
    private ProjetoService projetoService;

    @InjectMocks
    //injeta as dependências da classe durante a execução do teste
    private ProjetoController projetoController;
//
//    @Test
//    public void testCriarProjeto() {
//        //Objeto de projeto ficticio para criação
//        ProjetoRequest projetoRequest = new ProjetoRequest();
//
//        //Objeto ficticio retornado pelo serviço
//        Projeto projetoCriado = new Projeto();
//
//        //Simula a criação do projeto
//        when(projetoService.criarProjeto(any(ProjetoRequest.class))).thenReturn(projetoCriado);
//
//        ResponseEntity<ProjetoResponse> resposta = projetoController.criarProjeto(projetoRequest);
//
//        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
//        //assertEquals("Projeto criado com sucesso!", resposta.getBody());
//    }

//    @Test
//    public void testListarProjetos() {
//        //simulação da criação da lista de projetos
//        List<Projeto> projetos = new ArrayList<>();
//        projetos.add(new Projeto());
//        projetos.add(new Projeto());
//        Page<Projeto> projetoPage = new PageImpl<>(projetos);
//
//        //Simula comportamento do método
//        when(projetoService.listarProjetosPaginados(0, 10)).thenReturn(projetoPage);
//
//        //Chama o método da controller
//        ResponseEntity<ProjetoPageResponse> responseEntity = projetoController.listarProjetos(0, 10);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(projetos.size(), responseEntity.getBody().getProjetos().size());
//    }

    @Test
    public void atualizarProjeto() throws Exception {
        ProjetoRequest projetoRequest = new ProjetoRequest();
        projetoRequest.setId(1L);
        projetoRequest.setNome("Nome atualizado");
        projetoRequest.setDescricao("Descrição atualizada");
        projetoRequest.setDataInicio("2024-04-08");
        projetoRequest.setDataFim("2024-04-12");
        Projeto projetoDomainAtualizado = ProjetoMapper.toDomain(projetoRequest);

        when(projetoService.atualizarProjeto(eq(1L), any(Projeto.class))).thenReturn(projetoDomainAtualizado);

        //Requisição PUT com os parametros atualizados
        mockMvc.perform(put("/projetos/{id}", 1)
                        .param("nome", "Nome atualizado")
                        .param("descrição", "Descriição atualizada"))
                //resposta
                .andExpect(status().isOk())
                //corpo da resposta
                .andExpect((ResultMatcher) jsonPath("$.id").value(1))
                .andExpect((ResultMatcher) jsonPath("$.nome").value("Nome atualizado"))
                .andExpect((ResultMatcher) jsonPath("$.descrição").value("Descrição atualizada"));
    }

//    @Test
//    public void testaBuscarPorId() {
//        ProjetoRequest projetoRequest = new ProjetoRequest();
//        projetoRequest.setNome("Projeto Teste");
//        projetoRequest.setDescricao("testando projeto1");
//        projetoRequest.setDataInicio("2024-03-31");
//        projetoRequest.setDataFim("2024-04-04");
//        Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest);
//
//        //Comportamento esperado do mock
//        when(projetoService.buscarProjetoId(1L)).thenReturn(projetoDomain);
//
//        //Chama o método de busca
//        ResponseEntity<ProjetoResponse> resposta = projetoController.buscarProjetoId(1L);
//
//        //Verifica se o resultado retornado é igual ao esperado
//        assertEquals(HttpStatus.OK, resposta.getStatusCode());
//        //assertEquals(projetoDomain.toString(), resposta.getBody());
//    }

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

//    @Test
//    public void testDeletarProjetoExistente() {
//        ProjetoRequest projetoRequest = new ProjetoRequest();
//        projetoRequest.setNome("Projeto Teste");
//        projetoRequest.setDescricao("testando projeto1");
//        projetoRequest.setDataInicio("2024-03-31");
//        projetoRequest.setDataFim("2024-04-04");
//        Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest);
//        projetoDomain.setId(1L);
//
//        //Comportamento esperado do mock
//        when(projetoService.deletarProjeto(1L)).thenReturn(true);
//
//        //Ação do método
//        ResponseEntity<String> resultado = projetoController.deteletarProjeto(1l);
//
//        //Verifica se o resultado retornado é igual ao esperado
//        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
//        assertEquals("Projeto deletado com sucesso!", resultado.getBody());
//    }
}

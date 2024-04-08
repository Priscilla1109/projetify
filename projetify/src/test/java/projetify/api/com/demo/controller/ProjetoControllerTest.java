package projetify.api.com.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    //cria uma instancia simulada da classe
    private ProjetoService projetoService;

    @Test
    public void testCriarProjeto() throws Exception {
        ProjetoRequest projetoRequest = new ProjetoRequest();
        projetoRequest.setId(1L);
        projetoRequest.setNome("Nome Criado");
        projetoRequest.setDescricao("Descrição Criada");
        projetoRequest.setDataInicio("2024-04-08");
        projetoRequest.setDataFim("2024-04-12");
        Projeto projetoDomainCriado = ProjetoMapper.toDomain(projetoRequest);

        when(projetoService.criarProjeto(any(ProjetoRequest.class))).thenReturn(projetoDomainCriado);

        //Requisição POST
        mockMvc.perform(post("/APIs/projetify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projetoRequest)))
                //resposta da requisição
                .andExpect(status().isCreated())
                //corpo
                .andExpect(jsonPath("$.id").value(projetoRequest.getId()))
                .andExpect(jsonPath("$.nome").value(projetoRequest.getNome()))
                .andExpect(jsonPath("$.descricao").value(projetoRequest.getDescricao()))
                .andExpect(jsonPath("$.dataInicio").value(projetoRequest.getDataInicio()))
                .andExpect(jsonPath("$.dataFim").value(projetoRequest.getDataFim()));
    }

    @Test
    public void testListarProjetos() throws Exception {
        ProjetoRequest projetoRequest1 = new ProjetoRequest();
        projetoRequest1.setId(1L);
        projetoRequest1.setNome("Nome1");
        projetoRequest1.setDescricao("Descrição1");
        projetoRequest1.setDataInicio("2024-04-08");
        projetoRequest1.setDataFim("2024-04-12");
        Projeto projeto1 = ProjetoMapper.toDomain(projetoRequest1);

        ProjetoRequest projetoRequest2 = new ProjetoRequest();
        projetoRequest2.setId(2L);
        projetoRequest2.setNome("Nome2");
        projetoRequest2.setDescricao("Descrição2");
        projetoRequest2.setDataInicio("2024-04-08");
        projetoRequest2.setDataFim("2024-04-12");
        Projeto projeto2 = ProjetoMapper.toDomain(projetoRequest2);

        //simulação da criação da lista de projetos paginada
        List<Projeto> projetos = Arrays.asList(projeto1, projeto2);
        Page<Projeto> projetoPage = new PageImpl<>(projetos);

        //Simula comportamento do método
        when(projetoService.listarProjetosPaginados(0, 10)).thenReturn(projetoPage);

        //Requisição GET
        mockMvc.perform(get("/APIs/projetify/projetos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projetos.length()").value(projetoPage.getTotalElements()))
                //corpo projeto1
                .andExpect(jsonPath("$.projetos[0].id").value(projeto1.getId()))
                .andExpect(jsonPath("$.projetos[0].nome").value(projeto1.getNome()))
                .andExpect(jsonPath("$.projetos[0].descricao").value(projeto1.getDescricao()))
                .andExpect(jsonPath("$.projetos[0].dataInicio").value(projetoRequest1.getDataInicio()))
                .andExpect(jsonPath("$.projetos[0].dataFim").value(projetoRequest1.getDataFim()))
                //corpo projeto2
                .andExpect(jsonPath("$.projetos[1].id").value(projeto2.getId()))
                .andExpect(jsonPath("$.projetos[1].nome").value(projeto2.getNome()))
                .andExpect(jsonPath("$.projetos[1].descricao").value(projeto2.getDescricao()))
                .andExpect(jsonPath("$.projetos[1].dataInicio").value(projetoRequest2.getDataInicio()))
                .andExpect(jsonPath("$.projetos[1].dataFim").value(projetoRequest2.getDataFim()));

        verify(projetoService, times(1)).listarProjetosPaginados(anyInt(),anyInt());
    }

    @Test
    public void testBuscarProjeto() throws Exception {
        ProjetoRequest projetoRequest1 = new ProjetoRequest();
        projetoRequest1.setId(1L);
        projetoRequest1.setNome("Nome1");
        projetoRequest1.setDescricao("Descrição1");
        projetoRequest1.setDataInicio("2024-04-08");
        projetoRequest1.setDataFim("2024-04-12");
        Projeto projeto1 = ProjetoMapper.toDomain(projetoRequest1);

        //Comportamento esperado do mock
        when(projetoService.buscarProjetoId(1L)).thenReturn(projeto1);

        //Requisição GET
        mockMvc.perform(get("/APIs/projetify/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projetoRequest1)))
                //resposta da requisição
                .andExpect(status().isOk())
                //corpo
                .andExpect(jsonPath("$.id").value(projetoRequest1.getId()))
                .andExpect(jsonPath("$.nome").value(projetoRequest1.getNome()))
                .andExpect(jsonPath("$.descricao").value(projetoRequest1.getDescricao()))
                .andExpect(jsonPath("$.dataInicio").value(projetoRequest1.getDataInicio()))
                .andExpect(jsonPath("$.dataFim").value(projetoRequest1.getDataFim()));
    }

    @Test
    public void testAtualizarProjeto() throws Exception {
        ProjetoRequest projetoRequest = new ProjetoRequest();
        projetoRequest.setId(1L);
        projetoRequest.setNome("Nome atualizado");
        projetoRequest.setDescricao("Descrição atualizada");
        projetoRequest.setDataInicio("2024-04-08");
        projetoRequest.setDataFim("2024-04-12");
        Projeto projetoDomainAtualizado = ProjetoMapper.toDomain(projetoRequest);

        when(projetoService.atualizarProjeto(eq(1L), any(Projeto.class))).thenReturn(projetoDomainAtualizado);

        //Requisição PUT com os parametros atualizados
        mockMvc.perform(put("/APIs/projetify/1" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projetoRequest)))
                //status da resposta
                .andExpect(status().isOk())
                //corpo da resposta
                .andExpect(jsonPath("$.id").value(projetoRequest.getId()))
                .andExpect(jsonPath("$.nome").value(projetoRequest.getNome()))
                .andExpect(jsonPath("$.descricao").value(projetoRequest.getDescricao()));
    }

}
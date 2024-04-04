package model;

import org.junit.Test;
import projetify.api.com.demo.model.ProjetoRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjetoRequestTest {
    @Test
    public void testGettersSetters(){
        ProjetoRequest projetoRequest = new ProjetoRequest();

        Long id = 1L;
        String nome = "Projeto";
        String descricao = "Descrição";
        String dataInicio = "2024-04-04";
        String dataFim = "2024-04-10";

        projetoRequest.setId(id);
        projetoRequest.setNome(nome);
        projetoRequest.setDescricao(descricao);
        projetoRequest.setDataInicio(dataInicio);
        projetoRequest.setDataFim(dataFim);

        assertEquals(id, projetoRequest.getId());
        assertEquals(nome, projetoRequest.getNome());
        assertEquals(descricao, projetoRequest.getDescricao());
        assertEquals(dataInicio, projetoRequest.getDataInicio());
        assertEquals(dataFim, projetoRequest.getDataFim());
    }
}

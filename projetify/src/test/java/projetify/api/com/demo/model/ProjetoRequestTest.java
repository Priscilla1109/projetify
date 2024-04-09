package projetify.api.com.demo.model;

import org.junit.Test;
import projetify.api.com.demo.util.ProjetoRequestFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjetoRequestTest {
    @Test
    public void testGettersSetters(){
        ProjetoRequest projetoRequest = ProjetoRequestFixture.get().withRandomData_ErrorData().build();

        Long id = 1L;
        String nome = "Projeto";
        String descricao = "Descrição";

        projetoRequest.setId(id);
        projetoRequest.setNome(nome);
        projetoRequest.setDescricao(descricao);

        assertEquals(id, projetoRequest.getId());
        assertEquals(nome, projetoRequest.getNome());
        assertEquals(descricao, projetoRequest.getDescricao());
    }
}

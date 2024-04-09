package projetify.api.com.demo.mapper;

import org.junit.Test;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.model.ProjetoResponse;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;


public class ProjetoMapperTest {
    @Test
    public void testToDomain(){
        ProjetoRequest projetoRequest = new ProjetoRequest();
        projetoRequest.setId(1L);
        projetoRequest.setNome("Nome");
        projetoRequest.setDescricao("Descrição");
        projetoRequest.setDataInicio("2024-04-04");
        projetoRequest.setDataFim("2024-04-10");

        Projeto projeto = ProjetoMapper.toDomain(projetoRequest);

        //verifica se os atrinutos foram mapeados corretamente
        assertEquals(projetoRequest.getId(), projeto.getId());
        assertEquals(projetoRequest.getNome(), projeto.getNome());
        assertEquals(projetoRequest.getDescricao(), projeto.getDescricao());
        //assertEquals(projetoRequest.getDataInicio(), projeto.getDataInicio());
        //assertEquals(projetoRequest.getDataFim(), projetoRequest.getDataFim());
    }

    @Test
    public void testToResponse(){
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Nome");
        projeto.setDescricao("Descrição");
        projeto.setDataInicio(LocalDate.parse("2024-04-04"));
        projeto.setDataFim(LocalDate.parse("2024-04-10"));

        ProjetoResponse projetoResponse = ProjetoMapper.toResponse(projeto);

        assertNotNull(projetoResponse);
        assertEquals(projeto.getId(), projetoResponse.getId());
        assertEquals(projeto.getNome(), projetoResponse.getNome());
        assertEquals(projeto.getDescricao(), projetoResponse.getDescricao());
        assertEquals(projeto.getDataInicio(), projetoResponse.getDataInicio());
        assertEquals(projeto.getDataFim(), projetoResponse.getDataFim());
    }
}

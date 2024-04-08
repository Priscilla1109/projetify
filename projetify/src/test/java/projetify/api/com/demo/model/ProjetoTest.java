package projetify.api.com.demo.model;

import org.junit.Test;
import projetify.api.com.demo.model.Projeto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Classe usada para testar a criação do objeto Projeto
public class ProjetoTest {
    @Test
    public void projetoTest(){
        Projeto projeto = new Projeto();

        Long id = 1L;
        String nome = "Projeto";
        String descricao = "Descrição";
        LocalDate dataInicio = LocalDate.ofEpochDay(2024-04-04);
        LocalDate dataFim = LocalDate.ofEpochDay(2024-04-10);

        projeto.setId(id);
        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setDataInicio(dataInicio);
        projeto.setDataFim(dataFim);

        //verificação dos getters e setters
        assertEquals(id, projeto.getId());
        assertEquals(nome, projeto.getNome());
        assertEquals(descricao, projeto.getDescricao());
        assertEquals(dataInicio, projeto.getDataInicio());
        assertEquals(dataFim, projeto.getDataFim());
    }
}

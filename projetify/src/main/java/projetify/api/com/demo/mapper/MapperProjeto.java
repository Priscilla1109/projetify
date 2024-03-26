package projetify.api.com.demo.mapper;

import lombok.experimental.UtilityClass;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;

import java.time.LocalDate;

@UtilityClass
public class MapperProjeto {

    public Projeto toDomain(ProjetoRequest projetoRequest) {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome(projetoRequest.getNome());
        projeto.setDescricao(projetoRequest.getDescricao());
        projeto.setDataInicio(LocalDate.parse(projetoRequest.getDataInicio()));
        projeto.setDataFim(LocalDate.parse(projetoRequest.getDataFim()));
        return projeto;
    }
}

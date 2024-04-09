package projetify.api.com.demo.mapper;

import lombok.experimental.UtilityClass;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.model.ProjetoResponse;

@UtilityClass
public class ProjetoMapper {
    public Projeto toDomain(ProjetoRequest projetoRequest) {
        Projeto projeto = new Projeto();
        projeto.setId(projetoRequest.getId());
        projeto.setNome(projetoRequest.getNome());
        projeto.setDescricao(projetoRequest.getDescricao());
        projeto.setDataInicio(projetoRequest.getDataInicio());
        projeto.setDataFim(projetoRequest.getDataFim());
        return projeto;
    }

    public static ProjetoResponse toResponse(Projeto projeto){
        ProjetoResponse response = new ProjetoResponse();
        response.setId(projeto.getId());
        response.setNome(projeto.getNome());
        response.setDescricao(projeto.getDescricao());
        response.setDataInicio(projeto.getDataInicio());
        response.setDataFim(projeto.getDataFim());

        return response;
    }
}

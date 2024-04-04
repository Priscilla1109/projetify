package projetify.api.com.demo.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

//Classe de resposta especial que contenha os detalhes da página
@Data
public class ProjetoPageResponse {
    @Getter
    private List<Projeto> projetos;
    private Meta meta;
}

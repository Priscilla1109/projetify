package projetify.api.com.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoRequest {
    private Long id;

    private String nome;

    private String descricao;

    //solução para converter as datas de string para localDate
    private String dataInicio;

    private  String dataFim;
}

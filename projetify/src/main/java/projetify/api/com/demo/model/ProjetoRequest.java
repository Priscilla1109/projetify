package projetify.api.com.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
public class ProjetoRequest {

    private String nome;

    private String descricao;

    private String dataInicio;

    private  String dataFim;
}

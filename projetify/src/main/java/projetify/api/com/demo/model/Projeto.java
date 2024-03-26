package projetify.api.com.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data //extensão do lombok para getters e setters
public class Projeto {
    @Id //indica chave primária da tabela
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //delega o valor de geração da chave primária ao banco de dados
    //@Column (name = "id") //define o nome da coluna associada ao atributo
    private Long id;

    @Column (name = "nome")
    private String nome;

    @Column (name = "descrição")
    private String descricao;

    @Column (name = "dataInicio")
    private LocalDate dataInicio;

    @Column (name = "dataFim")
    private  LocalDate dataFim;
}

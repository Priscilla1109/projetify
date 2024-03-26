package projetify.api.com.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.time.LocalDate;

@Data //extensão do lombok para getters e setters
@Entity //indica que é uma entidade
public class Projeto {
    @Id
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private  LocalDate dataFim;
}

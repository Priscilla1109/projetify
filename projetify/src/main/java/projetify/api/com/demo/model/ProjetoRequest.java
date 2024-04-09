package projetify.api.com.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ProjetoRequest {
    private Long id;

    @NotBlank(message = "nome nao e valido")
    private String nome;

    @NotBlank(message = "descricao nao e valida")
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "dataInicio nao pode ser nulo")
    private LocalDate dataInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "dataFim não pode ser nulo")
    private  LocalDate dataFim;
}

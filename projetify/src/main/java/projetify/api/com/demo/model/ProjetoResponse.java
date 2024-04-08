package projetify.api.com.demo.model;

import lombok.Data;

import java.time.LocalDate;

//Classe usada como DTO (objeto de transferência de dados) para enviar dados específicos de um projetode volta para o cliente em uma resposta HTTP
@Data
public class ProjetoResponse {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}

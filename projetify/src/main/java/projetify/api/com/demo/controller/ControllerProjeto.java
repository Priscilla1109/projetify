package projetify.api.com.demo.controller;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositorioProjeto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/APIs/projetify")
//Classe responsável por lidar com as requisições HTTP
public class ControllerProjeto {

    @Autowired
    private RepositorioProjeto repositorioProjeto;

    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@RequestBody ProjetoRequest projetoRequest){
        Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest);
        Projeto novoProjeto = repositorioProjeto.save(projetoDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @GetMapping
    public List<Projeto> listarProjetos(){
        return  repositorioProjeto.findAll(); //retorna uma lista de todos os projetos
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarProjetoId(@PathVariable Long id){
        Optional<Projeto> projeto = repositorioProjeto.findById(id); //classe optional lida com a possibilidade do projeto não exixtir no banco de dados
        return projeto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@RequestBody Projeto projeto){
        Projeto novoProjeto = repositorioProjeto.save(projeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        Optional<Projeto> projetoExistente = repositorioProjeto.findById(id);
        if (projetoExistente.isPresent()){
            Projeto projeto = projetoExistente.get();
            projeto.setNome(projetoAtualizado.getNome());
            projeto.setDescricao(projetoAtualizado.getDescricao());
            projeto.setDataInicio(projetoAtualizado.getDataInicio());
            projeto.setDataFim(projetoAtualizado.getDataFim());
            repositorioProjeto.save(projeto);
            return ResponseEntity.ok(projeto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deteletarProjeto(@PathVariable Long id){
        repositorioProjeto.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

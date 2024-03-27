package projetify.api.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/APIs/projetify")
//Classe responsável por lidar com as requisições HTTP
public class ControllerProjeto {

    @Autowired
    private RepositoryProjeto repositoryProjeto;

    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@RequestBody ProjetoRequest projetoRequest){
        Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest);
        Projeto novoProjeto = repositoryProjeto.save(projetoDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @GetMapping
    public List<Projeto> listarProjetos(){
        return  repositoryProjeto.findAll(); //retorna uma lista de todos os projetos
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarProjetoId(@PathVariable Long id){
        Optional<Projeto> projeto = repositoryProjeto.findById(id); //classe optional lida com a possibilidade do projeto não exixtir no banco de dados
        return projeto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        Optional<Projeto> projetoExistente = repositoryProjeto.findById(id);
        if (projetoExistente.isPresent()){
            Projeto projeto = projetoExistente.get();
            projeto.setNome(projetoAtualizado.getNome());
            projeto.setDescricao(projetoAtualizado.getDescricao());
            projeto.setDataInicio(projetoAtualizado.getDataInicio());
            projeto.setDataFim(projetoAtualizado.getDataFim());
            repositoryProjeto.save(projeto);
            return ResponseEntity.ok(projeto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deteletarProjeto(@PathVariable Long id){
        repositoryProjeto.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

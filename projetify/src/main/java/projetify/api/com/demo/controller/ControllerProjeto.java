package projetify.api.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.repository.RepositorioProjeto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/APIs/projetify") //necessário revisar path
public class ControllerProjeto {
    @Autowired
    private RepositorioProjeto repositorioProjeto;

    @GetMapping
    public List<Projeto> listarProjetos(){
        return  repositorioProjeto.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarProjetoId(@PathVariable Long id){
        Optional<Projeto> projeto = repositorioProjeto.findById(id);
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

        }
    }
}

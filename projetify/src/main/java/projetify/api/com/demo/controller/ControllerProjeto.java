package projetify.api.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;
import projetify.api.com.demo.service.ProjetoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/APIs/projetify")
//Classe responsável por lidar com as requisições HTTP
public class ControllerProjeto {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public Projeto criarProjeto(@RequestBody ProjetoRequest projetoRequest){
        return projetoService.criarProjeto(projetoRequest);
    }

    @GetMapping
    public List<Projeto> listarProjetos(){ //funciona
        return projetoService.listarProjetos(); //retorna uma lista de todos os projetos
    }

    @GetMapping("/{id}")
    public Projeto buscarProjetoId(@PathVariable Long id){
        return projetoService.buscarProjetoId(id);
    }

    @PutMapping("/{id}")
    public Projeto atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        return projetoService.atualizarProjeto(id, projetoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deteletarProjeto(@PathVariable Long id){
        projetoService.deletarProjeto(id);
    }
}

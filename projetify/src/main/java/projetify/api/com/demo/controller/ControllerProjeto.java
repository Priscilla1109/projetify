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
    public ResponseEntity<String> criarProjeto(@RequestBody ProjetoRequest projetoRequest){
        projetoService.criarProjeto(projetoRequest);
        return new ResponseEntity<>("Projeto criado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping
    public List<Projeto> listarProjetos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        List<Projeto> projetos = projetoService.listarProjetosPaginados(page, pageSize);
        return projetos; //retorna uma lista de todos os projetos
    }

    @GetMapping("/{id}")
    public Projeto buscarProjetoId(@PathVariable Long id){
        return projetoService.buscarProjetoId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        projetoService.atualizarProjeto(id, projetoAtualizado);
        return new ResponseEntity<>("Projeto atualizado com sucesso!", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public void deteletarProjeto(@PathVariable Long id){
        projetoService.deletarProjeto(id);
    }
}

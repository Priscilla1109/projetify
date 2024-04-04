package projetify.api.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Meta;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoPageResponse;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.service.ProjetoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/APIs/projetify")
//Classe responsável por lidar com as requisições HTTP
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<String> criarProjeto(@RequestBody ProjetoRequest projetoRequest){
        projetoService.criarProjeto(projetoRequest);
        return new ResponseEntity<>("Projeto criado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping("/projetos")
    public ResponseEntity<ProjetoPageResponse> listarProjetos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Projeto> projetoPage = projetoService.listarProjetosPaginados(page, pageSize);

        ProjetoPageResponse response = new ProjetoPageResponse();
        response.setProjetos(projetoPage.getContent());
        response.setMeta(new Meta(
                projetoPage.getNumber(),
                projetoPage.getSize(),
                projetoPage.getTotalPages(),
                projetoPage.getTotalElements()
        ));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscarProjetoId(@PathVariable Long id){
        projetoService.buscarProjetoId(id);
        return new ResponseEntity<>("Projeto encontrado!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        projetoService.atualizarProjeto(id, projetoAtualizado);
        return new ResponseEntity<>("Projeto atualizado com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deteletarProjeto(@PathVariable Long id){
        projetoService.deletarProjeto(id);
        return new ResponseEntity<>("Projeto deletado com sucesso!", HttpStatus.NO_CONTENT);
    }
}

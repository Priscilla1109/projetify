package projetify.api.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.*;
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
    public ResponseEntity<ProjetoResponse> criarProjeto(@RequestBody ProjetoRequest projetoRequest){
        Projeto projeto = projetoService.criarProjeto(projetoRequest);
        ProjetoResponse projetoResponse = ProjetoMapper.toResponse(projeto);

        return ResponseEntity.status(HttpStatus.CREATED).body(projetoResponse);
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
    public ResponseEntity<ProjetoResponse> buscarProjetoId(@PathVariable Long id){
        Projeto projeto = projetoService.buscarProjetoId(id);
        ProjetoResponse response = ProjetoMapper.toResponse(projeto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponse> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        Projeto projeto = projetoService.atualizarProjeto(id, projetoAtualizado);
        ProjetoResponse response = ProjetoMapper.toResponse(projeto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deteletarProjeto(@PathVariable Long id){
        projetoService.deletarProjeto(id);
        return new ResponseEntity<>("Projeto deletado com sucesso!", HttpStatus.NO_CONTENT);
    }
}

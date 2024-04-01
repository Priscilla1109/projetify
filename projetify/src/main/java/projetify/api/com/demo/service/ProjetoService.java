package projetify.api.com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projetify.api.com.demo.exception.ExistentProjectException;
import projetify.api.com.demo.exception.InvalidDataException;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//Classe responsável pelas regras de negócio
@Service
public class ProjetoService {
    @Autowired
    private RepositoryProjeto repositoryProjeto;

    public Projeto criarProjeto(ProjetoRequest projetoRequest){
        Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest);
        if (projetoDomain.getDataInicio().isAfter(projetoDomain.getDataFim())){
            throw new InvalidDataException("A data de início não pode ser maior do que a data fim!");
        }
        return repositoryProjeto.save(projetoDomain);
    }

    public List<Projeto> listarProjetosPaginados(int page, int pageSize){
        //cria o objeto para fazer a consulta paginada
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Projeto> projetosPage = repositoryProjeto.findAll(pageable);
        return projetosPage.getContent();
    }

    public Projeto buscarProjetoId(Long id){
        Optional<Projeto> projeto = repositoryProjeto.findById(id);
        return projeto.orElseThrow(()-> new NoSuchElementException());
    }

    public Projeto atualizarProjeto(Long id, Projeto projetoAtualizado) {
        if (!repositoryProjeto.existsById(id)){
            throw new ExistentProjectException("O projeto com esse ID já existe!");
        }
        Optional<Projeto> projetoExistente = repositoryProjeto.findById(id);
            Projeto projeto = new Projeto();
            projeto.setId(id);
            projeto.setNome(projetoAtualizado.getNome());
            projeto.setDescricao(projetoAtualizado.getDescricao());
            projeto.setDataInicio(projetoAtualizado.getDataInicio());
            projeto.setDataFim(projetoAtualizado.getDataFim());
            return repositoryProjeto.save(projeto);
    }

    public void deletarProjeto(Long id){
        Projeto projeto = buscarProjetoId(id);
        repositoryProjeto.delete(projeto);
    }
}

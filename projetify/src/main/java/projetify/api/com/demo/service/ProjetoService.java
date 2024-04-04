package projetify.api.com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import projetify.api.com.demo.exception.ExistentProjectException;
import projetify.api.com.demo.exception.InvalidDataException;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;

import java.util.NoSuchElementException;
import java.util.Optional;

//Classe responsável pelas regras de negócio
@Service
public class ProjetoService {
    @Autowired
    private RepositoryProjeto repositoryProjeto;

    public Projeto criarProjeto(ProjetoRequest projetoRequest){
        Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest);
        if (!repositoryProjeto.existsById(projetoDomain.getId())){
            throw new ExistentProjectException("O projeto com esse ID já existe!");
        }
        if (projetoDomain.getDataInicio().isAfter(projetoDomain.getDataFim())){
            throw new InvalidDataException("A data de início não pode ser maior do que a data fim!");
        }
        return repositoryProjeto.save(projetoDomain);
    }

    public Page<Projeto> listarProjetosPaginados(int page, int size){ //a classe PageSize ja contém informações sobre paginação
        PageRequest pageRequest = PageRequest.of(page, size);
        return repositoryProjeto.findAll(pageRequest);
    }

    public Projeto buscarProjetoId(Long id){
        Optional<Projeto> projeto = repositoryProjeto.findById(id);
        return projeto.orElseThrow(()-> new NoSuchElementException());
    }

    public Projeto atualizarProjeto(Long id, Projeto projetoAtualizado) {
        Optional<Projeto> projetoAntigo = repositoryProjeto.findById(id);
        if (projetoAntigo.isPresent()){
            Projeto projeto = projetoAntigo.get();
            projeto.setId(projetoAtualizado.getId());
            projeto.setNome(projetoAtualizado.getNome());
            projeto.setDescricao(projetoAtualizado.getDescricao());
            projeto.setDataInicio(projetoAtualizado.getDataInicio());
            projeto.setDataFim(projetoAtualizado.getDataFim());

            return repositoryProjeto.save(projeto);
        } else {
            throw new NoSuchElementException("Esse projeto não existe para ser atualizado!");
        }
    }

    public boolean deletarProjeto(Long id){
        Projeto projeto = buscarProjetoId(id);
        repositoryProjeto.delete(projeto);
        return true;
    }
}

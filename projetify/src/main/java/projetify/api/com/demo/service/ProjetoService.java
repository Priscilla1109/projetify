package projetify.api.com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

//Classe responsável pelas regras de negócio
@Service
public class ProjetoService {
    @Autowired
    private RepositoryProjeto repositoryProjeto;

    public Projeto criarProjeto(ProjetoRequest projetoRequest){
        Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest);
        if (repositoryProjeto.existsById(projetoDomain.getId())){
            throw new RuntimeException("O projeto já existe!");
        }
        if (projetoDomain.isDataInicioAfterDataFim()){
            throw new RuntimeException("A data de início não pode ser maior do que a data fim!");
        }
        Projeto novoProjeto = repositoryProjeto.save(projetoDomain);
        return repositoryProjeto.save(novoProjeto);
    }

    public List<Projeto> listarProjetos(){
        return repositoryProjeto.findAll();
    }

    public Projeto buscarProjetoId(Long id){
        Optional<Projeto> projeto = repositoryProjeto.findById(id);
        return projeto.orElseThrow(()-> new RuntimeException("Projeto não encontrado!"));
    }

    public Projeto atualizarProjeto(Long id, Projeto projetoAtualizado) {
        Optional<Projeto> projetoExistente = repositoryProjeto.findById(id);
            Projeto projeto = projetoExistente.get();
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

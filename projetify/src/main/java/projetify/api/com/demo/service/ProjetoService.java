package projetify.api.com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;

@Service
public class ProjetoService {
    @Autowired
    private RepositoryProjeto repositoryProjeto;

    //Verificar se o projeto já existe
    public Projeto criarProjeto(Projeto projeto){
        if (repositoryProjeto.existsById(projeto.getId())){
            throw new RuntimeException("O projeto já existe");
        }
        return repositoryProjeto.save(projeto);
    }
}

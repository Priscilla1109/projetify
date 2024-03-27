package projetify.api.com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projetify.api.com.demo.model.Projeto;

@Repository
public interface RepositoryProjeto extends JpaRepository<Projeto, Long> {

}

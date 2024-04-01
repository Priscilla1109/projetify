package projetify.api.com.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projetify.api.com.demo.model.Projeto;

import java.util.List;

@Repository
public interface RepositoryProjeto extends PagingAndSortingRepository<Projeto, Long> {
    Page<Projeto> findAll(Pageable pageable);
}

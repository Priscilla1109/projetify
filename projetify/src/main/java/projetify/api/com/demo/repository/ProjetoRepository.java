package projetify.api.com.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import projetify.api.com.demo.model.Projeto;

@Repository
public interface ProjetoRepository extends PagingAndSortingRepository<Projeto, Long> {
    Page<Projeto> findAll(Pageable pageable);
}

package br.com.agroplanner.repository;

import br.com.agroplanner.domain.Propriedade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Propriedade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long>, JpaSpecificationExecutor<Propriedade> {

    @Query("select propriedade from Propriedade propriedade where propriedade.user.login = ?#{principal.username}")
    List<Propriedade> findByUserIsCurrentUser();

}

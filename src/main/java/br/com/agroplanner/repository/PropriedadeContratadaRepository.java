package br.com.agroplanner.repository;

import br.com.agroplanner.domain.PropriedadeContratada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PropriedadeContratada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropriedadeContratadaRepository extends JpaRepository<PropriedadeContratada, Long>, JpaSpecificationExecutor<PropriedadeContratada> {

    @Query("select propriedadeContratada from PropriedadeContratada propriedadeContratada where propriedadeContratada.user.login = ?#{principal.username}")
    List<PropriedadeContratada> findByUserIsCurrentUser();

}

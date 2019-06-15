package br.com.agroplanner.repository;

import br.com.agroplanner.domain.PropriedadeCaracteristica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PropriedadeCaracteristica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropriedadeCaracteristicaRepository extends JpaRepository<PropriedadeCaracteristica, Long>, JpaSpecificationExecutor<PropriedadeCaracteristica> {

}

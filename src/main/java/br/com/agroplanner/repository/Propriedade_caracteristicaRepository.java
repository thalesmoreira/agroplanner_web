package br.com.agroplanner.repository;

import br.com.agroplanner.domain.Propriedade_caracteristica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Propriedade_caracteristica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Propriedade_caracteristicaRepository extends JpaRepository<Propriedade_caracteristica, Long>, JpaSpecificationExecutor<Propriedade_caracteristica> {

}

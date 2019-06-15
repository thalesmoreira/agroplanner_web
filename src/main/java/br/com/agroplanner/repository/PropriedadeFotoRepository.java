package br.com.agroplanner.repository;

import br.com.agroplanner.domain.PropriedadeFoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PropriedadeFoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropriedadeFotoRepository extends JpaRepository<PropriedadeFoto, Long>, JpaSpecificationExecutor<PropriedadeFoto> {

}

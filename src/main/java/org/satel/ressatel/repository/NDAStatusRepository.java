package org.satel.ressatel.repository;

import org.satel.ressatel.entity.NDAStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NDAStatusRepository extends JpaRepository<NDAStatus, Integer> {
    NDAStatus findByName(String name);
}

package org.glygen.tablemaker.persistence.dao;

import java.util.List;

import org.glygen.tablemaker.persistence.dataset.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
	
	List<Publication> findByDoiId (String doi);
	List<Publication> findByPubmedId (Integer pubmedId);
}

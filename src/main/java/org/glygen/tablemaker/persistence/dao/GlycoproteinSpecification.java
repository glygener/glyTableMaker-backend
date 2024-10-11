package org.glygen.tablemaker.persistence.dao;

import org.glygen.tablemaker.persistence.UserEntity;
import org.glygen.tablemaker.persistence.glycan.GlycanTag;
import org.glygen.tablemaker.persistence.protein.Glycoprotein;
import org.glygen.tablemaker.view.Filter;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@SuppressWarnings("serial")
public class GlycoproteinSpecification implements Specification<Glycoprotein> {

	Filter filter;
	
	public GlycoproteinSpecification(final Filter filter) {
		super();
		this.filter = filter;
	}
  
	@Override
	public Predicate toPredicate(Root<Glycoprotein> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like(
                root.<String>get(filter.getId()).as(String.class), "%" + filter.getValue() + "%");
	}
	
	public static Specification<Glycoprotein> hasUserWithId(Long userid) {
	    return (root, query, criteriaBuilder) -> {
	        Join<UserEntity, Glycoprotein> gUser = root.join("user");
	        return criteriaBuilder.equal(gUser.get("userId"), userid);
	    };
	}
	
	public static Specification<Glycoprotein> hasTag(String label) {
	    return (root, query, criteriaBuilder) -> {
	        Join<GlycanTag, Glycoprotein> gTags = root.join("tags");
	        return criteriaBuilder.like(gTags.get("label"), "%" + label + "%");
	    };
	}

}
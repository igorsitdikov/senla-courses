package com.senla.bulletinboard.repository.specification;

import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.DialogEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DialogExistsSpecification implements Specification<DialogEntity> {

    private final Long id;
    private final Long ownerId;

    public DialogExistsSpecification(final Long id, final Long ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    @Override
    public Predicate toPredicate(final Root<DialogEntity> root,
                                 final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {
        Join<DialogEntity, BulletinEntity> entityDialogEntityJoin = root.join("bulletin");
        return criteriaBuilder.and(
            criteriaBuilder.equal(root.get("id"), id),
            criteriaBuilder.or(
                criteriaBuilder.equal(entityDialogEntityJoin.get("sellerId"), ownerId),
                criteriaBuilder.equal(root.get("customerId"), ownerId)));
    }
}

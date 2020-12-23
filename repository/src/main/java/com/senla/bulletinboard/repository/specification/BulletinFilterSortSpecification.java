package com.senla.bulletinboard.repository.specification;

import com.senla.bulletinboard.dto.FilterDto;
import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.entity.SellerVoteEntity;
import com.senla.bulletinboard.entity.UserEntity;
import com.senla.bulletinboard.enumerated.BulletinStatus;
import com.senla.bulletinboard.enumerated.SortBulletin;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BulletinFilterSortSpecification implements Specification<BulletinEntity> {

    private final FilterDto criteria;
    private final SortBulletin sortBulletin;
    private final Integer page;
    private final Integer size;

    public BulletinFilterSortSpecification(final FilterDto criteria,
                                           final SortBulletin sortBulletin,
                                           final Integer page,
                                           final Integer size) {
        this.criteria = criteria;
        this.sortBulletin = sortBulletin;
        this.page = page;
        this.size = size;
    }

    @Override
    public Predicate toPredicate(final Root<BulletinEntity> root,
                                 final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {
        root.fetch("seller");

        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getPriceGte() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.getPriceGte()));
        }
        if (criteria.getPriceLte() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getPriceLte()));
        }
        predicates.add(criteriaBuilder.equal(root.get("status"), BulletinStatus.OPEN));
        Integer firstElement = calculateFirstElement(page, size);
        Integer lastElement = calculateLastElement(page, size);
        predicates.add(criteriaBuilder.between(root.get("id"), firstElement, lastElement));

        criteriaQuery.groupBy(root.get("id"));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Integer calculateFirstElement(Integer page, Integer size) {
        return page * size;
    }

    private Integer calculateLastElement(Integer page, Integer size) {
        return calculateFirstElement(page, size) + size;
    }
}
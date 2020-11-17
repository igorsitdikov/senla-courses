package com.senla.bulletin_board.repository.specification;

import com.senla.bulletin_board.dto.FilterDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.SortBulletin;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BulletinSpecification implements Specification<BulletinEntity> {

    private final FilterDto criteria;
    private final SortBulletin sortBulletin;

    public BulletinSpecification(final FilterDto criteria, final SortBulletin sortBulletin) {
        this.criteria = criteria;
        this.sortBulletin = sortBulletin;
    }

    @Override
    public Predicate toPredicate(final Root<BulletinEntity> root,
                                 final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {
        final Path<UserEntity> seller = root.get("seller");
        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getPriceGte() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.getPriceGte()));
        }
        if (criteria.getPriceLte() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getPriceLte()));
        }
        Path<Object> path = getObjectPath(root, seller);
        criteriaQuery.orderBy(criteriaBuilder.asc(seller.get("premium")),
                              criteriaBuilder.desc(path));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Path<Object> getObjectPath(final Root<BulletinEntity> root, final Path<UserEntity> seller) {
        Path<Object> path;
        switch (sortBulletin) {
            case AUTHOR:
                path = seller.get(sortBulletin.getField());
                break;
            default:
                path = root.get(sortBulletin.getField());
        }
        return path;
    }
}

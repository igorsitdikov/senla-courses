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

    public BulletinFilterSortSpecification(final FilterDto criteria, final SortBulletin sortBulletin) {
        this.criteria = criteria;
        this.sortBulletin = sortBulletin;
    }

    @Override
    public Predicate toPredicate(final Root<BulletinEntity> root,
                                 final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {
        Join<BulletinEntity, UserEntity> entityUserEntityJoin = root.join("seller");
        root.fetch("seller");
        Join<SellerVoteEntity, BulletinEntity> sellerVoteEntityJoin = root.join("sellerVoteEntities", JoinType.LEFT);

        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getPriceGte() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.getPriceGte()));
        }
        if (criteria.getPriceLte() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.getPriceLte()));
        }
        predicates.add(criteriaBuilder.equal(root.get("status"), BulletinStatus.OPEN));
        final Order orderByPremiumStatus = criteriaBuilder.asc(root.get("seller").get("premium"));
        Expression<?> path;
        switch (sortBulletin) {
            case AUTHOR:
                path = entityUserEntityJoin.get(sortBulletin.getField());
                break;
            case AVERAGE:
                path = criteriaBuilder.avg(sellerVoteEntityJoin.get(sortBulletin.getField()));
                break;
            default:
                path = root.get(sortBulletin.getField());
        }

        criteriaQuery.orderBy(orderByPremiumStatus, criteriaBuilder.desc(path));
        criteriaQuery.groupBy(root.get("id"));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
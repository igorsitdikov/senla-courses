package com.senla.bulletin_board.repository.specification;

import com.senla.bulletin_board.dto.FilterDto;
import com.senla.bulletin_board.entity.BulletinEntity;
import com.senla.bulletin_board.entity.SellerVoteEntity;
import com.senla.bulletin_board.entity.UserEntity;
import com.senla.bulletin_board.enumerated.BulletinStatus;
import com.senla.bulletin_board.enumerated.SortBulletin;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
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
        switch (sortBulletin) {
            case AUTHOR:
                Path<Object> path = entityUserEntityJoin.get(sortBulletin.getField());
                criteriaQuery.orderBy(orderByPremiumStatus,
                                      criteriaBuilder.desc(path));
                break;
            case AVERAGE:
                final Expression<Double> avgVote = criteriaBuilder.avg(sellerVoteEntityJoin.get("vote"));
                criteriaQuery.orderBy(orderByPremiumStatus,
                                      criteriaBuilder.desc(avgVote));
                break;
            default:
                path = root.get(sortBulletin.getField());
                criteriaQuery.orderBy(orderByPremiumStatus,
                                      criteriaBuilder.desc(path));
        }

        criteriaQuery.groupBy(root.get("id"));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
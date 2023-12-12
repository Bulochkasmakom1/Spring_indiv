package com.example.crm.data.specifications;

import com.example.crm.data.entities.Participant;

import javax.persistence.criteria.*;
import java.util.ArrayList;

public class ParticipantDatatableFilter implements org.springframework.data.jpa.domain.Specification<Participant>{

    String userQuery;

    public ParticipantDatatableFilter(String queryString) {
        this.userQuery = queryString;
    }

    @Override
    public Predicate toPredicate(Root<Participant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (userQuery != null && userQuery != "") {
            predicates.add(criteriaBuilder.like(root.get("child"), '%' + userQuery + '%'));
 //           predicates.add(criteriaBuilder.like(root.get("dateBirth"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("city"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("country"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("parent"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("emailAddress"), '%' + userQuery + '%'));
            predicates.add(criteriaBuilder.like(root.get("phoneNumber"), '%' + userQuery + '%'));
            Expression<String> dateStringExpression = criteriaBuilder.function("TO_CHAR", String.class, root.get("dateBirth"),
                    criteriaBuilder.literal("YYYY-MM-DD"));
            predicates.add(criteriaBuilder.like(dateStringExpression, '%' + userQuery + '%'));
        }

        return (! predicates.isEmpty() ? criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])) : null);
    }
}

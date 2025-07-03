package com.transbnk.internPractise.service;

import com.transbnk.internPractise.dto.SearchRequestDto;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class FiltersSpecification<T> {

//    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto){
//        return new Specification<T>() {
//            @Override
//            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
//            }
//        };
//    }

    public Specification<T> getSearchSpecification(SearchRequestDto request) {
        return (root, query, criteriaBuilder) -> {
            String column = request.getColumn();
            String value = request.getValue();
            String operation = request.getOperation();

            Path<?> path = root.get(column);
            Class<?> type = path.getJavaType();

            switch (operation.toUpperCase()) {
                case "EQUAL":
                    return criteriaBuilder.equal(path, castToType(type, value));

                case "LIKE":
                    if (!type.equals(String.class)) {
                        throw new IllegalArgumentException("LIKE operation only allowed on String fields");
                    }
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get(column)), "%" + value.toLowerCase() + "%");

                case "GREATER_THAN":
                    if (!Comparable.class.isAssignableFrom(type)) {
                        throw new IllegalArgumentException("GREATER_THAN requires Comparable type");
                    }
                    return criteriaBuilder.greaterThan((Path<Comparable>) path, (Comparable) castToType(type, value));

                case "LESS_THAN":
                    if (!Comparable.class.isAssignableFrom(type)) {
                        throw new IllegalArgumentException("LESS_THAN requires Comparable type");
                    }
                    return criteriaBuilder.lessThan((Path<Comparable>) path, (Comparable) castToType(type, value));

                case "GREATER_THAN_EQUAL":
                    if (!Comparable.class.isAssignableFrom(type)) {
                        throw new IllegalArgumentException("GREATER_THAN_EQUAL requires Comparable type");
                    }
                    return criteriaBuilder.greaterThanOrEqualTo((Path<Comparable>) path, (Comparable) castToType(type, value));

                case "LESS_THAN_EQUAL":
                    if (!Comparable.class.isAssignableFrom(type)) {
                        throw new IllegalArgumentException("LESS_THAN_EQUAL requires Comparable type");
                    }
                    return criteriaBuilder.lessThanOrEqualTo((Path<Comparable>) path, (Comparable) castToType(type, value));

                default:
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
            }
        };
    }
    private Object castToType(Class<?> type, String value) {
        if (type.equals(String.class)) {
            return value;
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.isEnum()) {
            return Enum.valueOf((Class<Enum>) type, value.toUpperCase());
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type.getName());
        }
    }
}

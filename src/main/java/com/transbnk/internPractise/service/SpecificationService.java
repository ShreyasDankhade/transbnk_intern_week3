package com.transbnk.internPractise.service;


import com.transbnk.internPractise.dto.SearchRequestDto;
import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecificationService {

    private final UserRepo userRepo;

    private final FiltersSpecification<User> userFiltersSpecification;

    public List<User> getUsers(String column, String value, String operation, String sortBy, String direction){
        SearchRequestDto dto = new SearchRequestDto();
        dto.setColumn(column);
        dto.setValue(value);
        dto.setOperation(operation);

        Specification<User> searchSpecification = userFiltersSpecification.getSearchSpecification(dto);

        Sort sort = Sort.by(Sort.Direction.fromString(direction),sortBy);
        return userRepo.findAll(searchSpecification, sort);
    }
}
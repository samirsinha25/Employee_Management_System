package com.employeemanagement.mapper;


import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

   

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    // Ignore fields when converting DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    // No need for explicit mappings here (fields have the same names)
    EmployeeDTO toDTO(Employee employee);
}

package com.example.cs_test_assignment.model.mapper.config;

import com.example.cs_test_assignment.model.dto.RequestUserDTO;
import com.example.cs_test_assignment.model.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<RequestUserDTO, User> noMapId =
                modelMapper.createTypeMap(RequestUserDTO.class, User.class, "noMapId");
        noMapId.addMappings(mapping -> mapping.skip(User::setId));

        TypeMap<RequestUserDTO, User> noMapNull =
                modelMapper.createTypeMap(RequestUserDTO.class, User.class, "noMapNull");
        noMapNull
                .setPropertyCondition(context -> context.getSource() != null);

        return modelMapper;
    }
}

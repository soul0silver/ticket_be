package com.userservice.model.mapper;

import com.userservice.model.response.TeacherResponse;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static TeacherResponse mapFromMap(Map<String, Object> map) {
        return modelMapper.map(map, TeacherResponse.class);
    }

    public static List<TeacherResponse> mapListFromMaps(List<Map<String, Object>> maps) {
        return maps.stream()
                .map(Mapper::mapFromMap)
                .collect(Collectors.toList());
    }
}
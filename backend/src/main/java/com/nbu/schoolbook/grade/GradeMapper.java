package com.nbu.schoolbook.grade;


import com.nbu.schoolbook.grade.dto.GradeDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
@AllArgsConstructor
public class GradeMapper {
    private final ModelMapper modelMapper;


}

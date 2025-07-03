package com.dojagy.todaysave.mapper;

import com.dojagy.todaysave.dto.category.CategoryResponseDto;
import com.dojagy.todaysave.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

    CategoryResponseDto toDto(Category category);
    List<CategoryResponseDto> toDtoList(List<Category> categories);
}

package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.category.CategoryCreateDto;
import com.dojagy.todaysave.dto.category.CategoryResponseDto;
import com.dojagy.todaysave.dto.user.UserPrincipal;
import com.dojagy.todaysave.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("")
    public Result<CategoryResponseDto> create(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @RequestBody CategoryCreateDto requestDto) {
        return categoryService.createNewCategory(requestDto.getName(), userPrincipal.id());
    }

    @GetMapping("/search")
    public Result<Page<CategoryResponseDto>> searchCategories(
            @RequestParam String keyword,
            @PageableDefault(size = 20) Pageable pageable) {

        return categoryService.searchCategories(keyword, pageable);
    }
}

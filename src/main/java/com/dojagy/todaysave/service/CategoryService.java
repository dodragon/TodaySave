package com.dojagy.todaysave.service;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.category.CategoryResponseDto;
import com.dojagy.todaysave.entity.Category;
import com.dojagy.todaysave.entity.User;
import com.dojagy.todaysave.mapper.CategoryMapper;
import com.dojagy.todaysave.repository.CategoryRepository;
import com.dojagy.todaysave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 성능 최적화
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    public Result<CategoryResponseDto> createNewCategory(String categoryName, Long userId) {
        // 1. 입력값 검증 (앞뒤 공백 제거 및 빈 문자열 체크)
        String trimmedName = categoryName.trim();
        if (trimmedName.isEmpty()) {
            return Result.FAILURE("카테고리 이름은 비어 있을 수 없습니다.");
        }

        // 2. 먼저 정확한 이름으로 카테고리가 있는지 확인합니다.
        Optional<Category> existingCategory = categoryRepository.findByName(trimmedName);
        if (existingCategory.isPresent()) {
            return Result.FAILURE("이미 존재하는 카테고리 입니다.", categoryMapper.toDto(existingCategory.get()));
        }

        // 3. 존재하지 않으면 새로운 카테고리를 생성하고 저장합니다.
        // 이 블록에서 '경쟁 상태'가 발생할 수 있습니다. (상세 설명 참조)
        try {
            User creator = userRepository.findById(userId).orElse(null);
            if(creator == null) {
                return Result.FAILURE("사용자를 찾을 수 없습니다.");
            }

            Category newCategory = Category.builder()
                    .name(trimmedName)
                    .user(creator) // 최초 생성자 기록
                    .build();

            Category savedCategory = categoryRepository.save(newCategory);
            return Result.SUCCESS("카테고리 생성 완료했습니다.", categoryMapper.toDto(savedCategory));

        } catch (DataIntegrityViolationException e) {
            // 4. (중요) 저장 중 UNIQUE 제약 조건 위반 예외가 발생한 경우
            //    -> 다른 스레드가 방금 똑같은 이름의 카테고리를 먼저 저장했다는 의미입니다.
            return Result.FAILURE("이미 존재하는 카테고리 입니다.");
        }
    }

    public Result<Page<CategoryResponseDto>> searchCategories(String keyword, Pageable pageable) {
        // 컨트롤러에서 넘어온 Pageable 객체에 정렬 정보가 없다면,
        // 기본 정렬(이름 오름차순)을 설정해줍니다.
        // 만약 클라이언트가 다른 정렬(ex: 최신순)을 요청하면 그게 우선 적용됩니다.
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
        }

        Page<Category> categoryPage = categoryRepository.findByNameContaining(keyword, pageable);

        return Result.SUCCESS("카테고리 검색에 성공했습니다.", categoryPage.map(categoryMapper::toDto));
    }
}

package com.dojagy.todaysave.util.config;

import com.dojagy.todaysave.data.entity.Category;
import com.dojagy.todaysave.data.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count() == 0) {
            log.info("데이터베이스에 기본 카테고리가 없어 초기화를 시작합니다.");

            List<String> categoryNames = Arrays.asList(
                    "커리어/자기계발",
                    "재테크/투자",
                    "IT/테크",
                    "운동/건강",
                    "맛집/카페",
                    "여행",
                    "문화/예술",
                    "패션/뷰티",
                    "취미/리빙",
                    "인사이트/생각",
                    "기타"
            );

            categoryNames.forEach(name -> {
                Category category = Category.builder()
                        .name(name)
                        // User 필드는 null로 두면 자동으로 DB에 NULL로 저장됩니다.
                        // .user(null) // 명시적으로 작성할 필요 없습니다.
                        .build();
                categoryRepository.save(category);
            });

            log.info("기본 카테고리 {}개가 성공적으로 추가되었습니다.", categoryRepository.count());
        }else {
            log.info("데이터베이스에 이미 기본 카테고리가 존재합니다.");
        }
    }
}

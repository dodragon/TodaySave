package com.dojagy.todaysave.util.config;

import com.dojagy.todaysave.data.entity.Category;
import com.dojagy.todaysave.data.entity.NicknameAdjective;
import com.dojagy.todaysave.data.entity.NicknameNoun;
import com.dojagy.todaysave.data.repository.CategoryRepository;
import com.dojagy.todaysave.data.repository.NicknameAdjectiveRepository;
import com.dojagy.todaysave.data.repository.NicknameNounRepository;
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
    private final NicknameAdjectiveRepository adjectiveRepository;
    private final NicknameNounRepository nounRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeCategory();
        initializeNicknameWords();
    }

    private void initializeCategory() {
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

    private void initializeNicknameWords() {
        if (adjectiveRepository.count() == 0 && nounRepository.count() == 0) {
            log.info("데이터베이스에 닉네임 조합용 단어가 없어 초기화를 시작합니다.");

            List<String> adjectives = Arrays.asList(
                    // 감정/기분
                    "설레는", "행복한", "우울한", "심심한", "외로운", "신나는", "그리운", "궁금한", "부끄러운", "흐뭇한",
                    "서운한", "속상한", "진지한", "유쾌한", "시무룩한", "의욕없는", "기대하는", "감동받은", "고민하는", "만족한",
                    // 상태/모습
                    "졸린", "피곤한", "배고픈", "나른한", "어지러운", "따끈한", "시원한", "눅눅한", "뽀송한", "말랑한",
                    "폭신한", "동그란", "네모난", "흐릿한", "투명한", "반짝이는", "조용한", "시끄러운", "어두운", "새하얀",
                    "뭉클한", "바삭한", "촉촉한", "매콤한", "짭짤한", "느긋한", "게으른", "부지런한", "어설픈", "야무진",
                    // 행동/동작
                    "뒹구는", "떠드는", "산책하는", "생각하는", "고개숙인", "잠자는", "꿈꾸는", "숨어있는", "날아가는", "떠다니는",
                    "기다리는", "끄적이는", "흥얼대는", "방황하는", "질주하는", "춤추는", "뛰어가는", "헤엄치는", "소리치는", "속삭이는",
                    // 기타 추상/독특함
                    "비밀스러운", "엉뚱한", "알수없는", "위험한", "수상한", "평범한", "특별한", "오래된", "새로운", "조그만",
                    "거대한", "이상한", "신비로운", "단호한", "출렁이는", "단단한", "무심한", "다정한", "용감한", "소심한"
            );
            List<String> nouns = Arrays.asList(
                    // 일상 소품 (집/사무실)
                    "우산", "폴더", "클립", "손톱깎이", "쿠션", "메모지", "연필", "지우개", "리모컨", "휴지통",
                    "컴퓨터", "마우스", "키보드", "스피커", "충전기", "알람시계", "달력", "서랍", "이불", "베개",
                    "소파", "책상", "의자", "스탠드", "머그컵", "텀블러", "안경", "양말", "슬리퍼", "이어폰",
                    "가습기", "현관문", "창문", "비밀번호", "열쇠", "동전", "포스트잇", "볼펜", "가위", "거울",
                    // 음식/음료
                    "컵라면", "햄버거", "감자", "스콘", "커피", "콜라", "팝콘", "도넛", "아이스티", "샌드위치",
                    "마카롱", "솜사탕", "초콜릿", "치즈볼", "김밥", "떡볶이", "붕어빵", "계란후라이", "시리얼", "우유",
                    "요거트", "사이다", "핫초코", "오렌지", "귤", "푸딩", "아포가토", "크루아상", "소금빵", "각설탕",
                    // 동물/사물/기타
                    "문어", "고래", "고양이", "강아지", "햄스터", "나무늘보", "카피바라", "해파리", "먼지", "구름",
                    "비눗방울", "눈사람", "인형", "택배상자", "유령", "로봇", "외계인", "돌멩이", "낙엽", "별똥별"
            );

            adjectives.forEach(word -> adjectiveRepository.save(NicknameAdjective.builder().word(word).build()));
            nouns.forEach(word -> nounRepository.save(NicknameNoun.builder().word(word).build()));

            log.info("닉네임 단어 초기화 완료: 형용사 {}개, 명사 {}개", adjectiveRepository.count(), nounRepository.count());
        } else {
            log.info("데이터베이스에 이미 닉네임 조합용 단어가 존재합니다.");
        }
    }
}

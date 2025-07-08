package com.dojagy.todaysave.data.service;

import com.dojagy.todaysave.data.dto.Result;
import com.dojagy.todaysave.data.entity.NicknameAdjective;
import com.dojagy.todaysave.data.entity.NicknameNoun;
import com.dojagy.todaysave.data.repository.NicknameAdjectiveRepository;
import com.dojagy.todaysave.data.repository.NicknameNounRepository;
import com.dojagy.todaysave.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class NicknameGeneratorService {

    private final UserRepository userRepository;
    private final NicknameAdjectiveRepository adjectiveRepository;
    private final NicknameNounRepository nounRepository;
    private final Random random = new Random();

    // 랜덤 조합을 시도할 횟수
    private static final int RANDOM_NICKNAME_RETRY_COUNT = 5;

    @Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 성능 향상
    public Result<String> generateUniqueNickname() {
        String candidateNickname = "";

        // 1단계: 랜덤 조합 시도
        for (int i = 0; i < RANDOM_NICKNAME_RETRY_COUNT; i++) {
            candidateNickname = createRandomNickname();
            if (!userRepository.existsByNickname(candidateNickname)) {
                log.info("랜덤 닉네임 생성 성공: {}", candidateNickname);
                return Result.SUCCESS("닉네임 생성에 성공했습니다.", candidateNickname);
            }
        }

        // 2단계: 랜덤 조합 실패 시, 숫자 접미사 추가
        log.warn("랜덤 닉네임 생성에 모두 실패하여 숫자 접미사 추가 로직을 실행합니다. Base: {}", candidateNickname);
        String baseNickname = candidateNickname;
        int suffix = 2;
        while (true) {
            String suffixedNickname = baseNickname + suffix;
            if (!userRepository.existsByNickname(suffixedNickname)) {
                log.info("숫자 접미사 닉네임 생성 성공: {}", suffixedNickname);
                return Result.SUCCESS("닉네임 생성에 성공했습니다.", suffixedNickname);
            }
            suffix++;
        }
    }

    private String createRandomNickname() {
        String adjective = getRandomWord(adjectiveRepository, adjectiveRepository.count());
        String noun = getRandomWord(nounRepository, nounRepository.count());
        return adjective + noun;
    }

    private <T> String getRandomWord(JpaRepository<T, Long> repository, long count) {
        if (count == 0) {
            throw new IllegalStateException("닉네임 생성을 위한 단어가 DB에 존재하지 않습니다.");
        }
        int randomIndex = random.nextInt((int) count);
        Page<T> page = repository.findAll(PageRequest.of(randomIndex, 1));

        T entity = page.getContent().get(0);

        if (entity instanceof NicknameAdjective) {
            return ((NicknameAdjective) entity).getWord();
        } else if (entity instanceof NicknameNoun) {
            return ((NicknameNoun) entity).getWord();
        }
        throw new IllegalArgumentException("지원되지 않는 엔티티 타입입니다.");
    }
}

package com.dojagy.todaysave.data.entity.value;

public enum LinkType {
    // --- 소셜 미디어 & 커뮤니티 ---
    X_TWITTER("X(트위터)"),
    FACEBOOK("페이스북"),
    INSTAGRAM("인스타그램"),
    THREADS("쓰레드"),
    PINTEREST("핀터레스트"),
    REDDIT("레딧"),
    LINKEDIN("링크드인"),
    NAVER_CAFE("네이버카페"),
    DISCORD("디스코드"),
    HACKER_NEWS("해커뉴스"),

    // --- 콘텐츠 플랫폼 (블로그, 글쓰기) ---
    NAVER_BLOG("네이버 블로그"),
    TISTORY("티스토리"),
    VELOG("벨로그"),
    BRUNCH("브런치스토리"),
    MEDIUM("미디엄"),
    NOTION("노션"),
    DEV_TO("Dev.to"),
    HASHNODE("해시노드"),

    // --- 비디오 & OTT ---
    YOUTUBE("유튜브"),
    NETFLIX("넷플릭스"),
    TWITCH("트위치"),
    VIMEO("비메오"),
    TVING("티빙"),
    WAVVE("웨이브"),
    WATCHA("왓챠"),
    COUPANG_PLAY("쿠팡플레이"),
    DISNEY_PLUS("디즈니플러스"),

    // --- 음악 ---
    YOUTUBE_MUSIC("유튜브뮤직"),
    SPOTIFY("스포티파이"),
    APPLE_MUSIC("애플뮤직"),
    MELON("멜론"),
    GENIE_MUSIC("지니뮤직"),
    BUGS_MUSIC("벅스"),

    // --- 개발 도구 & 플랫폼 ---
    GITHUB("깃허브"),
    GITLAB("깃랩"),
    STACK_OVERFLOW("스택오버플로우"),
    DOCKER_HUB("도커허브"),
    NPM("npm"),
    ATLASSIAN("아틀라시안"), // Jira, Confluence, Bitbucket
    FIGMA("피그마"),
    CANVA("캔바"),

    // --- 교육 & 온라인 강의 ---
    INFLEARN("인프런"),
    FAST_CAMPUS("패스트캠퍼스"),
    CODEIT("코드잇"),
    SPARTA_CODING("스파르타코딩클럽"),
    PROGRAMMERS("프로그래머스"),
    COURSERA("코세라"),
    UDEMY("유데미"),
    UDACITY("유다시티"),
    GEEKSFORGEEKS("긱스포긱스"),
    MDN_DOCS("MDN 웹 문서"),

    // --- 뉴스 & 정보 ---
    NAVER_NEWS("네이버뉴스"),
    DAUM_NEWS("다음뉴스"),
    SURFIT("서핏"),

    // --- 지식 & Q&A ---
    NAVER_KIN("네이버 지식iN"),
    QUORA("쿼라"),

    // --- 채용 & 커리어 ---
    SARAMIN("사람인"),
    JOBKOREA("잡코리아"),
    WANTED("원티드"),
    ROCKETPUNCH("로켓펀치"),

    // --- 쇼핑 & 커머스 (한국) ---
    COUPANG("쿠팡"),
    MUSINSA("무신사"),
    MARKET_KURLY("마켓컬리"),
    DAANGN("당근"),
    NAVER_SHOPPING("네이버쇼핑"),
    ELEVEN_STREET("11번가"),
    GMARKET("G마켓"),
    AUCTION("옥션"),
    SSG("SSG.COM"),
    TODAYS_HOUSE("오늘의집"),

    // --- 도서 & 출판 ---
    YES24("YES24"),
    KYOBO("교보문고"),
    ALADIN("알라딘"),
    RIDIBOOKS("리디북스"),
    AMAZON("아마존"),

    // --- 지도 & 생활 (한국) ---
    NAVER_MAP("네이버 지도"),
    KAKAO_MAP("카카오맵"),
    BAEMIN("배달의민족"),
    YOGIYO("요기요"),

    // --- 여행 & 숙박 ---
    AIRBNB("에어비앤비"),
    AGODA("아고다"),
    BOOKING_COM("부킹닷컴"),
    YANOLJA("야놀자"),
    YEOGI_EOTTAE("여기어때"),

    // --- 클라우드 & 파일 공유 ---
    GOOGLE_DRIVE("구글 드라이브"),
    DROPBOX("드롭박스"),
    ONEDRIVE("원드라이브"),

    // --- 기본값 ---
    GENERAL("일반 링크");

    private final String description;

    LinkType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

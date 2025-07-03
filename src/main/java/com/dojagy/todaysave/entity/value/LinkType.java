package com.dojagy.todaysave.entity.value;

public enum LinkType {
    YOUTUBE("유튜브"),
    INSTAGRAM("인스타그램"),
    NAVER_BLOG("네이버 블로그"),
    VELOG("벨로그"),
    GITHUB("깃허브"),
    SURFIT("서핏"),
    TISTORY("티스토리"),
    THREADS("쓰레드"),
    BRUNCH("브런치스토리"),
    NOTION("노션"),
    MEDIUM("미디엄"),
    LINKEDIN("링크드인"),
    X_TWITTER("X(트위터)"),
    GENERAL("일반 링크"); // 특정되지 않은 모든 링크

    private final String description;

    LinkType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

package com.dojagy.todaysave.util;

import com.dojagy.todaysave.data.dto.content.UrlMetadataDto;
import com.dojagy.todaysave.data.entity.value.LinkType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;

@Component
public class UrlMetadataExtractor {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";

    private static final Map<LinkType, List<String>> DOMAIN_MAP = new LinkedHashMap<>();

    static {
        // --- 소셜 미디어 & 커뮤니티 ---
        DOMAIN_MAP.put(LinkType.X_TWITTER, Arrays.asList("x.com", "twitter.com"));
        DOMAIN_MAP.put(LinkType.FACEBOOK, Arrays.asList("facebook.com", "fb.com"));
        DOMAIN_MAP.put(LinkType.INSTAGRAM, List.of("instagram.com"));
        DOMAIN_MAP.put(LinkType.THREADS, List.of("threads.net"));
        DOMAIN_MAP.put(LinkType.PINTEREST, List.of("pinterest.com"));
        DOMAIN_MAP.put(LinkType.REDDIT, List.of("reddit.com"));
        DOMAIN_MAP.put(LinkType.LINKEDIN, List.of("linkedin.com"));
        DOMAIN_MAP.put(LinkType.NAVER_CAFE, List.of("cafe.naver.com"));
        DOMAIN_MAP.put(LinkType.DISCORD, Arrays.asList("discord.com", "discord.gg"));
        DOMAIN_MAP.put(LinkType.HACKER_NEWS, List.of("news.ycombinator.com"));

        // --- 콘텐츠 플랫폼 (블로그, 글쓰기) ---
        DOMAIN_MAP.put(LinkType.NAVER_BLOG, List.of("blog.naver.com"));
        DOMAIN_MAP.put(LinkType.TISTORY, List.of("tistory.com"));
        DOMAIN_MAP.put(LinkType.VELOG, List.of("velog.io"));
        DOMAIN_MAP.put(LinkType.BRUNCH, List.of("brunch.co.kr"));
        DOMAIN_MAP.put(LinkType.MEDIUM, List.of("medium.com"));
        DOMAIN_MAP.put(LinkType.NOTION, Arrays.asList("notion.so", "notion.site"));
        DOMAIN_MAP.put(LinkType.DEV_TO, List.of("dev.to"));
        DOMAIN_MAP.put(LinkType.HASHNODE, List.of("hashnode.dev"));

        // --- 비디오 & OTT ---
        DOMAIN_MAP.put(LinkType.YOUTUBE, List.of("youtube.com", "youtu.be"));
        DOMAIN_MAP.put(LinkType.NETFLIX, List.of("netflix.com"));
        DOMAIN_MAP.put(LinkType.TWITCH, List.of("twitch.tv"));
        DOMAIN_MAP.put(LinkType.VIMEO, List.of("vimeo.com"));
        DOMAIN_MAP.put(LinkType.TVING, List.of("tving.com"));
        DOMAIN_MAP.put(LinkType.WAVVE, List.of("wavve.com"));
        DOMAIN_MAP.put(LinkType.WATCHA, List.of("watcha.com"));
        DOMAIN_MAP.put(LinkType.COUPANG_PLAY, List.of("coupangplay.com"));
        DOMAIN_MAP.put(LinkType.DISNEY_PLUS, List.of("disneyplus.com"));

        // --- 음악 ---
        DOMAIN_MAP.put(LinkType.YOUTUBE_MUSIC, List.of("music.youtube.com"));
        DOMAIN_MAP.put(LinkType.SPOTIFY, List.of("spotify.com"));
        DOMAIN_MAP.put(LinkType.APPLE_MUSIC, List.of("music.apple.com"));
        DOMAIN_MAP.put(LinkType.MELON, List.of("melon.com"));
        DOMAIN_MAP.put(LinkType.GENIE_MUSIC, List.of("genie.co.kr"));
        DOMAIN_MAP.put(LinkType.BUGS_MUSIC, List.of("bugs.co.kr"));

        // --- 개발 도구 & 플랫폼 ---
        DOMAIN_MAP.put(LinkType.GITHUB, List.of("github.com"));
        DOMAIN_MAP.put(LinkType.GITLAB, List.of("gitlab.com"));
        DOMAIN_MAP.put(LinkType.STACK_OVERFLOW, List.of("stackoverflow.com"));
        DOMAIN_MAP.put(LinkType.DOCKER_HUB, List.of("hub.docker.com"));
        DOMAIN_MAP.put(LinkType.NPM, List.of("npmjs.com"));
        DOMAIN_MAP.put(LinkType.ATLASSIAN, List.of("atlassian.net")); // Jira, Confluence, Bitbucket
        DOMAIN_MAP.put(LinkType.FIGMA, List.of("figma.com"));
        DOMAIN_MAP.put(LinkType.CANVA, List.of("canva.com"));

        // --- 교육 & 온라인 강의 ---
        DOMAIN_MAP.put(LinkType.INFLEARN, List.of("inflearn.com"));
        DOMAIN_MAP.put(LinkType.FAST_CAMPUS, List.of("fastcampus.co.kr"));
        DOMAIN_MAP.put(LinkType.CODEIT, List.of("codeit.kr"));
        DOMAIN_MAP.put(LinkType.SPARTA_CODING, List.of("spartacodingclub.kr"));
        DOMAIN_MAP.put(LinkType.PROGRAMMERS, List.of("programmers.co.kr")); // 프로그래머스 (채용/교육 둘다 포함)
        DOMAIN_MAP.put(LinkType.COURSERA, List.of("coursera.org"));
        DOMAIN_MAP.put(LinkType.UDEMY, List.of("udemy.com"));
        DOMAIN_MAP.put(LinkType.UDACITY, List.of("udacity.com"));
        DOMAIN_MAP.put(LinkType.GEEKSFORGEEKS, List.of("geeksforgeeks.org"));
        DOMAIN_MAP.put(LinkType.MDN_DOCS, List.of("developer.mozilla.org"));

        // --- 뉴스 & 정보 ---
        DOMAIN_MAP.put(LinkType.NAVER_NEWS, List.of("news.naver.com"));
        DOMAIN_MAP.put(LinkType.DAUM_NEWS, List.of("news.daum.net"));
        DOMAIN_MAP.put(LinkType.SURFIT, List.of("surfit.io"));

        // --- 지식 & Q&A ---
        DOMAIN_MAP.put(LinkType.NAVER_KIN, List.of("kin.naver.com"));
        DOMAIN_MAP.put(LinkType.QUORA, List.of("quora.com"));

        // --- 채용 & 커리어 ---
        DOMAIN_MAP.put(LinkType.SARAMIN, List.of("saramin.co.kr"));
        DOMAIN_MAP.put(LinkType.JOBKOREA, List.of("jobkorea.co.kr"));
        DOMAIN_MAP.put(LinkType.WANTED, List.of("wanted.co.kr"));
        DOMAIN_MAP.put(LinkType.ROCKETPUNCH, List.of("rocketpunch.com"));

        // --- 쇼핑 & 커머스 (한국) ---
        DOMAIN_MAP.put(LinkType.COUPANG, Arrays.asList("coupang.com", "link.coupang.com"));
        DOMAIN_MAP.put(LinkType.MUSINSA, List.of("musinsa.com"));
        DOMAIN_MAP.put(LinkType.MARKET_KURLY, List.of("kurly.com"));
        DOMAIN_MAP.put(LinkType.DAANGN, List.of("daangn.com"));
        DOMAIN_MAP.put(LinkType.NAVER_SHOPPING, List.of("shopping.naver.com"));
        DOMAIN_MAP.put(LinkType.ELEVEN_STREET, List.of("11st.co.kr"));
        DOMAIN_MAP.put(LinkType.GMARKET, List.of("gmarket.co.kr"));
        DOMAIN_MAP.put(LinkType.AUCTION, List.of("auction.co.kr"));
        DOMAIN_MAP.put(LinkType.SSG, List.of("ssg.com"));
        DOMAIN_MAP.put(LinkType.TODAYS_HOUSE, List.of("ohou.se"));

        // --- 지도 & 생활 (한국) ---
        DOMAIN_MAP.put(LinkType.NAVER_MAP, List.of("map.naver.com"));
        DOMAIN_MAP.put(LinkType.KAKAO_MAP, Arrays.asList("map.kakao.com", "kko.kakao.com"));
        DOMAIN_MAP.put(LinkType.BAEMIN, List.of("baemin.com"));
        DOMAIN_MAP.put(LinkType.YOGIYO, List.of("yogiyo.co.kr"));
    }

    public UrlMetadataExtractor() {
        WebDriverManager.chromedriver().setup();
    }

    public Optional<UrlMetadataDto> extract(String url) {
        // [변경점 1] 이제 getPageSourceWithSelenium을 호출합니다.
        SeleniumResult seleniumResult = getPageSourceWithSelenium(url);
        if (seleniumResult == null) {
            return Optional.empty();
        }

        try {
            Document doc = Jsoup.parse(seleniumResult.htmlSource(), seleniumResult.finalUrl());

            String uniqueKeyUrl = extractAndValidateCanonicalUrl(doc, seleniumResult.finalUrl());

            return Optional.of(
                    UrlMetadataDto.builder()
                            .url(url)
                            .title(extractTitle(doc))
                            .description(extractMetaTag(doc, "description"))
                            .thumbnailUrl(extractThumbnailUrl(doc))
                            .faviconUrl(extractBestIconUrl(doc, seleniumResult.finalUrl()))
                            .canonicalUrl(uniqueKeyUrl)
                            .linkType(extractLinkType(seleniumResult.finalUrl()))
                            .build()
            );
        } catch (URISyntaxException e) {
            System.err.println("URL 메타데이터 추출 실패: url=" + url + ", error=" + e.getMessage());
            return Optional.empty();
        }
    }

    private SeleniumResult getPageSourceWithSelenium(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=" + USER_AGENT);

        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20)); // 타임아웃을 넉넉하게

            driver.get(url);

            String finalUrl = driver.getCurrentUrl();
            String htmlSource = driver.getPageSource();

            // title이 없는 about:blank 같은 페이지는 실패로 간주
            if (finalUrl.startsWith("about:blank")) {
                return null;
            }

            return new SeleniumResult(finalUrl, htmlSource);
        } catch (Exception e) {
            System.err.println("Selenium으로 페이지 가져오기 실패: url=" + url + ", error=" + e.getMessage());
            return null;
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private String extractAndValidateCanonicalUrl(Document doc, String finalUrl) {
        // 1. 페이지에서 canonical URL을 일단 추출합니다.
        String canonicalUrl = doc.select("link[rel=canonical]").attr("href");

        // 2. canonical URL이 비어있다면, 선택의 여지 없이 finalUrl이 고유 키가 됩니다.
        if (canonicalUrl.isEmpty()) {
            return finalUrl;
        }

        // 3. canonical URL이 존재한다면, "유의미한지" 검사합니다.
        if (isCanonicalUrlMeaningful(canonicalUrl)) {
            // 4. 유의미하다면 -> 이 canonical URL을 고유 키로 사용합니다.
            return canonicalUrl;
        } else {
            // 5. 유의미하지 않다면 (대표 도메인 등) -> finalUrl을 고유 키로 사용합니다. (Fallback)
            return finalUrl;
        }
    }

    private boolean isCanonicalUrlMeaningful(String canonicalUrl) {
        try {
            URI uri = new URI(canonicalUrl);
            String path = uri.getPath();

            // 경로가 없거나(null), 루트('/')이거나, 단순 언어/국가 코드이면 유의미하지 않다고 판단
            // 예: "/", "/ko/", "/en-us/" 같은 경우를 걸러냅니다.
            return path != null && !path.isEmpty() && !path.equals("/") && !path.matches("/[a-z]{2}(-[A-Z]{2})?/?$");

            // 위 필터를 통과했다면 고유한 정보를 담고 있을 가능성이 높습니다.
        } catch (URISyntaxException e) {
            // URL 형식이 잘못된 경우, 유의미하지 않은 것으로 간주하여 안전하게 처리
            return false;
        }
    }

    private String extractTitle(Document doc) {
        String title = extractMetaTag(doc, "og:title");
        if(title.isEmpty()) {
            title = doc.title();
        }
        return title;
    }

    private String extractThumbnailUrl(Document doc) {
        return extractMetaTag(doc, "og:image");
    }

    private String extractBestIconUrl(Document doc, String baseUrl) throws URISyntaxException {
        // 1순위: apple-touch-icon 찾기 (가장 높은 품질일 확률이 높음)
        // 여러개가 있다면 가장 큰 사이즈를 찾는 로직을 추가할 수 있지만, 보통은 하나만으로도 충분히 고품질.
        String bestIconUrl = doc.select("link[rel=apple-touch-icon]").attr("href");
        if (!bestIconUrl.isEmpty()) {
            return resolveUrl(baseUrl, bestIconUrl);
        }

        // 2순위: rel="icon" 중에서 가장 큰 사이즈의 아이콘 찾기
        int maxIconSize = 0;
        bestIconUrl = "";

        // rel="icon" 또는 rel="shortcut icon" 태그들을 모두 가져옵니다.
        for (org.jsoup.nodes.Element iconElement : doc.select("link[rel~=(?i)icon]")) {
            try {
                // "sizes" 속성에서 크기를 파싱합니다. (예: "192x192")
                String sizesAttr = iconElement.attr("sizes");
                if (sizesAttr.contains("x")) {
                    int size = Integer.parseInt(sizesAttr.split("x")[0].trim());
                    if (size > maxIconSize) {
                        maxIconSize = size;
                        bestIconUrl = iconElement.attr("href");
                    }
                } else {
                    // sizes 속성이 없는 경우, 일단 후보로 저장해둡니다.
                    if (bestIconUrl.isEmpty()) {
                        bestIconUrl = iconElement.attr("href");
                    }
                }
            } catch (NumberFormatException e) {
                // 크기 파싱 실패 시, 일단 후보로 저장
                if (bestIconUrl.isEmpty()) {
                    bestIconUrl = iconElement.attr("href");
                }
            }
        }

        if (!bestIconUrl.isEmpty()) {
            return resolveUrl(baseUrl, bestIconUrl);
        }

        // 최후의 수단: /favicon.ico 를 직접 요청
        return resolveUrl(baseUrl, "/favicon.ico");
    }

    // URL을 해석하는 헬퍼 함수 (상대경로 -> 절대경로)
    private String resolveUrl(String baseUrl, String relativeUrl) throws URISyntaxException {
        if (relativeUrl == null || relativeUrl.isEmpty()) {
            return "";
        }
        return new URI(baseUrl).resolve(relativeUrl).toString();
    }

    private LinkType extractLinkType(String url) {
        try {
            String host = new URI(url).getHost();
            if (host == null) {
                return LinkType.GENERAL;
            }
            host = host.toLowerCase();

            // [핵심 변경] Map을 순회하며 새로운 매칭 함수를 사용합니다.
            for (Map.Entry<LinkType, List<String>> entry : DOMAIN_MAP.entrySet()) {
                for (String domain : entry.getValue()) {
                    if (isHostMatchingDomain(host, domain)) {
                        return entry.getKey();
                    }
                }
            }

            // 모든 조건에 맞지 않으면 일반 링크로 처리합니다.
            return LinkType.GENERAL;
        } catch (URISyntaxException e) {
            // URL 형식이 잘못된 경우 일반 링크로 처리합니다.
            return LinkType.GENERAL;
        }
    }

    private boolean isHostMatchingDomain(String host, String domain) {
        // 1. 호스트가 해당 도메인으로 끝나는지 확인 (기본 필터)
        if (!host.endsWith(domain)) {
            return false;
        }

        // 2. 호스트와 도메인의 길이가 같은 경우 (e.g., host="youtube.com", domain="youtube.com")
        //    이것은 정확한 일치이므로 true 입니다.
        if (host.length() == domain.length()) {
            return true;
        }

        // 3. 호스트가 더 긴 경우 (서브도메인 가능성)
        //    (e.g., host="www.youtube.com", domain="youtube.com")
        //    도메인 바로 앞의 문자가 '.' 인지 확인하여 "my-youtube.com" 같은 경우를 걸러냅니다.
        if (host.charAt(host.length() - domain.length() - 1) == '.') {
            return true;
        }

        // 위의 모든 조건에 해당하지 않으면 잘못된 매칭입니다.
        return false;
    }

    private String extractMetaTag(Document doc, String attribute) {
        String content = doc.select("meta[name=" + attribute + "]").attr("content");
        if(content.isEmpty()) {
            content = doc.select("meta[property=" + attribute + "]").attr("content");
        }
        return content;
    }

    public record SeleniumResult(
            String finalUrl,
            String htmlSource
    ){}
}

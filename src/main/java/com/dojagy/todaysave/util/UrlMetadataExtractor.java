package com.dojagy.todaysave.util;

import com.dojagy.todaysave.dto.content.UrlMetadataDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
public class UrlMetadataExtractor {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36";

    public Optional<UrlMetadataDto> extract(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(5000)
                    .get();

            return Optional.of(
                    UrlMetadataDto.builder()
                            .url(url)
                            .title(extractTitle(doc))
                            .description(extractMetaTag(doc, "description"))
                            .thumbnailUrl(extractThumbnailUrl(doc))
                            .faviconUrl(extractFaviconUrl(doc, url))
                            .canonicalUrl(extractCanonicalUrl(doc, url))
                            .build()
            );
        } catch (IOException | URISyntaxException e) {
            System.err.println("URL 메타데이터 추출 실패: url=" + url + ", error=" + e.getMessage());
            return Optional.empty();
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
        String thumbnailUrl = extractMetaTag(doc, "og:image");
        if(thumbnailUrl.isEmpty()) {
            thumbnailUrl = extractMetaTag(doc, "twitter:image");
        }
        return thumbnailUrl;
    }

    private String extractCanonicalUrl(Document doc, String originalUrl) {
        String canonicalUrl = doc.select("link[rel=canonical]").attr("href");
        return canonicalUrl.isEmpty() ? originalUrl : canonicalUrl;
    }

    private String extractFaviconUrl(Document doc, String baseUrl) throws URISyntaxException {
        String faviconUrl = doc.select("link[rel~=(?i)icon]").attr("href");
        if(faviconUrl.isEmpty()) {
            return new URI(baseUrl).getScheme() + "://" + new URI(baseUrl).getHost() + "/favicon.ico";
        }

        return new URI(baseUrl).resolve(faviconUrl).toString();
    }

    private String extractMetaTag(Document doc, String attribute) {
        String content = doc.select("meta[name=" + attribute + "]").attr("content");
        if(content.isEmpty()) {
            content = doc.select("meta[property=" + attribute + "]").attr("content");
        }
        return content;
    }
}

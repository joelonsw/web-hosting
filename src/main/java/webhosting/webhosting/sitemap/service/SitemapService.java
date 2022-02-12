package webhosting.webhosting.sitemap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webhosting.webhosting.hosting.domain.HostingFileRepository;
import webhosting.webhosting.sitemap.domain.Sitemap;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SitemapService {

    private static final String BASE_URL = "https://easy-deploy.kr/";
    private static final String BEGIN_DOC = "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
    private static final String END_DOC = "</urlset>";
    private static final String CHANGE_FREQ_ALWAYS = "always";
    private static final String CHANGE_FREQ_HOURLY = "hourly";
    private static final String CHANGE_FREQ_DAILY = "daily";
    private static final String CHANGE_FREQ_WEEKLY = "weekly";
    private static final String CHANGE_FREQ_MONTHLY = "monthly";
    private static final String CHANGE_FREQ_YEARLY = "yearly";
    private static final String CHANGE_FREQ_NEVER = "never";

    private final HostingFileRepository hostingFileRepository;

    public String getSitemap() {
        Date now = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(BEGIN_DOC);
        sb.append(new Sitemap(BASE_URL, now, CHANGE_FREQ_MONTHLY, "1.0"));
        for (String pageName : hostingFileRepository.findAllPageNames()) {
            sb.append(new Sitemap(BASE_URL + pageName, now, CHANGE_FREQ_MONTHLY, "0.7"));
        }
        sb.append(END_DOC);
        return sb.toString();
    }
}

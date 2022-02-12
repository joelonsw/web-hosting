package webhosting.webhosting.sitemap.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sitemap {

    private static final SimpleDateFormat SITE_MAP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final String location;
    private final Date lastModified;
    private final String changeFrequency;
    private final String priority;

    public Sitemap(String location, Date lastModified, String changeFrequency, String priority) {
        this.location = location;
        this.lastModified = lastModified;
        this.changeFrequency = changeFrequency;
        this.priority = priority;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<url>");
        sb.append("<loc>").append(location).append("</loc>");
        sb.append("<lastmod>").append(SITE_MAP_DATE_FORMAT.format(lastModified)).append("</lastmod>");
        sb.append("<changefreq>").append(changeFrequency).append("</changefreq>");
        sb.append("<priority>").append(priority).append("</priority>");
        sb.append("</url>");
        return sb.toString();
    }
}

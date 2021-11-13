package webhosting.webhosting.hosting.domain;

import webhosting.webhosting.hosting.service.util.FileNameConverter;

public class HostingFileFixture {

    protected final String pageName = "pageName";
    protected final String pageName2 = "pageName2";
    protected final String pageName3 = "pageName3";

    protected final String htmlServerPath = FileNameConverter.generateServerFilePath(pageName, "index.html");
    protected final String cssServerPath1 = FileNameConverter.generateServerFilePath(pageName, "style1.css");
    protected final String cssServerPath2 = FileNameConverter.generateServerFilePath(pageName, "style2.css");
    protected final String jsServerPath1 = FileNameConverter.generateServerFilePath(pageName, "app1.js");
    protected final String jsServerPath2 = FileNameConverter.generateServerFilePath(pageName, "app2.js");

    protected final HostingFile htmlFile = new HostingFile(pageName, htmlServerPath, FileType.HTML);
    protected final HostingFile cssFile1 = new HostingFile(pageName, cssServerPath1, FileType.CSS);
    protected final HostingFile cssFile2 = new HostingFile(pageName, cssServerPath2, FileType.CSS);
    protected final HostingFile jsFile1 = new HostingFile(pageName, jsServerPath1, FileType.JS);
    protected final HostingFile jsFile2 = new HostingFile(pageName, jsServerPath2, FileType.JS);

    protected final String pageName2HtmlServerPath = FileNameConverter.generateServerFilePath(pageName2, "index.html");
    protected final HostingFile pageName2HtmlFile = new HostingFile(pageName2, pageName2HtmlServerPath, FileType.HTML);

    protected final String pageName3HtmlServerPath = FileNameConverter.generateServerFilePath(pageName3, "index.html");
    protected final HostingFile pageName3HtmlFile = new HostingFile(pageName3, pageName3HtmlServerPath, FileType.HTML);
}

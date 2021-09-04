package webhosting.webhosting.hosting.domain;

import webhosting.webhosting.hosting.service.util.FileNameConverter;

public class HostingFileFixture {

    public final String pageName = "page";

    public final String htmlServerPath = FileNameConverter.generateServerFilePath(pageName, "index.html");
    public final String cssServerPath1 = FileNameConverter.generateServerFilePath(pageName, "style1.css");
    public final String cssServerPath2 = FileNameConverter.generateServerFilePath(pageName, "style2.css");
    public final String jsServerPath1 = FileNameConverter.generateServerFilePath(pageName, "app1.js");
    public final String jsServerPath2 = FileNameConverter.generateServerFilePath(pageName, "app2.js");

    public final HostingFile htmlFile = new HostingFile(pageName, htmlServerPath, FileType.HTML);
    public final HostingFile cssFile1 = new HostingFile(pageName, cssServerPath1, FileType.CSS);
    public final HostingFile cssFile2 = new HostingFile(pageName, cssServerPath2, FileType.CSS);
    public final HostingFile jsFile1 = new HostingFile(pageName, jsServerPath1, FileType.JS);
    public final HostingFile jsFile2 = new HostingFile(pageName, jsServerPath2, FileType.JS);

}

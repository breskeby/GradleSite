package com.breskeby.gradle.site

import org.apache.maven.doxia.module.apt.AptParser
import org.apache.maven.doxia.module.xhtml.XhtmlSinkFactory

/**
 * @author: Rene
 */
class AptSiteRenderer extends DefaultSiteRenderer {

    File aptSource

    public AptSiteRenderer(SiteModel model, File aptFile) {
        super(model)
        this.aptSource = aptFile;
    }

    protected void renderContent() {
        builder.mkp.yieldUnescaped(getAptSectionAsHtml(aptSource))
    }

    String getAptSectionAsHtml(File aptFile) {
        AptParser parser = new AptParser();
        OutputStream outputStream = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b);
            }

            public String toString() {
                return this.string.toString();
            }
        };
        def sink = new XhtmlSinkFactory().createSink(outputStream)

        parser.parse(aptFile.newReader(), sink)
        String aptHtmlSite = outputStream.toString()
        def matches = aptHtmlSite =~ /(?ms)<body\b[^>]*>(.*?)<\/body>/
        matches[0][1]

        //aptHtmlSite.substring(aptHtmlSite.indexOf("<body>"), aptHtmlSite.indexOf("</body>"))
    }
}

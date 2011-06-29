package com.breskeby.gradle.site

import org.apache.maven.doxia.module.apt.AptParser
import org.apache.maven.doxia.module.xhtml.XhtmlSinkFactory
import org.apache.maven.doxia.module.xdoc.XdocParser

/**
 * Created by IntelliJ IDEA.
 * @author: Rene
 */
class XDocsSiteRenderer extends DefaultSiteRenderer {

    File xdocSource

    public XDocsSiteRenderer(SiteModel model, File xdocFile) {
        super(model)
        this.xdocSource = xdocFile;
    }

    protected void renderContent() {
        builder.mkp.yieldUnescaped(getXdocSectionAsHtml(xdocSource))
    }

    String getXdocSectionAsHtml(File xdocFile) {
        XdocParser parser = new XdocParser();
        parser.setValidate(false)
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

        parser.parse( xdocFile.newReader(), sink)
        outputStream.toString()
    }
}
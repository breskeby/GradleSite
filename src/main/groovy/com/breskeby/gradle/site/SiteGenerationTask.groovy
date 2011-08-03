package com.breskeby.gradle.site

import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

import org.apache.commons.io.IOUtils
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.logging.ProgressLoggerFactory
import org.gradle.logging.ProgressLogger
import org.gradle.api.internal.ConventionTask

/**
 * Created by IntelliJ IDEA.
 * @author: Rene
 */
class SiteGenerationTask extends ConventionTask {

    @Nested SiteModel html = new SiteModel()

    /**
     * The directory where we expect the maven based site definition
     * */
	@InputDirectory
    File sourceDir

    /**
     * the directory we want the generated html content in
     * */
    @OutputDirectory
    File targetDir


    @TaskAction
    void generate() {

        ProgressLoggerFactory fac = services.get(ProgressLoggerFactory.class)
        ProgressLogger progressLogger = fac.newOperation("gradle.site")

        progressLogger.description = "informs about the site generation progress"
        progressLogger.started()

        // convert apt files to html
        new File(getSourceDir(), "apt").eachFileMatch(~/.*\.apt/) {
            AptSiteRenderer aptRenderer = new AptSiteRenderer(getHtml(), it)
            def outputFileName = it.name.replace("apt", "html")
			progressLogger.progress("rendering $outputFileName")
            aptRenderer.render(new File(getTargetDir(), outputFileName))
        }

        // convert xdocs to hmtl
        new File(getSourceDir(), "xdoc").eachFileMatch(~/.*\.xml/) {
            XDocsSiteRenderer aptRenderer = new XDocsSiteRenderer(getHtml(), it)
            def outputFileName = it.name.replace("xml", "html")
			progressLogger.progress("rendering $outputFileName")
            aptRenderer.render(new File(getTargetDir(), outputFileName))
        }

        progressLogger.completed()
        copyStyleSheet()
    }

    void copyStyleSheet() {
        File cssFile = new File(getTargetDir(), "style.css");
        OutputStream outputStream = new FileOutputStream(cssFile);
        try {
            InputStream cssResource = getClass().getResourceAsStream("/site.css");
            try {
                IOUtils.copy(cssResource, outputStream);
            } finally {
                cssResource.close();
            }
        } finally {
            outputStream.close();
        }
    }


    public void setSourceDir(File sourceDir){
        this.sourceDir = sourceDir;
        this.html.siteXmlPath = new File(sourceDir, "site.xml")
    }
}
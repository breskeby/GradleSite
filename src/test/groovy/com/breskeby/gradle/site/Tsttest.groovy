package com.breskeby.gradle.site;
import spock.lang.Specification
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import org.junit.Rule
import org.junit.rules.TemporaryFolder

/**
 * @Author: Rene
 * Email: rene@breskeby.com
 */
class Tsttest extends Specification {

    Project testProject = ProjectBuilder.builder().build()
    SiteGenerationTask cut = testProject.tasks.add("site", SiteGenerationTask)

    @Rule TemporaryFolder tempFolder = new TemporaryFolder()


    def "copyStyleSheet creates a style.css in target dir"() {
      when:
          cut.targetDir = tempFolder.newFolder("targetDir")
          cut.copyStyleSheet()
      then:
          new File(cut.targetDir, "style.css").exists()
    }

}
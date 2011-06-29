package com.breskeby.gradle.site

import spock.lang.Specification
import org.junit.rules.TemporaryFolder
import org.junit.Rule
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project

/**
 * Created by IntelliJ IDEA.
 * @author: Rene
 */

class SiteGenerationTaskTest extends Specification{

    @Rule TemporaryFolder tempFolder = new TemporaryFolder()

    def testProject = ProjectBuilder.builder().build();
    def cut = testProject.tasks.add("site", SiteGenerationTask.class)

    def "copyStyleSheet() copies a style.css file in the target dir"(){
        when:
            cut.targetDir = tempFolder.newFolder("siteDist")
            cut.copyStyleSheet()
        then:
            new File(cut.targetDir, "style.css").exists()
    }
}

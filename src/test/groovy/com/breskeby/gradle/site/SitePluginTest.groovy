package com.breskeby.gradle.site

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

class SitePluginTest extends Specification{

	private final Project project = ProjectBuilder.builder().build()
	private final SitePlugin plugin = new SitePlugin()

    def "Applying the Site plugin does add 'site' tasks"() {
	    when:
	    plugin.apply(project)
	    then:
	    project.tasks.all.size() == 1
		project.tasks.getByName("site") != null
	}
}

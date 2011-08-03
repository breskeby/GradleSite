package com.breskeby.gradle.site

import org.gradle.api.Plugin
import org.gradle.api.Project

class SitePlugin implements Plugin<Project> {
    public static final String SITE_PLUGIN_NAME = 'site'
    SiteConvention conv
    Project project


    public void apply(final Project project) {
        this.project = project
        addSiteConvention()

        SiteGenerationTask siteTask = project.tasks.add("site", SiteGenerationTask.class)
        siteTask.sourceDir = project.file("src/site")
        siteTask.conventionMapping.targetDir = { conv.deployDir }
        siteTask.html.title = project.name
        siteTask.doLast {
            getProject().copy{
                into(getTargetDir())
                from(new File(getSourceDir(), "resources"))
            }
        }
    }

    void addSiteConvention() {
        conv = new SiteConvention(project);

        project.convention.plugins.site = conv;
    }
}
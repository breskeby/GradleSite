package com.breskeby.gradle.site

import org.gradle.api.Project

/**
 * @Author: Rene
 * Email: rene@breskeby.com
 */
class SiteConvention {
     File deployDir

     public SiteConvention(Project project){
         deployDir = new File(project.buildDir, "site")
     }

}

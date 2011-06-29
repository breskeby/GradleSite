package com.breskeby.gradle.site

import org.gradle.api.tasks.Input

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 */
class SiteModel{

    // title shown in browsers title section
    // customize by build file
    @Input String title

    // title on the html page
	//lazy initialized when site.xml is parsed
    private String headerTitle = null

    // sections referred in sites menu bar
    //lazy initialized when site.xml is parsed
	private def menu = [:]

    // path to the site.xml file used as template for menu items
    File siteXmlPath

    def getMenu(){
        if(menu.isEmpty()){
            parseSiteXml(this.siteXmlPath)
        }
        menu
    }

    // read from site.xml
    def getHeaderTitle(){
        if(!headerTitle){
            parseSiteXml(siteXmlPath)
        }
        headerTitle
    }

    def void parseSiteXml(File sitexml) {
        def project = new XmlSlurper().parse(sitexml)
        headerTitle = project.bannerLeft.src
        //parse sections
        for (menu in project.body.menu) {
            def items = [:]
            for (item in menu.item) {
               items << [(item.@name.toString()): item.@href.toString()]
            }
            this.menu << [(menu.@name.toString()): items]
        }
    }


}

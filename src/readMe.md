# JAVA 11

 JAVA 11 and groovy 2.4.12 requires some IDE settings:
 https://www.logicbig.com/tutorials/misc/groovy/intellij.html

 Also you have to add JAXB dependencies if you are using Groovy 2.5.3 + Java 11 (also check out Java 11 related change). Groovy comes with extra JAXB Jars so we can add them. Open 'Project Structure' dialog, then select 'Dependencies' tab and add the dependencies as shown:
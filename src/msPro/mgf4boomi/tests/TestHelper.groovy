package msPro.mgf4boomi.tests

import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput

class TestHelper {
    static void printTextDocuments(DataContext dataContext) {
        int docNo = 0
        for (def doc in dataContext.Documents) {
            println("Doc[${docNo++}]=" + doc.toString())
        }
    } 
    
    static void printJsonDocuments(DataContext dataContext) {
        int docNo = 0
        for (def doc in dataContext.Documents) {
            println("Doc[${docNo++}]=" + JsonOutput.prettyPrint(doc.toString()))
        }
    }
}
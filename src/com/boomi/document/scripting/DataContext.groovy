package com.boomi.document.scripting

import msPro.mgf4boomi.Document
import groovy.transform.TypeChecked

/**
 * Represent a Boomi-Platform Scripting context.
 *  <br/>
 * @see
 *  <a href="https://help.boomi.com/bundle/integration/page/c-atm-Scripting_context.html">Boomi-Platform Scripting-Context</a>
 */
@TypeChecked
class DataContext {
    /** The incoming and outgoing documents. */
    private List<Document> _inputDocuments = null
    List<Document> _outputDocuments = null

    /**
     * Private constructor.
     */
    private DataContext(List<Document> inputDocuments) { _inputDocuments = inputDocuments }
    
    
    
    // http://docs.groovy-lang.org/docs/groovy-2.4.4/html/groovy-jdk/java/io/InputStream.html
    // * https://community.boomi.com/s/question/0D51W00006As1qC/recursive-function-groovy

    /**
     * Public factory method to create a DataContext with a list fo documents.
     * @param inputDocuments
     * @return DataContext
     */
    static DataContext withDocuments(List<Document> inputDocuments) {
         return new DataContext(inputDocuments)
    }

    /**
     * Create a new Context with no document.
     */
    static DataContext empty() {
        return new DataContext([])
    }




    /**
     * Provides the count of the number of Documents
     */
    int getDataCount() {
        assert _inputDocuments != null, "DataContext not initialized!"
        return _inputDocuments.size()
    }


    /**
     * Retrieve the Document data as an InputStream object.
     */
    InputStream getStream(int docNo) {
        assert _inputDocuments != null, "DataContext not initialized!"
        // Always reset s single document's stream after accessing it.
        // Otherwise, the document's stream position (pointer) 
        // will point to the end of the stream after reading it.
        // Not sure if this is the Boomi platform behaviour!?
        _inputDocuments[docNo]._stream.reset()
        return _inputDocuments[docNo]._stream
    }

    
    /**
     * After performing your custom logic, you need to convert the data back
     * into an InputStream and store it to the dataContext to pass to the next
     * Process step. The number of InputStreams you store will be the number of
     * Documents passed to the next step.
     */
    void storeStream(InputStream is, Properties props) {
        if( _outputDocuments == null) _outputDocuments = []
        
        /*
            Groovy Goodness: Check for Object Equality
            Groovy overloads the == operator and maps it to the equals() method.
            This is very different from Java, so when developers are switching back and forth between Groovy and
            Java mistakes are bound to happen. In Java we use the == operator to see if variables are referring
            to the same object instance. In Groovy we use the == operator to see if two objects are the same,
            in Java we would use the equals() method for this. To test if two variables are referring to the same
            object instance in Groovy >>>>>>>>>>> we use the is() method. <<<<<<<<<<<<<<<<<<<<<<<
            The != operator is also overloaded and maps to the !equals() statement.
            https://blog.mrhaki.com/2009/09/groovy-goodness-check-for-object.html#:~:text=In%20Groovy%20we%20use%20the,overloaded%20and%20maps%20to%20the%20!
         */
        _outputDocuments.add( new Document( is, props ))
    }



    /**
     * Retrieve the Document's properties a Properties object. This includes
     * Connector tracked properties, Meta Information properties, as well as
     * User Defined Document Properties.
     */
    Properties getProperties(int docNo) {
        assert _inputDocuments != null, "DataContext not initialized!"
        return _inputDocuments[docNo].DocumentProperties
    }
}

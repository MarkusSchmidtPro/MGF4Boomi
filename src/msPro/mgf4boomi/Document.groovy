package msPro.mgf4boomi

import groovy.transform.TypeChecked


/**
 * Framework internal helper class 
 *  that represents a document stream with its properties (DDP).
 */
@TypeChecked
class Document {
    public static final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    final public InputStream _stream
    final public Properties _dynamicDocumentProperties

    Document(InputStream stream, Properties props ) {
        assert props!= null
        _stream = stream
        _dynamicDocumentProperties = props
    }
    
    // Document from text, no properties
    static Document fromText(String document, Map<String, String> props = null) {

        final stream = new ByteArrayInputStream(document.getBytes("UTF-8"))
        final dynamicDocumentProperties = new Properties()
        
        props?.each { 
            dynamicDocumentProperties.setProperty( userDefinedPropertyBase + it.key, it.value) 
        }   
        return new Document(stream, dynamicDocumentProperties)
    }

    static Document fromFile(String filePath, Map<String, String> props = null) {
        File f = new File(filePath)
        assert f.exists(), "Test data file (${filePath}) does not exist!"
        return fromText(f.getText(), props)
    }

    String toString() { return _stream.getText("UTF-8") }
}

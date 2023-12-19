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

    public Document(InputStream stream, Map<String, String> props = null) {
        _stream = stream
        _dynamicDocumentProperties = new Properties()
        if( props == null) return
        
        for (def p in props) _dynamicDocumentProperties.setProperty( userDefinedPropertyBase + p.key, p.value)
    }

    // Document from text, no properties
    static Document fromText(String document, Map<String, String> props = null) {
        return new Document(new ByteArrayInputStream(document.getBytes("UTF-8")), props)
    }

    static Document fromFile(String filePath, Map<String, String> props = null) {
        File f = new File(filePath)
        assert f.exists(), "Test data file (${filePath}) does not exist!"
        return fromText(f.getText(), props)
    }

    String toString() { return _stream.getText("UTF-8") }
}

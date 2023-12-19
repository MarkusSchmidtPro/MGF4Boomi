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

    public Document(InputStream stream, Properties props = null) {
        _stream = stream
        _dynamicDocumentProperties = props != null ? props : new Properties()
    }

    // Document from text, no properties
    static Document fromText(String document, Properties props = null) {
        return new Document(new ByteArrayInputStream(document.getBytes("UTF-8")), props)
    }

    static Document fromFile(String filePath, Properties props = null) {
        File f = new File(filePath)
        assert f.exists(), "Test data file (${filePath}) does not exist!"
        return fromText(f.getText(), props)
    }

    String toString() { return _stream.getText("UTF-8") }
}

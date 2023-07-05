package msPro.mgf4boomi

import groovy.transform.TypeChecked


/**
 * DataContext document helper class.
 * Used to simulate a Document.
 */
@TypeChecked
class Document {
    final private InputStream _stream

    Document(InputStream stream, Properties props) {
        _stream = stream
        DocumentProperties = props
    }
    
    public static final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

    final public Properties DocumentProperties
    
    // Document from text, no properties
    static Document withoutProperties(String document) {
        return new Document(new ByteArrayInputStream(document.getBytes("UTF-8")), new Properties())
    }

    static Document fromFile(String filePath, Properties props=null) {
        File f = new File(filePath)
        assert f.exists(), "Test data file (${filePath}) does not exist!"
        //return withoutProperties(f.text)
        return new Document(new ByteArrayInputStream(f.text.getBytes("UTF-8")), props!=null ? props : new Properties())
    }

  
}

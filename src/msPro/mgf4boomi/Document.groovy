package msPro.mgf4boomi

import groovy.transform.TypeChecked


/**
 * DataContext document helper class.
 * Used to simulate a Document.
 */
@TypeChecked
class Document {
    // Do not access these properties directly,
    // always use DataContext.
    final public InputStream _stream
    final public Properties _docProps

    public static HashSet<String> wellKnownPropertyNames = wellKnownPropertyNames

    public static final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

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

    Document(InputStream stream, Properties props) {
        _stream = stream
        _docProps = props
        
        //HashMap<String, String> dynamicDocumentProperties
        for (def entry in props) {
//            if (!entry.key.startsWith(userDefinedPropertyBase))
//                throw new Exception('Dynamic Document Property name must start with ' + userDefinedPropertyBase)
//            //_docProps.setProperty(entry.key, entry.value)
//        }
        }
    }

    /*
        HashMap<String, String> dynamicDocumentProperties
        for (def entry in dynamicDocumentProperties) {
            if (!entry.key.startsWith(userDefinedPropertyBase))
                throw new Exception('Dynamic Document Property name must start with ' + userDefinedPropertyBase)
            _docProps.setProperty(entry.key, entry.value)
        }
    }
        */
}

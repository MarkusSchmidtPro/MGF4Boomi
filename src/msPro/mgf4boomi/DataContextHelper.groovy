package msPro.mgf4boomi

import com.boomi.document.scripting.DataContext
import groovy.transform.TypeChecked

/** Provides functionality to easily access a DataContext during testing.
 */
@TypeChecked
class DataContextHelper {

    final DataContext _dataContext
    DataContextHelper(DataContext dataContext){
        _dataContext = dataContext
    }

    int getOutDocumentCount() {
        return _dataContext._outputDocuments == null ? 0 : _dataContext._outputDocuments.size()
    }

    String getOutDocumentText(int docNo) {
        _dataContext._outputDocuments[docNo]._stream.reset()
        InputStream documentStream = _dataContext._outputDocuments[docNo]._stream
        return documentStream.getText("UTF-8")
    }

    Properties getOutDocumentProperties(int docNo) {
        Properties originalProperties = _dataContext._outputDocuments[docNo]._docProps
        // remove the trailing Boomi user defined property name
        int firstIndex = Document.userDefinedPropertyBase.length()
        Properties results = new Properties()
        for( String k in originalProperties.keys()){
            // remove trailing string
            String docPropertyName = k.substring(firstIndex)
            results.setProperty( docPropertyName, originalProperties.getProperty(k))
        }
        return results
    }
}

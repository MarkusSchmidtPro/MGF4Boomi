package msPro.mgf4boomi

import com.boomi.document.scripting.DataContext
import com.boomi.execution.ExecutionUtil
import groovy.transform.TypeChecked
import org.codehaus.groovy.control.CompilerConfiguration

@TypeChecked
class ProcessScript{
    static final String SCRIPT_BASE_DIR = "../MyScript/src/processScript/"
    private final File _scriptFile

    ProcessScript(String fileName, String baseDir = SCRIPT_BASE_DIR){
        _scriptFile = new File(baseDir + fileName)
        if (!_scriptFile.exists()) throw new FileNotFoundException("File '${_scriptFile.getCanonicalPath()}' not found.")
    }

    DataContextHelper run(ExecutionUtilContexts processExecutionContext, DataContext dataContext)
    {
        assert processExecutionContext != null
        assert dataContext != null
        assert dataContext.dataCount > 0, "Cannot run script on DataContext without documents"
        ExecutionUtil.initialize(processExecutionContext)

        // All values which are set on the Binding are directly available to the script.
        // binding.dataContext = dataContext;

        // https://docs.groovy-lang.org/latest/html/documentation/type-checking-extensions.html
        // 1.3. A DSL for type checking
        // see also: http://docs.groovy-lang.org/docs/groovy-2.4.8/html/gapi/org/codehaus/groovy/control/customizers/ASTTransformationCustomizer.html
        def config = new CompilerConfiguration()
        // Does not work yet, to support type check on the script
        //        config.addCompilationCustomizers(
        //                new ASTTransformationCustomizer(TypeChecked
        //                , extensions:['boomiExtensions.groovy']
        //                ));

        GroovyShell shell = new GroovyShell(config)
        Script script = shell.parse(_scriptFile)
        Binding binding = new Binding()
        binding.setVariable('dataContext', dataContext)
        script.setBinding(binding)
        script.run()

        // Do some process script POST execution checks
        assert dataContext.dataCount != 0, 'Process script should return one or more documents!'
        return new DataContextHelper(dataContext)
    }
}
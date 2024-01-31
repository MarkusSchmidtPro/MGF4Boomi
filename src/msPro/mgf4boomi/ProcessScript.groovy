package msPro.mgf4boomi

import com.boomi.document.scripting.DataContext
import com.boomi.execution.ExecutionUtil
import groovy.transform.TypeChecked
import org.codehaus.groovy.control.CompilerConfiguration

@TypeChecked
class ProcessScript {
    public static final String SCRIPT_BASE_DIR = "src/processScript/"   // Current project root
    private final File _scriptFile

    /**
     * Create a new processScript instance for testing.
     * <h3>Example</h3/> 
     * <code>final ProcessScript _testScript = 
     *      new ProcessScript('processProperties/psgProperties_DPP.groovy')`</code>
     *      
     * @param fileName A filename relative to @baseDir + @relativeDir.
     * @param relativeDir a [baseDir] relative directory.
     * @param baseDir  The script base dir, default: [SCRIPT_BASE_DIR].
     */
    ProcessScript(String fileName, String relativeDir = "", String baseDir = SCRIPT_BASE_DIR) {
        
        if( !baseDir.endsWith("/")) baseDir += "/"
        baseDir = (new File(baseDir, relativeDir)).canonicalPath
        if( !baseDir.endsWith("/")) baseDir += "/"
        _scriptFile = new File(baseDir + fileName)
        if (!_scriptFile.exists()) throw new FileNotFoundException("File '${_scriptFile.getCanonicalPath()}' not found.")
    }


    /** Run a Boomi Process Script using the provided contexts.
     * 
     * The process script filename was provided in the constructor.
     * 
     * @param dataContext Provide DataContext.create( <documents> )
     * @param executionContexts
     * @return
     */
    DataContext run(DataContext dataContext, ExecutionContexts executionContexts = null) {
        assert dataContext != null
        ExecutionUtil.fw_initialize( executionContexts)

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
        return dataContext
    }
}
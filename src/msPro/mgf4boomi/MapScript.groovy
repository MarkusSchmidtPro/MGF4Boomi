package msPro.mgf4boomi

import com.boomi.document.scripting.DataContext
import com.boomi.execution.ExecutionUtil
import groovy.transform.TypeChecked

/** A proxy class to execute a plain Boomi-Platform Groovy Script.
 *
 * A Boomi-Platform script normally runs in the context of the Boomi-Platform.<br/>
 * The Boomi-Platform provides script contexts to the script so that a script can
 * access and modify the process- and document context during run-time. <i>Map</i>
 * scripts have <i>Input</i> parameters provided by the Boomi-Platform, and those
 * scripts populate a set of predefined <i>Output</i> parameters which can be used
 * in later steps of the execution process.<br/>
 * During the development process using the IDE there are no Boomi-Platform contexts.
 * The <i>ScriptRunner</i> allows the execution of a script while providing
 * the necessary contexts.<br/>
 * <b>Please notice:</b> The {@link DataContext}, the {@link ExecutionContexts},
 * as well as the Input parameters must be provided by execution host.
 */
@TypeChecked
class MapScript {
    static final String SCRIPT_BASE_DIR = '../MyScript/src/MapScript/'

    private final File _scriptFile

    /** Create a new instance.
     *
     * @param scriptFilePath The file that contains the plain Boomi Groovy Script code
     *                          as it will be copy & pasted.
     *
     * <pre>{@code
     ScriptRunner script = new ScriptRunner("src/app/scripts/helloWorld.groovy" );
     // ...
     script.RunTest( new ProcessExecutionContext(), dc);}
     * </pre>
     */
    MapScript(String fileName, String baseDir = SCRIPT_BASE_DIR) {
        _scriptFile = new File(baseDir + fileName)
        if (!_scriptFile.exists()) throw new FileNotFoundException("File '${_scriptFile.getCanonicalPath()}' not found.")
    }

    Map<String, Object> run(ExecutionContexts processExecutionContext, Map inputs) {
        return run(processExecutionContext, new Binding(inputs))
    }

    Map<String, Object> run(ExecutionContexts processExecutionContext, Binding binding) {
        assert (processExecutionContext != null)
        ExecutionUtil.fw_initialize(processExecutionContext)
        GroovyShell shell = new GroovyShell(binding)
        shell.evaluate(_scriptFile)
        return binding.variables
    }
}


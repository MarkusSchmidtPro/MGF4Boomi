package com.boomi.execution

import groovy.transform.TypeChecked
import msPro.mgf4boomi.ExecutionContexts

import java.util.logging.ConsoleHandler
import java.util.logging.Logger

/** Represents the ExecutionUtil mock object as it exists on the Boomi-Platform.
 * https://community.boomi.com/s/article/get-execution-properties-during-execution
 * https://help.boomi.com/bundle/integration/page/r-atm-com_boomi_execution_ExecutionUtil.html
 */
@TypeChecked
class ExecutionUtil {

    private final static Logger _logger = Logger.getLogger(ExecutionUtil.class.name)
    private static ExecutionContexts _executionContext


    /** Framework function to initialize the ExecutionUtil.
      * @param executionContexts
     */
    static fw_initialize(ExecutionContexts executionContexts ) {
        _executionContext = executionContexts != null ? executionContexts : new ExecutionContexts()
        
        _logger.setUseParentHandlers(false)
        def ch = new ConsoleHandler()
        ch.setFormatter( {
            String lev = it.level.toString().padRight(10)    
            return "${lev} ${it.message}\r\n".toString()        
        } )
        _logger.addHandler(ch)
        /* _logger.setFilter { it.loggerName ==ExecutionUtil.class.name } */
    }         


    /**
     * Get a platform logger instance.
     * @return A platform {@link java.util.logging.Logger}.
     *
     * @see
     * <a href="https://community.boomi.com/s/article/write-to-process-logs-using-groovy">
     *     How to Write to the Process Logs Using Groovy</a>
     */
    static Logger getBaseLogger() { return _logger }

    static getRuntimeExecutionProperty(String key){
        _executionContext.executionProperties[ key]
    }

    /** Get a dynamic process property from the execution context
     */
    static void setDynamicProcessProperty(String propertyName, Object value, Boolean persist) {
        _executionContext.dynamicProcessProperties[propertyName] = value
    }

    static Object getDynamicProcessProperty(String propertyName) {
        return _executionContext.dynamicProcessProperties[ propertyName]
    }

    static Object getProcessProperty(String componentId, String propertyKey) {
        return _executionContext.processProperties[componentId][propertyKey]
    }

    static void setProcessProperty(String componentId, String propertyKey, Object value) {
        assert _executionContext != null, "ExecutionUtil not initialized!"
        _executionContext.processProperties[componentId][propertyKey] = value
    }
}


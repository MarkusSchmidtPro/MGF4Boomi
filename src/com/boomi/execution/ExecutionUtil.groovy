package com.boomi.execution

import groovy.transform.TypeChecked
import msPro.mgf4boomi.ExecutionContexts

import java.util.logging.Logger

/** Represents the ExecutionUtil mock object as it exists on the Boomi-Platform.
 * https://community.boomi.com/s/article/get-execution-properties-during-execution
 * https://help.boomi.com/bundle/integration/page/r-atm-com_boomi_execution_ExecutionUtil.html
 */
@TypeChecked
class ExecutionUtil {

    private final static Logger _logger = Logger.getLogger("ExecutionUtil")

    private static ExecutionContexts _executionContext 

    /** Framework function to initialize the ExecutionUtil.
      * @param executionContexts
     */
    static fw_initialize(ExecutionContexts executionContexts ) {
        _executionContext = executionContexts != null ? executionContexts : ExecutionContexts.default()
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
        assert _executionContext != null, "ExecutionUtil not initialized!"
        _executionContext.executionProperties.get( key)
    }

    /** Get a dynamic process property from the execution context
     */
    static void setDynamicProcessProperty(String propertyName, String value, Boolean persist) {
        assert _executionContext != null, "ExecutionUtil not initialized!"
        _executionContext.dynamicProcessProperties.put(propertyName, value)
    }

    static String getDynamicProcessProperty(String propertyName) {
        assert _executionContext != null, "ExecutionUtil not initialized!"
        return _executionContext.dynamicProcessProperties.get(propertyName)
    }

    static String getProcessProperty(String componentId, String propertyKey) {
        assert _executionContext != null, "ExecutionUtil not initialized!"
        return _executionContext.processProperties.get(componentId + propertyKey)
    }

    static void setProcessProperty(String componentId, String propertyKey, String value) {
        assert _executionContext != null, "ExecutionUtil not initialized!"
        _executionContext.processProperties.put(componentId + propertyKey, value)
    }
}


package com.boomi.execution

import groovy.transform.TypeChecked
import msPro.mgf4boomi.ExecutionUtilContexts
import sun.reflect.generics.reflectiveObjects.NotImplementedException

/** Represent the ExecutionManager object as it exists on the Boomi-Platform.
 * https://community.boomi.com/s/article/get-execution-properties-during-execution
 */
@TypeChecked
class ExecutionManager {

    static initialize(ExecutionUtilContexts processExecutionContext) {
        _processExecutionContext = processExecutionContext
    }

    private static ExecutionUtilContexts _processExecutionContext = null


    static ExecutionManager getCurrent() { throw new NotImplementedException() }


    int getTopLevelExecutionId(String propertyName) { throw new NotImplementedException() }
}


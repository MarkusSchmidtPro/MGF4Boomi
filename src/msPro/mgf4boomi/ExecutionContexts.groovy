package msPro.mgf4boomi

import groovy.transform.TypeChecked


/**
 * The Boomi-Platform {@link com.boomi.execution.ExecutionUtil} contexts.
 */
@TypeChecked
class ExecutionContexts {
    public final HashMap<String, Object> dynamicProcessProperties = []
    public final HashMap<String, HashMap> processProperties = [:]
    public final HashMap executionProperties = [
            ACCOUNT_ID : 'IntelliJ_IDEA-M42S66',
            ATOM_ID: '0b6e3ab7-9d81-4954-b781-d212195e577c',
            ATOM_NAME: 'IntelliJ_IDEA-Atom',
            EXECUTION_ID: _generateExecutionId(),
            PROCESS_ID: UUID.randomUUID().toString(),
            PROCESS_NAME: 'PROCESS_NAME'
    ]

    private static String _generateExecutionId() {
        // execution-17445b68-8f07-4b5f-a34e-d142c1e27b68-2020.11.03'
        String dateUtc = (new Date()).format('yyyy.MM.dd', TimeZone.getTimeZone('UTC'))
        return "execution-${UUID.randomUUID()}-${dateUtc}"
    }
}
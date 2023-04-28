package msPro.mgf4boomi

import groovy.transform.TypeChecked


/**
 * The Boomi-Platform {@link com.boomi.execution.ExecutionUtil} contexts.
 */
@TypeChecked
class ExecutionUtilContexts {
    public HashMap<String, String> dynamicProcessProperties
    public HashMap<String, Object> processProperties
    public HashMap<String, String> executionProperties


    ExecutionUtilContexts(HashMap<String, String> dynamicProcessProperties = null) {

        this.dynamicProcessProperties = dynamicProcessProperties != null ? dynamicProcessProperties : new HashMap<String, String>()
        processProperties = new HashMap<String, Object>()
        executionProperties = new HashMap<String, String>()
        executionProperties['ACCOUNT_ID'] = 'IntelliJ_IDEA-M42S66'
        executionProperties['ATOM_ID'] = '0b6e3ab7-9d81-4954-b781-d212195e577c'
        executionProperties['ATOM_NAME'] = 'IntelliJ_IDEA-Atom'
        // not yet provided - need to think about it
        // executionProperties[ 'DOCUMENT_COUNT'] = 'IntelliJ_IDEA-Atom';
        // executionProperties[ 'NODE_ID'] = 'IntelliJ_IDEA-Atom';
        executionProperties['EXECUTION_ID'] = _generateExecutionId()
        executionProperties['PROCESS_ID'] = UUID.randomUUID().toString()
        executionProperties['PROCESS_NAME'] = 'PROCESS_NAME'
    }

    /** Create a new and empty ProcessExecutionContext without any property.
     * To add document properties, use
     *
     * @see #initAddDynamicProcessProperty(java.lang.String, java.lang.String)
     * @see #initAddProcessProperty(java.lang.String, java.lang.String, java.lang.Object)
     */
    static ExecutionUtilContexts empty() { return new ExecutionUtilContexts() }


    /** Add a dynamic process property to the ProcessExecutionContext.
     *
     * When your script is called an you expect any Dynamic Process Property to
     * exists - in case you have set a *DPP* in your process before - you should initialize
     * you *ProcessExecutionContext* with these Dynamic Process Properties.
     * @param propertyName The property name as you specified it in your Boomi process.
     * @param value The property value
     */
    void initAddDynamicProcessProperty(String propertyName, String value) {
        if (!propertyName.startsWith("DPP_"))
            println("WARN(${propertyName}): Dynamic Process Property names should start with 'DPP_'")

        dynamicProcessProperties[propertyName] = value
    }


    /** Add a dynamic process property to the ProcessExecutionContext.
     *
     * When your script is called an you expect any Dynamic Process Property to
     * exists - in case you have set a *DPP* in your process before - you should initialize
     * you *ProcessExecutionContext* with these Dynamic Process Properties.
     * @param propertyName The property name as you specified it in your Boomi process.
     * @param value The property value
     */
    void initAddProcessProperty(String componentId, String propertyKey, Object value) {
        if (!isGuid(componentId))
            throw new InvalidPropertiesFormatException("ComponentId(${componentId}) must be a valid GUID")
        if (!isGuid(propertyKey))
            throw new InvalidPropertiesFormatException("PropertyKey(${propertyKey}) must be a valid GUID")
        processProperties[componentId + propertyKey] = value
    }

    static boolean isGuid(String g) {
        return g.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")
    }

    String getProcessProperty(String componentId, String propertyKey) { processProperties[componentId + propertyKey] }


    private static String _generateExecutionId() {
        // execution-17445b68-8f07-4b5f-a34e-d142c1e27b68-2020.11.03'
        String dateUtc = (new Date()).format('yyyy.MM.dd', TimeZone.getTimeZone('UTC'))
        return "execution-${UUID.randomUUID()}-${dateUtc}"
    }
}
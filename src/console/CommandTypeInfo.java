package console;

import java.lang.reflect.Method;

public class CommandTypeInfo {

    private String command;
    private String info;
    private Method method;
    private Object target;

    CommandTypeInfo(String command, String info, Object target , Method method) { //... zero or more Class objects may be passed
        this.command = command;
        this.info = info;
        this.method = method;
        this.target = target;
    }

    public String getName() {
        return this.command;
    }

    public String getHelpText() {
        return this.info;
    }

    public Class<?>[] getParamTypes() {
        return method.getParameterTypes();
    }
    
    public Method getMethod() {
		return method;
    }
    
    public Object getTarget() {
		return target;
    	
    }
}

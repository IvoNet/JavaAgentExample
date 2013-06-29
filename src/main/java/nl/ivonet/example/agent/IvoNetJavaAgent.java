package nl.ivonet.example.agent;

import nl.ivonet.example.code.MyMain;
import nl.ivonet.example.code.One;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IvoNetJavaAgent {

    static final Logger LOG = LoggerFactory.getLogger(IvoNetJavaAgent.class);

    private static Instrumentation instrumentation;

    /**
     * JVM hook to statically load the javaagent at startup.
     *
     * After the Java Virtual Machine (JVM) has initialized, the premain method
     * will be called. Then the real application main method will be called.
     */
    public static void premain(final String args, final Instrumentation inst) throws Exception {
        instrumentation = inst;
        LOG.info("premain method invoked with args: {} and inst: {}", args, inst);
        instrumentation.addTransformer(new FooMethodTransformer());
//        printAllLoadedClasses();

        invokeFoo();
    }

    private static void invokeFoo() throws IllegalAccessException, InvocationTargetException {
        MyMain main = new MyMain();
        printFoo(main);
        One one = main.getOne();
        printFoo(one);

    }

    private static void printFoo(final Object anObject) throws IllegalAccessException, InvocationTargetException {
        final Method[] methods = anObject.getClass().getMethods();
        for (final Method method : methods) {
            if ("foo".equals(method.getName())) {
                LOG.info(method.getDeclaringClass().getName()+" has method foo");
                method.invoke(anObject, null);
                break;
            }
        }
    }

    private static void printAllLoadedClasses() {
        final Class[] allLoadedClasses = instrumentation.getAllLoadedClasses();
        for (final Class loadedClass : allLoadedClasses) {
            LOG.info(loadedClass.getName());
        }
    }

    /**
     * JVM hook to dynamically load javaagent at runtime.
     *
     * The agent class may have an agentmain method for use when the agent is
     * started after VM startup.
     *
     * @param args
     * @param inst
     * @throws Exception
     */
    public static void agentmain(final String args, final Instrumentation inst) throws Exception {
        LOG.info("agentmain method invoked with args: {} and inst: {}", args, inst);
//        instrumentation = inst;
//        instrumentation.addTransformer(new MyClassFileTransformer());
    }

    /**
     * Programmatic hook to dynamically load javaagent at runtime.
     */
//    public static void initialize() {
//        if (instrumentation == null) {
//            MyJavaAgentLoader.loadAgent();
//        }
//    }

}
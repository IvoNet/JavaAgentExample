package nl.ivonet.example.agent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * I Add the method foo to all classes.
 *
 * @author Ivo Woltring
 */
public class FooMethodTransformer implements ClassFileTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(FooMethodTransformer.class);

    @Override
    public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined,
                            final ProtectionDomain protectionDomain, final byte[] classfileBuffer)
            throws IllegalClassFormatException {

        LOG.info("Classname           : " + className);

        //Enter javassist dependency
        try {
            final ClassPool cp = ClassPool.getDefault();
            final CtClass ct = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
            ct.addMethod(CtNewMethod.make("public void foo() {}", ct));
            final CtMethod method = ct.getMethod("foo", "()V");
            method.insertAfter("System.out.println(\"foo is invoked on: \" + this.getClass().getName());");
            return ct.toBytecode();

        } catch (IOException | CannotCompileException | NotFoundException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Should not be here");
    }
}

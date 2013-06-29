package nl.ivonet.example.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 *
 * @author Ivo Woltring
 */
public class MyMain {
    private static final Logger LOG = LoggerFactory.getLogger(MyMain.class);
    private final One one;

    public MyMain() {
        one = new One();
    }

    public One getOne() {
        return one;
    }

    public static void main(String[] args) {
        LOG.info("main method invoked with args: {}", Arrays.asList(args));

    }
}

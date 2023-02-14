package kz.meiir.petproject;


import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author Meiir Akhmetov on 14.02.2023
 */
public class TimingExtension implements
        BeforeTestExecutionCallback, AfterTestExecutionCallback, BeforeAllCallback, AfterAllCallback {

    private static final Logger Log = LoggerFactory.getLogger("result");

    private StopWatch stopWatch;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        stopWatch = new StopWatch("Execution time of " + extensionContext.getRequiredTestClass().getSimpleName());

    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        Log.info("Start stopWatch");
        stopWatch.start(extensionContext.getDisplayName());
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        stopWatch.stop();
        Log.info("stop stopWatch");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        Log.info('\n' + stopWatch.prettyPrint() + '\n');
    }
}

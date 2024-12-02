package tst;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * test suite that acts as the center hub to run the test classes AccountTest class and CreditTest class.
 * @author Gerardo Sillas
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AccountTest.class,
    CreditTest.class
})
public class TestSuite {
}



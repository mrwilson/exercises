package uk.co.probablyfine.exercises.recorder;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class CollatorTest {
    /*
    The CollatedTests class is a class that is used to collect test results across multiple Junit runs.
    During each Junit run, log records are collected that indicate which tests passed, failed, were disabled, or were aborted.
    (I do not know what aborted means in this context, and have never seen an aborted test.)

    Imagine a TestStatus, which is Pass, Fail, Disable, Abort, and one other, Unrun.

    The CollatedTests class has two functions.
    1. add(testname:String, status:TestStatus)
    2. endRun():List<TestResult>.

    This is because the data comes in as four arrays associated with the first four TestStatus enums above. (No Unruns are added.)

    endRun() is the DS&A part.
    It must return a list of TestResult, which has three fields: Name of the test, TestStatus, and a boolean isNew.
    Further, that list must be in the same order every time it’s called.
    That is, if the 0’th test was “MyTest.testSomething()”, that must be the 0th entry at every call to endRun().
    Even if it was not run during that JUnit run, and hence is missing in log record.

    Finally, if a test has never been run before a particular JUnit run, that TestResult must be marked as “isNew”.
     */

    static class CollatedTests {

        public enum TestStatus {
            PASS
        }

        private final Set<String> seenTests = new HashSet<>();
        private List<TestResult> results = new ArrayList<>();

        public record TestResult(String testName, TestStatus status, boolean isNew) {}

        public void add(String testCase, TestStatus status) {
            results.add(new TestResult(testCase, status, !seenTests.contains(testCase)));
            seenTests.add(testCase);
        }

        public List<TestResult> endRun() {
            var returnValue = List.copyOf(results);
            results.clear();
            return returnValue;
        }
    }

    @Test
    void noTestsAddedReturnsEmptySummary() {
        var collator = new CollatedTests();

        assertThat(collator.endRun(), Matchers.empty());
    }

    @Test
    void aSingleSubmittedTestGivesASingleResultBack() {
        var collator = new CollatedTests();

        collator.add("test1", CollatedTests.TestStatus.PASS);

        var summary = collator.endRun();
        var expected = new CollatedTests.TestResult("test1", CollatedTests.TestStatus.PASS, true);

        assertThat(summary, Matchers.hasItem(expected));
    }

    @Test
    void theCollatorTracksNewTests() {
        var collator = new CollatedTests();

        collator.add("test1", CollatedTests.TestStatus.PASS);
        var expected = new CollatedTests.TestResult("test1", CollatedTests.TestStatus.PASS, true);
        assertThat(collator.endRun(), Matchers.hasItem(expected));

        collator.add("test1", CollatedTests.TestStatus.PASS);
        var expected2 = new CollatedTests.TestResult("test1", CollatedTests.TestStatus.PASS, false);
        assertThat(collator.endRun(), Matchers.hasItem(expected2));
    }
}

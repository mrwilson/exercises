package uk.co.probablyfine.exercises.recorder;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import uk.co.probablyfine.exercises.recorder.CollatorTest.CollatedTests.TestResult;
import uk.co.probablyfine.exercises.recorder.CollatorTest.CollatedTests.TestStatus;

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
            PASS,
            UNRUN
        }

        private final Set<String> seenTests = new HashSet<>();
        private final Map<String, TestResult> results = new HashMap<>();

        public record TestResult(String testName, TestStatus status, boolean isNew) {
            static TestResult unrun(String test) {
                return new TestResult(test, TestStatus.UNRUN, false);
            }
        }

        public void add(String testCase, TestStatus status) {
            results.put(testCase, new TestResult(testCase, status, !seenTests.contains(testCase)));
            seenTests.add(testCase);
        }

        public List<TestResult> endRun() {
            var returnValue =
                    seenTests.stream()
                            .map(test -> results.getOrDefault(test, TestResult.unrun(test)))
                            .toList();

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

        collator.add("test1", TestStatus.PASS);

        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.PASS, true));
    }

    @Test
    void theCollatorTracksNewTests() {
        var collator = new CollatedTests();

        collator.add("test1", TestStatus.PASS);
        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.PASS, true));

        collator.add("test1", TestStatus.PASS);
        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.PASS, false));
    }

    @Test
    void theCollatorTracksUnrunTests() {
        var collator = new CollatedTests();

        collator.add("test1", TestStatus.PASS);
        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.PASS, true));

        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.UNRUN, false));
    }

    static Matcher<Iterable<? super TestResult>> hasTestResult(
            String test, TestStatus status, boolean isNew) {
        return Matchers.hasItem(new TestResult(test, status, isNew));
    }
}

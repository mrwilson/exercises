package uk.co.probablyfine.exercises.recorder;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;
import org.hamcrest.Matcher;
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

    public enum TestStatus {
        PASS,
        FAIL,
        UNRUN
    }

    public record TestResult(String testName, TestStatus status, boolean isNew) {
        public TestResult(String testName, StatusAndNew status) {
            this(testName, status.status(), status.isNew());
        }
    }

    public record StatusAndNew(TestStatus status, boolean isNew) {
        public StatusAndNew() {
            this(TestStatus.UNRUN, false);
        }

        public StatusAndNew(TestStatus status) {
            this(status, true);
        }

        public StatusAndNew seen() {
            return new StatusAndNew(status, false);
        }
    }

    static class CollatedTests {

        private final Map<String, StatusAndNew> results = new TreeMap<>();

        public void add(String testCase, TestStatus status) {
            results.merge(testCase, new StatusAndNew(status), (_, next) -> next.seen());
        }

        public List<TestResult> endRun() {
            var returnValue =
                    results.entrySet().stream()
                            .map(e -> new TestResult(e.getKey(), e.getValue()))
                            .toList();

            results.replaceAll((_, _) -> new StatusAndNew());
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

    @Test
    void theCollatorMaintainsOrderOfTestsOverTime() {
        var collator = new CollatedTests();

        collator.add("test1", TestStatus.PASS);
        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.PASS, true));

        collator.add("test2", TestStatus.PASS);
        assertThat(
                collator.endRun(),
                Matchers.contains(
                        new TestResult("test1", TestStatus.UNRUN, false),
                        new TestResult("test2", TestStatus.PASS, true)));
    }

    @Test
    void theCollatorMaintainsOrderOfTestsBetweenRuns() {
        var collator = new CollatedTests();

        collator.add("test1", TestStatus.PASS);
        assertThat(collator.endRun(), hasTestResult("test1", TestStatus.PASS, true));

        collator.add("test2", TestStatus.PASS);
        collator.add("test1", TestStatus.PASS);
        assertThat(
                collator.endRun(),
                Matchers.contains(
                        new TestResult("test1", TestStatus.PASS, false),
                        new TestResult("test2", TestStatus.PASS, true)));
    }

    @Test
    void theCollatorPassesRonsHardTest() {
        var collator = new CollatedTests();

        collator.add("T1", TestStatus.PASS);
        assertThat(collator.endRun(), hasTestResult("T1", TestStatus.PASS, true));

        collator.add("T2", TestStatus.PASS);
        collator.add("T1", TestStatus.FAIL);

        assertThat(
                collator.endRun(),
                Matchers.contains(
                        new TestResult("T1", TestStatus.FAIL, false),
                        new TestResult("T2", TestStatus.PASS, true)));

        collator.add("T3", TestStatus.PASS);
        collator.add("T1", TestStatus.FAIL);

        assertThat(
                collator.endRun(),
                Matchers.contains(
                        new TestResult("T1", TestStatus.FAIL, false),
                        new TestResult("T2", TestStatus.UNRUN, false),
                        new TestResult("T3", TestStatus.PASS, true)));
    }

    static Matcher<Iterable<? super TestResult>> hasTestResult(
            String test, TestStatus status, boolean isNew) {
        return Matchers.hasItem(new TestResult(test, status, isNew));
    }
}

package uk.co.probablyfine.exercises.recorder;

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

}

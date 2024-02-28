package edu.eci.arep;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

public class SparkWebServerTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SparkWebServerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SparkWebServerTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

    public void testPalindrome() throws Exception {
        String[] strings = new String[]{"hola", "anitalavalatina", "123454321"};
        String[] expectedResults = new String[]{"false", "true", "true"};
        String[] result = SparkWebServer.palindrome(strings);
        assertTrue(Arrays.equals(expectedResults, result));
    }

    public void testCompute() throws Exception {
        String[] queries = new String[]{"sin(1)", "cos(4)", "pal(123454321)", "vem(3,4)"};
        String[] expectedResults = new String[]{"{\"result\":[0.8414709848078965]}", "{\"result\":[-0.6536436208636119]}", "{\"result\":[true]}", "{\"result\":[5.0]}"};
        for (int i = 0; i < queries.length; i++) {
            String result = SparkWebServer.compute(queries[i]);
            String expected = expectedResults[i];
            assertEquals(expected, result);
        }
    }

    public void testMathMethods() throws Exception {
        double[] array = new double[] {1, 2, 3};
        String command = "sin";
        double[] expectedResults = new double[]{Math.sin(1), Math.sin(2), Math.sin(3)};
        SparkWebServer.mathMethods(array, command);
        assertTrue(Arrays.equals(expectedResults, array));
    }

    public void testVectorMagnitude() {
        double[][] arrays = new double[][]{{1, 2}, {3, 4}, {5, 12}};
        double[] expectedResults = new double[]{2.23606797749979, 5.0, 13.0};
        for (int i = 0; i < arrays.length; i++) {
            double result = SparkWebServer.vectorMagnitude(arrays[i]);
            double expected = expectedResults[i];
            assertEquals(expected, result);
        }
    }

    public void testDoubleArrayParams() {
        String[] paramsStrings = new String[]{"1,2,3", "4.5,6.78", "10"};
        double[][] expectedResults = new double[][]{{1, 2, 3}, {4.5, 6.78}, {10}};
        for (int i = 0; i < paramsStrings.length; i++) {
            double[] result = SparkWebServer.doubleArrayParams(paramsStrings[i]);
            double[] expected = expectedResults[i];
            assertTrue(Arrays.equals(expected, result));
        }
    }
}

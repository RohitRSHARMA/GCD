package com.gcdops.util;

/**
 * This class contains utility methods to support the Application
 * @author rohit.sharma
 *
 */
public class GCDOpsUtil {
	
	
	/**
	 * Method to find GCD between two numbers 
	 * @param number1
	 * @param number2
	 * @return GCD of two numbers
	 */
	public static int findGCD(int number1, int number2) {
        //base case
        if(number2 == 0){
            return number1;
        }
        return findGCD(number2, number1%number2);
    }
}

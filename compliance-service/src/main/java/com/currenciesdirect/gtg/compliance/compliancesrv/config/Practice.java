package com.currenciesdirect.gtg.compliance.compliancesrv.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Practice {
	
	    public static void main(String[] args) {
	        // Your list of elements
	        List<Integer> numbers = new ArrayList<>(Arrays.asList(4, 5, 60, 7, 1, 2, 3));

	        // Filter numbers greater than 50 using streams
	        List<Integer> filteredNumbers = numbers.stream()
	                .filter(number -> number > 50)
	                .collect(Collectors.toList());

	        // Output the filtered numbers
	        for (Integer filteredNumber : filteredNumbers) {
	            System.out.print(filteredNumber + " ");
	        }
	    }
	}



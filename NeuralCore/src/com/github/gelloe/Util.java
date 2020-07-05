package com.github.gelloe;

public class Util {
	
	public static double sigmoid(double d) {
		return 1 / (1 + Math.exp(-d));
	}
	
	public static double trunc(double input, double decimalpoints) {
		return Math.round(input * Math.pow(10, decimalpoints)) / Math.pow(10, decimalpoints);
	}

}

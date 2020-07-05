package com.github.gelloe;

import java.util.Arrays;

public class Main {

	public static void main(String args[]) {

		Network n = new Network(2, 5, 2);
		n.randomize();
		System.out.println(Arrays.toString(n.query(new double[] { 1, 1 })));

	}

}

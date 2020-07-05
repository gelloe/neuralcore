package com.github.gelloe;

import java.util.ArrayList;
import java.util.Random;

public class Network {

	private ArrayList<Matrix> matrices = new ArrayList<Matrix>();
	private ArrayList<Matrix> weights = new ArrayList<Matrix>();

	public Network(int... cells) {
		if (cells.length < 2)
			throw new NullPointerException("Neural network must contain 2 or more layers");
		for (int i = 0; i < cells.length - 1; i++) {
			matrices.add(new Matrix(cells[i], cells[i + 1]));
			weights.add(new Matrix(1, cells[i + 1]));
		}
	}
	
	public void randomize() {
		Random r = new Random();
		for (Matrix m : matrices)
			m.forEach(s -> s = (double) r.nextInt(10) - 5);
		for (Matrix m : weights)
			m.forEach(s -> s = Util.trunc(r.nextDouble(), 5));
	}
	
	public double[] node(int node, double[] inputs) {
		Matrix input = new Matrix(1, inputs.length);
		for (int i = 0; i < inputs.length; i++) {
			input.insert(0, i, inputs[i]);
		}
		Matrix aux = input.multiply(matrices.get(node));
		aux.forEach(s -> Util.sigmoid(s));
		aux.multiply(weights.get(node).transpose());
		double[] result = new double[matrices.get(node).columns];
		return result;
	}
	
	public double[] query(double[] inputs) {
		Matrix m = new Matrix(1, inputs.length);
		m.insertRow(0, inputs);
		for (int i = 0; i < matrices.size(); i++) {
//			System.out.println("	Operation " + (i + 1) + " / " + matrices.size() + "\n");
//			System.out.println(m + "  *");
//			System.out.println(matrices.get(i) + "  =");
			Matrix aux = m.multiply(matrices.get(i));
//			System.out.println(aux + " sig()");
			aux.forEach(s -> s = Util.sigmoid(s));
//			System.out.println(aux + "+");
			aux = aux.add(weights.get(i));
//			System.out.println(weights.get(i) + " = \n" + aux);
			if (i + 1 == matrices.size())
				return aux.getRow(0);
			else
				m = aux;
		}
		return null;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < matrices.size(); i++) {
			result += "Matrix " + i + ":\n";
			result += matrices.get(i);
			result += "Weight " + i + ":\n";
			result += weights.get(i);
			result += "\n";
		}
		return result;
	}

}

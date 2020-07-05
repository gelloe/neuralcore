package com.github.gelloe;

import java.util.Arrays;
import java.util.function.Function;

public class Matrix {

	private double[][] array;
	public final int rows, columns;

	public Matrix(int rows, int columns) {
		this.array = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}

	public double get(int row, int column) {
		return array[row][column];
	}
	
	public double[] getRow(int row) {
		return array[row];
	}
	
	public double[] getColumn(int column) {
		double[] result = new double[rows];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i][column];
		}
		return result;
	}
	
	public double[][] asArray(){
		return array;
	}

	public void insert(int row, int column, double val) {
		array[row][column] = val;
	}
	
	public void insertRow(int row, double[] values) {
		array[row] = values;
	}
	
	public void insertCol(int col, double[] values) {
		for (int i = 0; i < rows; i++) {
			array[i][col] = values[i]; 
		}
	}

	public void forEach(Function<Double, Double> f) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				insert(i, j, f.apply(get(i, j)));
			}
		}
	}

	public Matrix multiply(Matrix m) {
		if (columns != m.rows) {
			System.err.println(this);
			System.err.println(m);
			throw new NullPointerException("Number of columns does not match number of rows in second matrix (col: " + columns + ", rows: " + m.rows + ")");
		}
		Matrix product = new Matrix(rows, m.columns);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < m.columns; j++)
				for (int k = 0; k < columns; k++)
					product.insert(i, j, product.get(i, j) + get(i, k) * m.get(k, j));
		return product;
	}

	public Matrix multiply(int scalar) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				array[i][j] *= scalar;
			}
		}
		return this;
	}

	public Matrix add(Matrix m) {
		if (!(rows == m.rows && columns == m.columns))
			throw new NullPointerException("Matrices don't contain equal number of rows and columns");
		Matrix product = new Matrix(rows, columns);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				product.insert(i, j, get(i, j) + m.get(i, j));
			}
		}
		return product;
	}

	public Matrix subtract(Matrix m) {
		return add(m.multiply(-1));
	}

	public Matrix transpose() {
		Matrix product = new Matrix(columns, rows);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				product.insert(j, i, get(i, j));
			}
		}
		return product;
	}

	@Override
	public String toString() {
		String print = "";
		for (int i = 0; i < rows; i++) {
			print += Arrays.toString(array[i]) + "\n";
		}
		return print;
	}

}
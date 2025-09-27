import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
 import java.text.DecimalFormat;

public class Polynomial{
	double [] coeffis;
	int [] exp;
	
	public Polynomial(){}
	
	public Polynomial(double[] coeffis, int[] exp)
	{
		this.coeffis = new double[coeffis.length];
		for(int i=0; i<coeffis.length; i++)
		{
			this.coeffis[i] = coeffis[i];
		}

		this.exp = new int[exp.length];
		for(int i=0; i<exp.length; i++)
		{
			this.exp[i] = exp[i];
		}
	}

	public Polynomial(File f){
		try (Scanner reader = new Scanner(f)){
			String poly = reader.nextLine();
			int idx=0;
			String regex = "(?=[+-])";
			String[] poly_array = poly.split(regex);
			coeffis = new double[poly_array.length];
			exp = new int[poly_array.length];

			for (int i=0; i<poly_array.length; i++){
				if (!poly_array[i].contains("x")){
					coeffis[idx] = Double.parseDouble(poly_array[i]);
					exp[idx] = 0;
				}

				else {
					String[] elements = poly_array[i].split("x", -1);
					for (String s:elements){
					}
					if (elements[0].equals("") || elements[0].equals("+")){
						coeffis[idx] = 1;
					}

					else if(elements[0].equals("-")){
						coeffis[idx] = -1;
					}

					else{
						coeffis[idx] = Double.parseDouble(elements[0]);
					}

					if (elements[1].equals("")){
						exp[idx] = 1;
					}

					else{
						exp[idx] = Integer.parseInt(elements[1]);
					}
				}
				idx++;
			}
		} catch (Exception excep){
			excep.printStackTrace();
		}
	}
	
	public Polynomial add(Polynomial p) {
		int max_len = this.coeffis.length + p.coeffis.length;
		double[] new_co = new double[max_len];
		int[] new_ex = new int[max_len];
		int pure_length = 0;
		int size = 0;
		int idx = 0;
		boolean found = false;

		for (int i=0; i<p.exp.length; i++)
		{
			new_ex[i]=p.exp[i];
			new_co[i]=p.coeffis[i];
			size++;
		}

		for (int i=0; i<this.exp.length; i++){
			found = false;
			for (int j=0; j<p.exp.length; j++){
				if (new_ex[j]==this.exp[i]){
					new_co[j] += this.coeffis[i];
					found = true;
				}
			}
			if (!found){
				new_ex[size] = this.exp[i];
				new_co[size] = this.coeffis[i];
				size++;
			}

		}

		for (int i=0; i < size; i++){
			if (Math.abs(new_co[i]) > 1e-12) pure_length++;
		}

		int[] refined_ex = new int[pure_length];
		double[] refined_co = new double[pure_length];

		for (int i=0; i<size; i++){
			if (Math.abs(new_co[i])>1e-12)
			{
				refined_ex[idx] = new_ex[i]; 
				refined_co[idx] = new_co[i];
				idx++;
			}
		}

		return new Polynomial(refined_co, refined_ex);
	}
	
	public double evaluate(double x)
	{
		double result=0;
		for(int i=0; i<this.coeffis.length;i++)
		{
			result += coeffis[i] * Math.pow(x, exp[i]);
		}
		
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result = this.evaluate(x);
		if (Math.abs(result) < 1e-9) {
			return true;
		}
		return false;
	}

	public Polynomial multiply(Polynomial p){
		int len = this.coeffis.length * p.coeffis.length;
		double[] new_co = new double[len];
		int[] new_ex = new int[len];
		int idx=0;
		int size=0;
		int pure_length=0;

		for (int i=0; i<this.coeffis.length; i++){
			for (int j=0; j<p.coeffis.length; j++){
				new_co[size] = this.coeffis[i] * p.coeffis[j];
				new_ex[size] = this.exp[i] + p.exp[j];
				size++;
			}
		}

		for (int i=0; i<size; i++){
			for (int j=i+1; j<size; j++){
				if (new_ex[i]==new_ex[j]){
					new_co[i] += new_co[j];
					new_co[j] = 0.0;
				}
			}
		}

		for (int i=0; i < size; i++){
			if (Math.abs(new_co[i]) > 1e-12) pure_length++;
		}

		int[] refined_ex = new int[pure_length];
		double[] refined_co = new double[pure_length];

		for (int i=0; i<size; i++){
			if (Math.abs(new_co[i])>1e-12)
			{
				refined_ex[idx] = new_ex[i]; 
				refined_co[idx] = new_co[i];
				idx++;
			}
		}

		return new Polynomial(refined_co, refined_ex);
	}

	public void saveToFile(String name){
		DecimalFormat format = new DecimalFormat("0.#");
		File poly = new File(name + ".txt");
		try (FileWriter writer = new FileWriter(poly)){
			for (int i=0; i<exp.length; i++){
				if (exp[i]==0){
					if (coeffis[i] > 0 && i != 0){
						writer.write("+");
					}
					writer.write(format.format(coeffis[i]));
				}
				else{
					if (coeffis[i]==1){
						if (i!=0){
							writer.write("+");
						}
					}
					else if(coeffis[i]==-1){
						writer.write("-");
					}
					else {
						if (i!=0 && coeffis[i] > 0) {
							writer.write("+");
						}
						writer.write(format.format(coeffis[i]));
					}
				}

				if (exp[i] == 1){
					writer.write("x");
				}
				else if(exp[i] != 0){
					writer.write("x" + exp[i]);
				}
			}
		}
		catch(IOException e){
			System.out.println("Error handling the file: " + e.getMessage());
		}
	}
}
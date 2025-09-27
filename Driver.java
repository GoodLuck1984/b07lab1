import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        try {

            File poly = new File("poly.txt");
            FileWriter writer = new FileWriter(poly);
            writer.write("2+x+3x2-x2-7x3");  
            writer.close();

            //Test file constructor
            Polynomial p1 = new Polynomial(poly);
            System.out.println("p1.exp = " + Arrays.toString(p1.exp));
            System.out.println("p1.coeffis = " + Arrays.toString(p1.coeffis));

            //Test manual constructor
            double[] coeffs = {1, 1, -2}; 
            int[] exps = {0, 2, 1};
            Polynomial p2 = new Polynomial(coeffs, exps);

            //Test evaluation
            System.out.println("p1(2) = " + p1.evaluate(2)); 
            System.out.println("p2(2) = " + p2.evaluate(2)); 

            //Test addition
            Polynomial sum = p1.add(p2);
            System.out.println("p1 + p2 at x=2 : " + sum.evaluate(2));

            //Test multiplication
            Polynomial prod = p1.multiply(p2);
            System.out.println("p1 * p2 at x=2 : " + prod.evaluate(2));

            //Test root check
            System.out.println("x=1 is a root of p1? " + p1.hasRoot(1));
            System.out.println("x=0.25 is a root of p2? " + p2.hasRoot(1));

            //Test saveToFile
            p1.saveToFile("new polynomial-1");
            p2.saveToFile("new polynomial-2");

        } catch (IOException e) {
            System.out.println("Error handling the file: " + e.getMessage());
        }
    }
}
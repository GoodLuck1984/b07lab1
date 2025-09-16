public class Polynomial{
	double [] coeffis;
	
	public Polynomial(){
		coeffis = new double[] {0};
	}
	
	public Polynomial(double [] coeffis) {
		this.coeffis = new double[coeffis.length];
		for (int i=0;i<coeffis.length;i++) {
			this.coeffis[i]=coeffis[i];
		}
	}
	
	public Polynomial add(Polynomial p) {
		int len = Math.max(this.coeffis.length, p.coeffis.length);
		double [] result = new double[len];
		for (int i=0; i<len; i++) {
			if (this.coeffis.length-1>=i && p.coeffis.length-1>=i)
			{
				result[i] += this.coeffis[i] + p.coeffis[i];
			}
			else if (this.coeffis.length<=i) {
				result[i] += p.coeffis[i];
			}
			else {
				result[i] += this.coeffis[i];
			}
		}
		
		return new Polynomial(result);
	}
	
	public double evaluate(double x) {
		double result=0; 
		for (int i=0; i<this.coeffis.length; i++) {
			if (this.coeffis[i]!=0) {
				result = result + this.coeffis[i]*Math.pow(x, i);
			}
		}
		
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result;
		result = this.evaluate(x);
		if (Math.abs(this.evaluate(x)) < 1e-9) {
			return true;
		}
		return false;
	}
}
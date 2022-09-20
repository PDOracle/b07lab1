public class Polynomial{
	double [] coefficients;
	
	public Polynomial(){
		coefficients = new double[]{0};
	}

	public Polynomial(double [] p2coeff){
		coefficients = new double[p2coeff.length];
		for(int i = 0; i < p2coeff.length; i++){
			coefficients[i] = p2coeff[i];
		}
	}

	public Polynomial add(Polynomial p2){
		double [] p2coeff = p2.coefficients;
		if(coefficients.length <= p2coeff.length){
			for(int i = 0; i < coefficients.length; i++){
				p2coeff[i] += coefficients[i]; 
			}
			return p2;
		}
		else{
			for(int i = 0; i < p2coeff.length; i++){
				coefficients[i] += p2coeff[i];
			}
			return this;
		}
	}

	public double evaluate(double x){
		double total = 0;
		for(int i = 0; i < coefficients.length; i++){
			total += coefficients[i] * Math.pow(x, i);
		}
		return total;
	}
	

	public boolean hasRoot(double x){
		if(evaluate(x) == 0){
			return true;
		}
		else{
			return false;
		}
	}
















}
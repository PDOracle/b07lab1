import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Polynomial{
	double [] coefficients;
	int [] exponents;
	public Polynomial(){
		coefficients = new double[]{0};
		exponents = new int[]{0};
	}

	public Polynomial(double [] p2coeff, int [] p2exp){
		int coeffCount = 0;
		for(int i = 0; i < p2coeff.length; i++) {
			if(p2coeff[i] != 0) {
				coeffCount += 1;
			}
		}
		coefficients = new double[coeffCount];
		exponents = new int[coeffCount];
		coeffCount = 0;
		
		for(int j = 0; j < p2coeff.length; j++){
			if(p2coeff[j] != 0) {
				coefficients[coeffCount] = p2coeff[j];
				exponents[coeffCount] = p2exp[j];
				coeffCount += 1;
			}
		}
		
	}
	
	public Polynomial(File f) throws FileNotFoundException, IOException {
		double [] newCoeffInit;
		int [] newExpInit;
		double [] newCoeffFinal;
		int [] newExpFinal;
		BufferedReader polyFile = new BufferedReader(new FileReader(f));
		String fInit = polyFile.readLine();
		String fInit2 = fInit.replace("-", "+-");
		String [] termList = fInit2.split("\\+");
		int maxExp = -1;
		
		for(int i = 0; i < termList.length; i++) {
			String [] oneTerm = termList[i].split("x");
			if(termList[i].indexOf('x') == -1) { //constant case
				if(maxExp < 0) {
					maxExp = 0;
				}
			}
			else if(termList[i].indexOf('x') == (termList[i].length() - 1)) { //x^1 case
				if(maxExp < 1) {
					maxExp = 1;
				}
			}
			
			else{
				int expTerm = Integer.parseInt(oneTerm[1]);
				if(maxExp < expTerm) {
					maxExp = expTerm;
				}
			}
		}
		
		newCoeffInit = new double [maxExp + 1];
		newExpInit = new int [maxExp + 1];
		
		for(int j = 0; j < termList.length; j++) { //loop to add values to arrays (j)
			String [] oneTerm = termList[j].split("x");
			if(termList[j].indexOf('x') == -1) { //constant case
				double doubTerm = Double.parseDouble(oneTerm[0]);
				newCoeffInit[0] = doubTerm;
				newExpInit[0] = 0;
			}
			else if(termList[j].indexOf('x') == termList[j].length() - 1) { //x^1 case
				double doubTerm = Double.parseDouble(oneTerm[0]);
				newCoeffInit[1] = doubTerm;
				newExpInit[1] = 1;
			}
			
			else{
				double doubTerm = Double.parseDouble(oneTerm[0]);
				int expTerm = Integer.parseInt(oneTerm[1]);
				newCoeffInit[expTerm] = doubTerm;
				newExpInit[expTerm] = expTerm;
			}
		}
		
		newCoeffFinal = new double [termList.length];
		newExpFinal = new int [termList.length];
		
		int coeffCount = 0;
		
		for(int l = 0; l < newCoeffInit.length; l++) {
			if(newCoeffInit[l] != 0) {
				newCoeffFinal[coeffCount] = newCoeffInit[l];
				newExpFinal[coeffCount] = newExpInit[l];
				coeffCount += 1;
			}
		}
		
		coefficients = newCoeffFinal;
		exponents = newExpFinal;
		
	}

	public Polynomial add(Polynomial p2){
		double [] p2coeff = p2.coefficients;
		int [] p2exp = p2.exponents;
		double [] p3coeff;
		int [] p3exp;
		double [] sumCoeff;
		int [] sumExp;
		int coeffCount = 0;
		int maxExp = -1;
		for(int m = 0; m < p2coeff.length; m++) {
			if(p2exp[m] > maxExp) {
				maxExp = p2exp[m];
			}
		}
		for(int n = 0; n < coefficients.length; n++) {
			if(exponents[n] > maxExp) {
				maxExp = exponents[n];
			}
		}
			
		p3coeff = new double[maxExp + 1];
		p3exp = new int[maxExp + 1];
		
		
		for(int i = 0; i < coefficients.length; i++) {
			p3coeff[exponents[i]] += coefficients[i];
			p3exp[exponents[i]] = exponents[i];
		}
		for(int j = 0; j < p2coeff.length; j++) {
			p3coeff[p2exp[j]] += p2coeff[j];
			p3exp[p2exp[j]] = p2exp[j];
		}
		
		
		for(int k = 0; k < p3coeff.length; k++) {
			if(p3coeff[k] != 0) {
				coeffCount += 1;
			}
		}
		
		
		sumCoeff = new double[coeffCount];
		sumExp = new int[coeffCount];
		
		int newCount = 0;
		
		for(int l = 0; l < p3coeff.length; l++) {
			if(p3coeff[l] != 0) {
				sumCoeff[newCount] = p3coeff[l];
				sumExp[newCount] = p3exp[l];
				newCount += 1;
			}
		}
		
		Polynomial p = new Polynomial(sumCoeff, sumExp);
		return p;
	}

	public double evaluate(double x){
		double total = 0;
		for(int i = 0; i < coefficients.length; i++){
			total += coefficients[i] * Math.pow(x, exponents[i]);
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

		
	public Polynomial multiply(Polynomial p2) {
		double [] p2coeff = p2.coefficients;
		int [] p2exp = p2.exponents;
		double [] p3coeff;
		int [] p3exp;
		double [] multCoeff;
		int [] multExp;
		int coeffCount = 0;
		
		int maxExp1 = -1;
		int maxExp2 = -1;
		for(int m = 0; m < p2coeff.length; m++) {
			if(p2exp[m] > maxExp1) {
				maxExp1 = p2exp[m];
			}
		}
		for(int n = 0; n < coefficients.length; n++) {
			if(exponents[n] > maxExp2) {
				maxExp2 = exponents[n];
			}
		}
			
		p3coeff = new double[maxExp1 + maxExp2 + 1];
		p3exp = new int[maxExp1 + maxExp2 + 1];
		
		for(int i = 0; i < coefficients.length; i++) {
			for(int j = 0; j < p2coeff.length; j++) {
				p3coeff[exponents[i] + p2exp[j]] += (coefficients[i] * p2coeff[j]);
				p3exp[exponents[i] + p2exp[j]] = (exponents[i] + p2exp[j]); 
			}
		}
		
		for(int k = 0; k < p3coeff.length; k++) {
			if(p3coeff[k] != 0 && k != 0) {
				coeffCount += 1;
			}
		}
		
		multCoeff = new double[coeffCount];
		multExp = new int[coeffCount];
		
		coeffCount = 0;
		
		for(int l = 0; l < p3coeff.length; l++) {
			if(p3coeff[l] != 0) {
				multCoeff[coeffCount] = p3coeff[l];
				multExp[coeffCount] = p3exp[l];
				coeffCount += 1;
			}
		}
		
		Polynomial p = new Polynomial(multCoeff, multExp);
		return p;
	}

	public void saveToFile(String fileName) throws FileNotFoundException, IOException {
		File fName = new File(fileName);
		fName.createNewFile();
		String newPoly = "";
		String newTerm;
		for(int i = 0; i < coefficients.length; i++) {
			newTerm = "";
			newTerm = newTerm + Double.toString(coefficients[i]);
			if(exponents[i] != 0){ 
				newTerm = newTerm + 'x';
			}
			if(exponents[i] > 1) {
				newTerm = newTerm + Integer.toString(exponents[i]);
			}
			if(i < coefficients.length - 1 && coefficients[i+1] > 0){ //setting up for negative numbers
				newTerm = newTerm + '+';
			}
			newPoly = newPoly + newTerm;
		}
		FileWriter myWriter = new FileWriter(fName);
	    myWriter.write(newPoly);
	    myWriter.close();
	}

	











}
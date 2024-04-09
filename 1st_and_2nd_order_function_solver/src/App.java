import java.util.Arrays;
public class App {    
    public static void main(String[] args) throws Exception {
        //Initialize the printer
        Printer printer = new Printer();

        // First Order
        FirstOrder equation = new FirstOrder(1, 2.2);
        System.out.println(equation.getValue(12));
        printer.printRoot(equation.getRoot());
        
        //Second order
        System.out.println("Now we are doing 2nd order, testing 2 solutions ");
        SecondOrder second = new SecondOrder(1, 0, -9);
        System.out.println(second.getValue(1));
        printer.printRoot(second.getRoot());

        // One solotuin
        System.out.println("Now tesing one solution: ");
        SecondOrder test1 = new SecondOrder(1, 2, 1);
        printer.printRoot(test1.getRoot());

        // No solution
        System.out.println("Now tesing no solution: ");
        SecondOrder test2 = new SecondOrder(1, 1, 1);
        printer.printRoot(test2.getRoot());
    }
}


class FirstOrder{
    // Constructor
    public FirstOrder(double b, double c){
        this.b = b;
        this.c = c;
    }

    // Getters
    public double getb(){
        return b; 
    }
    public double getc(){ return c; }

    //getRoot
    public double[] getRoot(){
        double[] empty = {};
        double[] root = {(-1) * c / b};
        if(b == 0){
            return empty;
        }
        return root;
    }

    //getValue
    public double getValue(double x){
        return b*x + c;
    }

    // Instance variables
    private double b;
    private double c;
}


class SecondOrder extends FirstOrder{
    //Constructor
    public SecondOrder(double a, double b, double c){
        super(b, c);
        this.a = a;
    }

    // getRoot
    @Override
    public double[] getRoot(){
        double judger = super.getb()*super.getb() - 4*a*super.getc();
        double delta = Math.sqrt(judger);
        if(judger < 0){
            double[] empty = {};
            return empty;
        }
        else if(judger == 0){
            double[] oneSolution = {(-1)*super.getb() / 2 / a};
            return oneSolution;
        }
        double [] twoSolutions = { ((-1)*super.getb() + delta) / 2 / a, ((-1)*super.getb() - delta) / 2 / a};
        return twoSolutions;
    }

    // getValue
    public double getValue(double x){
        return a*x*x + super.getb()*x + super.getc();
    }

    // Instance variable
    private double a;
}

class Printer{
    public void printRoot(double[] solutions){
        if(solutions.length == 0){
            System.out.println("No solution");
        }
        else{
            System.out.println(Arrays.toString(solutions));
        }
    }
}
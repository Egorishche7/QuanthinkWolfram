package by.quantumquartet.quanthink.math;

import java.util.Objects;

public class Equations {

    private final static String[] order = {"e", "+-", "*", "1"};

    public static String SolveEquation(String equation){
        String tmp = equation;
        if (Objects.equals(tmp, ""))
            throw new IllegalArgumentException("Nothing to solve");
        for (String s : order) {
            switch (s) {
                case "e":
                    tmp = UtilFunctions.convertConstToValues(tmp);
                    tmp = UtilFunctions.checkFloatPoints(tmp);

                    break;
                case "+-":
                    tmp = UtilFunctions.reduceSumSub(tmp);
                    break;
                case "1":
                    tmp = UtilFunctions.addOneBeforeX(tmp);
                    break;
                case "*":
                    tmp = UtilFunctions.SimplifyMul(tmp);
                    break;
            }
        }
        double[] coeffs = classifyEquation(tmp);
        StringBuilder roots = new StringBuilder();
        int pow = 0;
        for (int i = 5; i >= 0; i--)
        {
            if(coeffs[i] != 0){
              pow = i;
              break;
            }

        }
        switch (pow) {
            case 0:
                throw new IllegalArgumentException("Not an equation");
            case 1:
                roots = new StringBuilder(String.valueOf(-coeffs[0] / coeffs[1]));
                break;
            case 2:
                roots = new StringBuilder(solveSquareEquation(coeffs[1] / coeffs[2], coeffs[0] / coeffs[2]));
                break;
            case 3:
                roots = new StringBuilder(solveCubeEquation(coeffs[2] / coeffs[3], coeffs[1] / coeffs[3], coeffs[0] / coeffs[3]));
                break;
            case 4:
                //                eqClass = classifyEquation(tmp);
                break;
            case 5:
                //                roots = classifyEquation(tmp);
                break;
        }
        String[] rootsArr = roots.toString().split(" ");
        roots = new StringBuilder();
        for (String s : rootsArr) {
            if (UtilFunctions.checkDouble(Double.parseDouble(s))) {
                roots.append(Double.parseDouble(s)).append(" ");
            } else
                roots.append(Math.round(Double.parseDouble(s))).append(" ");
        }
        roots.delete(roots.length()-1, roots.length());
        return roots.toString();
    }

    private static double[] classifyEquation(String equation){
        double[] eqClass  = {0, 0, 0, 0, 0, 0};
        int start = -1;
        boolean n_digit = false;
        char prev_char = ' ';
        for (int i = 0 ; i < equation.length(); i++) {
                if (Character.isDigit(equation.charAt(i)) && !n_digit){
                    n_digit = true;
                    if (i > 0)
                        prev_char = equation.charAt(i - 1);
                    start = i;
                }
                else if (equation.charAt(i) == 'x'){
                    n_digit = false;
                    start = -1;
                    prev_char = ' ';
                }
                if (i == equation.length() - 1 && n_digit && prev_char != '^')
                {
                    eqClass[0] = Double.parseDouble(equation.substring(start));
                    if (equation.charAt(start - 1) == '-')
                        eqClass[0] *= -1;
                    break;
                }
                else if (!Character.isDigit(equation.charAt(i)) && equation.charAt(i) != '.' && n_digit && prev_char != '^'){
                    eqClass[0] = Double.parseDouble(equation.substring(start, i));
                    if (start > 1 && equation.charAt(start - 1) == '-')
                        eqClass[0] *= -1;
                    break;
                }

        }
        int previous = -1;
        while (true)
        {
            double number = 0;
            int index = equation.indexOf("x", previous + 1);
            if (index == -1)
                break;
            for (int i = index - 1; i >= 0  ; i--)
            {
                if (i == 0)
                    number = Double.parseDouble(equation.substring(i, index));
                if (!Character.isDigit(equation.charAt(i)) && equation.charAt(i) != '.')
                {
                    number = Double.parseDouble(equation.substring(i + 1, index));
                    if (equation.charAt(i) == '-')
                        number *= -1;
                    break;
                }
            }
            for (int i = index; i < equation.length(); i++)
                if (!Character.isDigit(equation.charAt(i)) && equation.charAt(i) != '.') {
                    if (equation.charAt(i) == '^') {
                        for (int j = i + 1; j < equation.length(); j++) {
                            if (j == equation.length() - 1)
                            {
                                if (UtilFunctions.checkDouble(Double.parseDouble(equation.substring(i + 1, j))))
                                    throw new IllegalArgumentException("Equation class can't be defined");
                                eqClass[Integer.parseInt(equation.substring(i + 1))] = number;
                            }
                            if (!Character.isDigit(equation.charAt(j)) && equation.charAt(j) != '.') {
                                if (UtilFunctions.checkDouble(Double.parseDouble(equation.substring(i + 1, j))))
                                    throw new IllegalArgumentException("Equation class can't be defined");
                                eqClass[Integer.parseInt(equation.substring(i + 1, j))] = number;
                                break;
                            }
                        }
                        break;
                    }
                    else
                        eqClass[1] = number;
                }
            previous = index;
        }
        return eqClass;
    }


    private static String solveSquareEquation(double b, double c) {
        double D = Math.pow(b,2) - 4 * c;
        if (D > 0) {
            D = Math.sqrt(D);
            double x1 = -0.5 * b + D / 2.0;
            double x2  = -0.5 * b - D / 2.0;
            if (x1 < x2)
                return x1 + " " + x2;
            else
                return x2 + " " + x1;

        }
        else if (D == 0)
            return String.valueOf(-b / 2.0);
        else
            throw new ArithmeticException("No root Found");
    }


    private static String solveCubeEquation(double b, double c, double d) {
        double a2 = b*b;
        double q  = (a2 - 3*c)/9;
        double r  = (b*(2*a2-9*c) + 27*d)/54;
        double x1;
        double x2;
        double x3;
        if (Math.abs(q) < UtilFunctions.getDelta()) {
            if (Math.abs(r) < UtilFunctions.getDelta()) {
                x1 = -b/3;
                return String.valueOf(x1);
            }

            x1 = rootCube(-2 * r);	// Sebastien Berthet <seb.berthet@gmail.com>
            x2 = x1 * 0.5;
            x3 = x1 * Math.sqrt(3.0) / 2;
            return x1 + " " + x2 + "±"+ Math.abs(x3) +"i";
        }
        // now favs(q)>eps
        double r2 = r*r;
        double q3 = q*q*q;
        double A,B;
        if (r2 <= (q3 + UtilFunctions.getDelta())) {
            double t=r/Math.sqrt(q3 + UtilFunctions.getDelta());
            if(t < -1) t=-1;
            if(t > 1) t= 1;
            t = Math.acos(t);
            b /= 3; q=-2*Math.sqrt(q);
            x1 = q * Math.cos(t / 3) - b;
            x2 = q * Math.cos((t + 2 * Math.PI)/3) - b;
            x3 = q * Math.cos((t - 2 * Math.PI)/3) - b;
            if (x1 > x2 && x2 > x3) {
                    return x3 + " " + x2 + " " + x1;
            }
            else if (x3 > x2 && x2 > x1) {
                return x1 + " " + x2 + " " + x3;
            }
            else if (x2 > x3 && x3 > x1) {
                return x1 + " " + x3 + " " + x2;
            }
            else if (x1 > x3 && x3 > x2) {
                return x2 + " " + x3 + " " + x1;
            }
            else if (x3 > x1 && x1 > x2) {
                return x2 + " " + x1 + " " + x3;
            }
            else
                return x3 + " " + x1 + " " + x2;

        } else {
            A = -rootCube(Math.abs(r) + Math.sqrt(r2-q3));
            if( r<0 ) A=-A;
            B = (A == 0 ? 0 : q/A);

            b/=3;
            x1 = (A + B) - b;
            x2 = -0.5 * (A + B) - b;
            x3 = 0.5 * Math.sqrt(3.0) * (A-B);
            if(Math.abs(x3)< UtilFunctions.getDelta()) {
                return x1 + " " + x2;
            }
            return x1 + " " + x2 + "±"+ Math.abs(x3) +"i";
        }
    }


    static double rootCube(double x)
    {
        double s = 1.0;
        while ( x < 1.0 )
        {
            x *= 8.0;
            s *= 0.5;
        }
        while ( x > 8.0 )
        {
            x *= 0.125;
            s *= 2.0;
        }
        double r = 1.5;
        int i = 6;
        while (i > 0)
        {
            r -= 1.0/3.0 * ( r - x / ( r * r ) );
            i--;
        }
        return r * s;
    }
}

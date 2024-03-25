package by.quantumquartet.quanthink.math;

import java.util.Objects;

public class BasicArithmetic {

    private static final double delta = 1e-8;
    private final static String[] order = {"e","(", "^", "*", "+"};
    private final static char PI = 'π';
    private final static char EXP = 'e';

    private static Boolean checkDouble(double value){
        return Math.abs(Math.round(value) - value) > delta;
    }

    public static String SolveExpression(String expr){
        String tmp = expr;
        if (Objects.equals(tmp, ""))
            return "0";
        for (String s : order) {
            switch (s) {
                case "e":
                    tmp = ConvertConstToValues(tmp);
                    tmp = checkFloatPoints(tmp);
                    tmp = checkMulBrackets(tmp);
                    break;
                case "(":
                    while (true) {
                        int begin = -1;
                        int end = -1;
                        for (int i = 0; i < tmp.length(); i++) {
                            if (tmp.charAt(i) == '(') {
                                begin = i;
                                continue;
                            }
                            if (tmp.charAt(i) == ')') {
                                end = i;
                                break;
                            }
                        }
                        if ((begin != -1 && end == -1) || (begin == -1 && end != -1))
                            throw new IllegalArgumentException("Numbers of left and right brackets must be equal");
                        if (begin == end)
                            break;
                        else
                            tmp = tmp.substring(0, begin) + SolveExpression(tmp.substring(begin + 1, end))
                                    + tmp.substring(end + 1);
                    }
                    break;
                case "^":
                    tmp = SolvePow(tmp);
                    break;
                case "*":
                    tmp = SolveMulDiv(tmp);
                    break;
                case "+":
                    tmp = SolveSumSub(tmp);
                    break;
            }
            tmp = ReduceSumSub(tmp);
        }
        if (checkDouble(Double.parseDouble(tmp))) {
            return String.valueOf(Double.parseDouble(tmp));
        }
        else
            return String.valueOf(Math.round(Double.parseDouble(tmp)));
    }

    private static String checkMulBrackets(String expr){
        String tmp = expr;
        int previous = -1;
        while (true)
        {
            int index = tmp.indexOf('(', previous + 1);
            if (index == -1)
                break;
            if (index != 0 && Character.isDigit(tmp.charAt(index-1)))
                tmp = tmp.substring(0, index) + "*" + tmp.substring(index);
            previous = index + 1;
        }
        previous = -1;
        while (true)
        {
            int index = tmp.indexOf(')', previous + 1);
            if (index == -1)
                break;
            if (index != tmp.length() - 1 && (Character.isDigit(tmp.charAt(index+1)) || tmp.charAt(index+1) == '('))
                tmp = tmp.substring(0, index + 1) + "*" + tmp.substring(index + 1);
            previous = index + 2;
        }
        return tmp;
    }

    private static String ConvertConstToValues(String expr){
        String tmp = expr;
        while (true)
        {
            int index = tmp.indexOf(PI);
            if (index == -1)
                break;
            tmp = tmp.substring(0, index) + Math.PI + tmp.substring(index + 1);
        }
        while (true)
        {
            int index = tmp.indexOf(EXP);
            if (index == -1)
                break;
            tmp = tmp.substring(0, index) + Math.E + tmp.substring(index + 1);
        }
        return tmp;
    }

    private static String checkFloatPoints(String expr){
        String tmp = expr;
        int previous = -1;
        while (true)
        {
            int index = tmp.indexOf('.',previous + 1);
            if (index == -1)
                break;
            if (index == tmp.length() - 1 || !Character.isDigit(tmp.charAt(index + 1)))
                tmp = tmp.substring(0, index + 1) + '0' + tmp.substring(index + 1);
            previous = index + 1;
        }
        previous = -1;
        while (true)
        {
            int index = tmp.indexOf('.',previous + 1);
            if (index == -1)
                break;
            if (index == 0)
                tmp = '0' + tmp.substring(index);
            else if (!Character.isDigit(tmp.charAt(index - 1)))
                tmp = tmp.substring(0, index) + '0' + tmp.substring(index);
            previous = index + 1;
        }
        previous = tmp.indexOf('.');
        while (true)
        {
            int index = tmp.indexOf('.',previous + 1);
            if (index == -1)
                break;
            boolean check = false;
            for (int i = previous + 1; i < index; i++){
                if (!Character.isDigit(tmp.charAt(i)))
                {
                    check = true;
                    break;
                }
            }
            if (!check)
                throw new IllegalArgumentException("Error");
            previous = index;
        }
        return tmp;
    }
    private static String SolveMulDiv(String expr) {
        String tmp = expr;
        while (true)
        {
            int ind_operation_begin = -1;
            int ind_operation_end = -1;
            int check_mul_del = -1;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' )
                {
                    ind_operation_begin = i;
                    continue;
                }
                if (tmp.charAt(i) == '*' || tmp.charAt(i) == '/'){
                    check_mul_del = i;
                    break;
                }

            }
            if (check_mul_del == -1)
                break;
            else{
                for (int i = check_mul_del + 1; i < tmp.length(); i++) {
                    if(tmp.charAt(i) == '+' || tmp.charAt(i) == '-' || tmp.charAt(i) == '*' || tmp.charAt(i) == '/') {
                        if (i - 1 == check_mul_del  && !(tmp.charAt(i) == '*' || tmp.charAt(i) == '/'))
                            continue;
                        ind_operation_end = i;
                        break;
                    }
                }
                if (ind_operation_end == -1)
                    ind_operation_end = tmp.length();
                if (ind_operation_end == check_mul_del + 1 || ind_operation_begin == check_mul_del - 1)
                    throw new ArithmeticException("Error");
                double left = Double.parseDouble(tmp.substring(ind_operation_begin + 1, check_mul_del));
                double right = Double.parseDouble(tmp.substring(check_mul_del + 1, ind_operation_end));
                if (tmp.charAt(check_mul_del) == '*')
                {

                    if (Math.abs(left * right) - 1 > (double)Integer.MAX_VALUE)
                        throw new StackOverflowError("Stack overflow");
                    tmp = tmp.substring(0,ind_operation_begin + 1) + (left * right)
                            + tmp.substring(ind_operation_end);
                }
                else {
                    if (right == 0)
                        throw new ArithmeticException("Can't divide by zero");
                    if (Math.abs(left / right) - 1 > (double)Integer.MAX_VALUE)
                        throw new StackOverflowError("Stack overflow");
                    tmp = tmp.substring(0, ind_operation_begin + 1) + (left / right)
                                + tmp.substring(ind_operation_end);

                }

            }
        }
        return tmp;
    }

    private static String SolvePow(String expr){
        String tmp = expr;
        while (true)
        {
            int ind_operation_begin = -1;
            int ind_operation_end = -1;
            int check_pow = -1;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' || tmp.charAt(i) == '*' || tmp.charAt(i) == '/')
                {
                    ind_operation_begin = i;
                    continue;
                }
                if (tmp.charAt(i) == '^'){
                    check_pow = i;
                    break;
                }

            }
            if (check_pow == -1)
                break;
            else{
                for (int i = check_pow + 1; i < tmp.length(); i++) {
                    if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' || tmp.charAt(i) == '*' || tmp.charAt(i) == '/' || tmp.charAt(i) == '^')
                    {
                        if((tmp.charAt(i) == '+' || tmp.charAt(i) == '-') && i - 1 == check_pow)
                            continue;
                        ind_operation_end = i;
                        break;
                    }
                }
                if (ind_operation_end == -1)
                    ind_operation_end = tmp.length();
                if (ind_operation_end == check_pow + 1 || ind_operation_begin == check_pow - 1)
                    throw new ArithmeticException("Error");
                double basis = Double.parseDouble(tmp.substring(ind_operation_begin + 1, check_pow));
                double degree = Double.parseDouble(tmp.substring(check_pow + 1, ind_operation_end));
                if (Math.abs(Math.pow(basis, degree)) - 1 > Integer.MAX_VALUE)
                    throw new StackOverflowError("Stack overflow");
                tmp = tmp.substring(0,ind_operation_begin + 1) + Math.pow(basis, degree)
                        + tmp.substring(ind_operation_end);
            }
        }
        return tmp;
    }

    private static String SolveSumSub(String expr){
        String tmp = expr;
        while (true)
        {
            int ind_operation_begin = 0;
            int ind_operation_end = -1;
            int check_sum_sub = -1;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' )
                {
                    if (i == 0)
                        continue;
                    check_sum_sub = i;
                    break;
                }

            }
            if (check_sum_sub == -1)
                break;
            else{
                for (int i = check_sum_sub + 1; i < tmp.length(); i++) {
                    if((tmp.charAt(i) == '+' || tmp.charAt(i) == '-')) {
                        if (i - 1 == check_sum_sub)
                            continue;
                        ind_operation_end = i;
                        break;
                    }
                }
                if (ind_operation_end == -1)
                    ind_operation_end = tmp.length();
                double left = Double.parseDouble(tmp.substring(ind_operation_begin, check_sum_sub));
                double right = Double.parseDouble(tmp.substring(check_sum_sub + 1, ind_operation_end));
                if (tmp.charAt(check_sum_sub) == '+')
                {
                    if (Math.abs(left + right) - 1  > Integer.MAX_VALUE)
                        throw new StackOverflowError("Stack overflow");
                    tmp = tmp.substring(0,ind_operation_begin) + (left + right)
                            + tmp.substring(ind_operation_end);
                }
                else {
                    if (Math.abs(left - right) - 1 > Integer.MAX_VALUE)
                        throw new StackOverflowError("Stack overflow");
                    tmp = tmp.substring(0,ind_operation_begin) + (left - right)
                            + tmp.substring(ind_operation_end);
                }

            }
        }
        return tmp;
    }

    private static String ReduceSumSub(String expr){
        String tmp = expr;
        while (true) {
            int ind = tmp.indexOf("--");
            if (ind == -1)
                break;
            tmp = tmp.substring(0, ind) + "+" + tmp.substring(ind + 2);
        }
        while (true) {
            int ind = tmp.indexOf("++");
            if (ind == -1)
                break;
            tmp = tmp.substring(0, ind) + "+" + tmp.substring(ind + 2);
        }
        while (true) {
            int ind = tmp.indexOf("-+");
            if (ind == -1)
                break;
            tmp = tmp.substring(0, ind) + "-" + tmp.substring(ind + 2);
        }
        while (true) {
            int ind = tmp.indexOf("+-");
            if (ind == -1)
                break;
            tmp = tmp.substring(0, ind) + "-" + tmp.substring(ind + 2);
        }
        if(tmp.charAt(0) == '+')
            tmp = tmp.substring(1);
        return tmp;
    }
}

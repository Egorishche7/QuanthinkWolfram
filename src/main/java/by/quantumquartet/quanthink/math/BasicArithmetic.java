package by.quantumquartet.quanthink.math;

public class BasicArithmetic {

    private final static String[] order = {"e","(", "^", "*", "+"};
    private final static char PI = 'π';
    private final static char EXP = 'e';
    private static Boolean checkDouble(double value){
        return Math.floor(value) - value != 0;
    }

    public static String SolveExpression(String expr){
        String tmp = expr;
        for (String s : order) {
            switch (s) {
                case "(":
                    while (true) {
                        int begin = 0;
                        int end = 0;
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
                        if (begin == end)
                            break;
                        else
                            tmp = tmp.substring(0, begin) + SolveExpression(tmp.substring(begin + 1, end))
                                    + tmp.substring(end + 1);
                    }
                    break;
                case "e":
                    tmp = ConvertConstToValues(tmp);
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
        }
        if (checkDouble(Double.parseDouble(tmp)))
            return tmp;
        else
            return String.valueOf((int)Double.parseDouble(tmp));
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

    private static String SolveMulDiv(String expr) {
        String tmp = expr;
        while (true)
        {
            int ind_operation_begin = 0;
            boolean operation_begin = false;
            int ind_operation_end = 0;
            int check_mul_del = 0;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' )
                {
                    ind_operation_begin = i;
                    operation_begin = true;
                    continue;
                }
                if (tmp.charAt(i) == '*' || tmp.charAt(i) == '/'){
                    check_mul_del = i;
                    break;
                }

            }
            if (check_mul_del == 0)
                break;
            else{
                for (int i = check_mul_del; i < tmp.length(); i++) {
                    if((tmp.charAt(i) == '+' || tmp.charAt(i) == '-')) {
                        if (i - 1 == check_mul_del)
                            continue;
                        ind_operation_end = i;
                        break;
                    }
                }
                if (ind_operation_end == 0)
                    ind_operation_end = tmp.length();
                double left;
                if (operation_begin) {
                    left = Double.parseDouble(tmp.substring(ind_operation_begin + 1, check_mul_del));
                    ind_operation_begin++;
                }
                else
                    left = Double.parseDouble(tmp.substring(ind_operation_begin, check_mul_del));
                double right = Double.parseDouble(tmp.substring(check_mul_del + 1, ind_operation_end));
                if (tmp.charAt(check_mul_del) == '*')
                {
                    tmp = tmp.substring(0,ind_operation_begin) + (left * right)
                            + tmp.substring(ind_operation_end);
                }
                else {
                    tmp = tmp.substring(0,ind_operation_begin) + (left / right)
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
            int ind_operation_begin = 0;
            boolean operation_begin = false;
            int ind_operation_end = 0;
            int check_pow = 0;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' || tmp.charAt(i) == '*' || tmp.charAt(i) == '/')
                {
                    ind_operation_begin = i;
                    operation_begin = true;
                    continue;
                }
                if (tmp.charAt(i) == '^'){
                    check_pow = i;
                    break;
                }

            }
            if (check_pow == 0)
                break;
            else{
                for (int i = check_pow; i < tmp.length(); i++) {
                    if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' || tmp.charAt(i) == '*' || tmp.charAt(i) == '/')
                    {
                        if((tmp.charAt(i) == '+' || tmp.charAt(i) == '-') && i - 1 == check_pow)
                            continue;
                        ind_operation_end = i;
                        break;
                    }
                }
                if (ind_operation_end == 0)
                    ind_operation_end = tmp.length();
                double basis;
                if (operation_begin) {
                    basis = Double.parseDouble(tmp.substring(ind_operation_begin + 1, check_pow));
                    ind_operation_begin++;
                }
                else
                    basis = Double.parseDouble(tmp.substring(ind_operation_begin, check_pow));
                double degree = Double.parseDouble(tmp.substring(check_pow + 1, ind_operation_end));
                tmp = tmp.substring(0,ind_operation_begin) + Math.pow(basis, degree)
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
            int ind_operation_end = 0;
            int check_sum_sub = 0;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '+' || tmp.charAt(i) == '-' )
                {
                    if (i == 0)
                        continue;
                    check_sum_sub = i;
                    break;
                }

            }
            if (check_sum_sub == 0)
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
                if (ind_operation_end == 0)
                    ind_operation_end = tmp.length();
                double left;
                left = Double.parseDouble(tmp.substring(ind_operation_begin, check_sum_sub));
                double right = Double.parseDouble(tmp.substring(check_sum_sub + 1));
                if (tmp.charAt(check_sum_sub) == '+')
                {
                    tmp = tmp.substring(0,ind_operation_begin) + (left + right)
                            + tmp.substring(ind_operation_end);
                }
                else {
                    tmp = tmp.substring(0,ind_operation_begin) + (left - right)
                            + tmp.substring(ind_operation_end);
                }

            }
        }
        return tmp;
    }
}

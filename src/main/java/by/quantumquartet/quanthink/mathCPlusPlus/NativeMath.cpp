#define _USE_MATH_DEFINES

#include <string>
#include <exception>
#include <stdexcept>
#include <limits>
#include <math.h>
#include "by_quantumquartet_quanthink_mathCPlusPlus_NativeMathLib.h"


//This is how we represent a Java exception already in progress
struct ThrownJavaException : std::runtime_error {
    ThrownJavaException() : std::runtime_error("") {}
    ThrownJavaException(const std::string& msg) : std::runtime_error(msg) {}
};

const double DELTA = 1e-8;
const char PI = 'π';
const char EXP = 'e';

const char order[] = { 'e','(', '^', '*', '+'};
std::string SolveExpression(std::string expr);
std::string SolveMulDiv(std::string expr);
std::string SolvePow(std::string expr);
std::string SolveSumSub(std::string expr);
bool CheckDouble(double value);
std::string FloatRemover(std::string value);
double GetDelta();
std::string CheckMulBrackets(std::string expr);
std::string ConvertConstToValues(std::string expr);
std::string AddOneBeforeX(std::string expr);
std::string ReduceSumSub(std::string expr);
std::string CheckFloatPoints(std::string expr);
std::string SimplifyMul(std::string expr);

//JNIEXPORT jstring JNICALL Java_by_quantumquartet_quanthink_mathCPlusPlus_NativeMathLib_solveExpressionC
//(JNIEnv*, jobject, jstring) {
//}

JNIEXPORT jstring JNICALL Java_by_quantumquartet_quanthink_mathCPlusPlus_NativeMathLib_solveExpressionC
  (JNIEnv *env, jobject, jstring jStr){
      const jclass stringClass = env->GetObjectClass(jStr);
      const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
      const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

      size_t length = (size_t) env->GetArrayLength(stringJbytes);
      jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

      std::string ret = std::string((char *)pBytes, length);
      env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

      env->DeleteLocalRef(stringJbytes);
      env->DeleteLocalRef(stringClass);
    return env->NewStringUTF(SolveExpression(ret).c_str());
  }

std::string SolveExpression(std::string expr) {
    std::string tmp = expr;

    if (tmp == "")
        return "0";
    for (int i = 0; i < (sizeof(order) / sizeof(*order)); i++) {
    char item = order[i];
        switch (item) {
        case 'e':
            tmp = ConvertConstToValues(tmp);
            tmp = CheckFloatPoints(tmp);
            tmp = CheckMulBrackets(tmp);
            break;
        case '(':
            while (true) {
                int begin = -1;
                int end = -1;
                for (int i = 0; i < tmp.length(); i++) {
                    if (tmp[i] == '(') {
                        begin = i;
                        continue;
                    }
                    if (tmp[i] == ')') {
                        end = i;
                        break;
                    }
                }
                if ((begin != -1 && end == -1) || (begin == -1 && end != -1))
                    throw new std::runtime_error("numbers of left and right brackets must be equal");
                if (begin == end)
                    break;
                else
                    tmp = tmp.substr(0, begin) + SolveExpression(tmp.substr(begin + 1, end))
                    + tmp.substr(end + 1);
            }
            break;
        case '^':
            tmp = SolvePow(tmp);
            break;
        case '*':
            tmp = SolveMulDiv(tmp);
            break;
        case '+':
            tmp = SolveSumSub(tmp);
            break;
        }
        tmp = ReduceSumSub(tmp);
    }
    return FloatRemover(tmp);
}


std::string SolveMulDiv(std::string expr) {
    std::string tmp = expr;
    while (true)
    {
        int ind_operation_begin = -1;
        int ind_operation_end = -1;
        int check_mul_del = -1;
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp[i] == '+' || tmp[i] == '-')
            {
                ind_operation_begin = i;
                continue;
            }
            if (tmp[i] == '*' || tmp[i] == '/') {
                check_mul_del = i;
                break;
            }

        }
        if (check_mul_del == -1)
            break;
        else {
            for (int i = check_mul_del + 1; i < tmp.length(); i++) {
                if (tmp[i] == '+' || tmp[i] == '-' || tmp[i] == '*' || tmp[i] == '/') {
                    if (i - 1 == check_mul_del && !(tmp[i] == '*' || tmp[i] == '/'))
                        continue;
                    ind_operation_end = i;
                    break;
                }
            }
            if (ind_operation_end == -1)
                ind_operation_end = tmp.length();
            if (ind_operation_end == check_mul_del + 1 || ind_operation_begin == check_mul_del - 1)
                throw new std::runtime_error("Error");
            double left = std::stod(tmp.substr(ind_operation_begin + 1, check_mul_del));
            double right = std::stod(tmp.substr(check_mul_del + 1, ind_operation_end));
            if (tmp[check_mul_del] == '*')
            {

                if ( abs(left * right) - 1 > (double)std::numeric_limits<int>::max())
                    throw new std::runtime_error("Stack overflow");
                tmp = tmp.substr(0, ind_operation_begin + 1) + std::to_string(left * right)
                    + tmp.substr(ind_operation_end);
            }
            else {
                if (right == 0)
                    throw new std::runtime_error("Can't divide by zero");
                if (abs(left / right) - 1 > (double)std::numeric_limits<int>::max())
                    throw new std::runtime_error("Stack overflow");
                tmp = tmp.substr(0, ind_operation_begin + 1) + std::to_string(left / right)
                    + tmp.substr(ind_operation_end);

            }

        }
    }
    return tmp;
}

std::string SolvePow(std::string expr) {
    std::string tmp = expr;
    while (true)
    {
        int ind_operation_begin = -1;
        int ind_operation_end = -1;
        int check_pow = -1;
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp[i] == '+' || tmp[i] == '-' || tmp[i] == '*' || tmp[i] == '/')
            {
                ind_operation_begin = i;
                continue;
            }
            if (tmp[i] == '^') {
                check_pow = i;
                break;
            }

        }
        if (check_pow == -1)
            break;
        else {
            for (int i = check_pow + 1; i < tmp.length(); i++) {
                if (tmp[i] == '+' || tmp[i] == '-' || tmp[i] == '*' || tmp[i] == '/' || tmp[i] == '^')
                {
                    if ((tmp[i] == '+' || tmp[i] == '-') && i - 1 == check_pow)
                        continue;
                    ind_operation_end = i;
                    break;
                }
            }
            if (ind_operation_end == -1)
                ind_operation_end = tmp.length();
            if (ind_operation_end == check_pow + 1 || ind_operation_begin == check_pow - 1)
                throw new std::runtime_error("Error");
            double basis = std::stod(tmp.substr(ind_operation_begin + 1, check_pow));
            double degree = std::stod(tmp.substr(check_pow + 1, ind_operation_end));
            if (abs(pow(basis, degree)) - 1 > std::numeric_limits<int>::max())
                throw new std::runtime_error("Stack overflow");
            tmp = tmp.substr(0, ind_operation_begin + 1) + std::to_string(pow(basis, degree))
                + tmp.substr(ind_operation_end);
        }
    }
    return tmp;
}

std::string SolveSumSub(std::string expr) {
    std::string tmp = expr;
    while (true)
    {
        int ind_operation_begin = 0;
        int ind_operation_end = -1;
        int check_sum_sub = -1;
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp[i] == '+' || tmp[i] == '-')
            {
                if (i == 0)
                    continue;
                check_sum_sub = i;
                break;
            }

        }
        if (check_sum_sub == -1)
            break;
        else {
            for (int i = check_sum_sub + 1; i < tmp.length(); i++) {
                if ((tmp[i] == '+' || tmp[i] == '-')) {
                    if (i - 1 == check_sum_sub)
                        continue;
                    ind_operation_end = i;
                    break;
                }
            }
            if (ind_operation_end == -1)
                ind_operation_end = tmp.length();
            double left = std::stod(tmp.substr(ind_operation_begin, check_sum_sub));
            double right = std::stod(tmp.substr(check_sum_sub + 1, ind_operation_end));
            if (tmp[check_sum_sub] == '+')
            {
                if (abs(left + right) - 1 > std::numeric_limits<int>::max())
                    throw new std::runtime_error("Stack overflow");
                tmp = tmp.substr(0, ind_operation_begin) + std::to_string(left + right)
                    + tmp.substr(ind_operation_end);
            }
            else {
                if (abs(left - right) - 1 > std::numeric_limits<int>::max())
                    throw new std::runtime_error("Stack overflow");
                tmp = tmp.substr(0, ind_operation_begin) + std::to_string(left - right)
                    + tmp.substr(ind_operation_end);
            }

        }
    }
    return tmp;
}



// util



bool CheckDouble(double value) {
    return abs(round(value) - value) > GetDelta();
}

std::string FloatRemover(std::string value) {
    if (CheckDouble(std::stod(value)))
        return std::to_string(std::stod(value));
    else
        return std::to_string((int)round(std::stod(value)));
}

double GetDelta() {
    return DELTA;
}

std::string CheckMulBrackets(std::string expr) {
    std::string tmp = expr;
    int previous = -1;
    while (true)
    {
        int index = tmp.find('(', previous + 1);
        if (index == -1)
            break;
        if (index != 0 && std::isdigit(tmp[index - 1]))
            tmp = tmp.substr(0, index) + "*" + tmp.substr(index);
        previous = index + 1;
    }
    previous = -1;
    while (true)
    {
        int index = tmp.find(')', previous + 1);
        if (index == -1)
            break;
        if (index != tmp.length() - 1 && (std::isdigit(tmp[index + 1]) || tmp[index + 1] == '('))
            tmp = tmp.substr(0, index + 1) + "*" + tmp.substr(index + 1);
        previous = index + 2;
    }
    return tmp;
}

std::string ConvertConstToValues(std::string expr) {
    std::string tmp = expr;
    while (true)
    {
        int index = tmp.find(PI);
        if (index == -1)
            break;
        tmp = tmp.substr(0, index) + std::to_string(M_PI) + tmp.substr(index + 1);
    }
    while (true)
    {
        int index = tmp.find(EXP);
        if (index == -1)
            break;
        tmp = tmp.substr(0, index) +std::to_string(M_E) + tmp.substr(index + 1);
    }
    return tmp;
}



std::string ReduceSumSub(std::string expr) {
    std::string tmp = expr;
    while (true) {
        int ind = tmp.find("--");
        if (ind == -1)
            break;
        tmp = tmp.substr(0, ind) + "+" + tmp.substr(ind + 2);
    }
    while (true) {
        int ind = tmp.find("++");
        if (ind == -1)
            break;
        tmp = tmp.substr(0, ind) + "+" + tmp.substr(ind + 2);
    }
    while (true) {
        int ind = tmp.find("-+");
        if (ind == -1)
            break;
        tmp = tmp.substr(0, ind) + "-" + tmp.substr(ind + 2);
    }
    while (true) {
        int ind = tmp.find("+-");
        if (ind == -1)
            break;
        tmp = tmp.substr(0, ind) + "-" + tmp.substr(ind + 2);
    }
    if (tmp[0] == '+')
        tmp = tmp.substr(1);
    return tmp;
}

std::string CheckFloatPoints(std::string expr) {
    std::string tmp = expr;
    int previous = -1;
    while (true)
    {
        int index = tmp.find('.', previous + 1);
        if (index == -1)
            break;
        if (index == tmp.length() - 1 || !std::isdigit(tmp[index + 1]))
            tmp = tmp.substr(0, index + 1) + '0' + tmp.substr(index + 1);
        previous = index + 1;
    }
    previous = -1;
    while (true)
    {
        int index = tmp.find('.', previous + 1);
        if (index == -1)
            break;
        if (index == 0)
            tmp = '0' + tmp.substr(index);
        else if (!std::isdigit(tmp[index - 1]))
            tmp = tmp.substr(0, index) + '0' + tmp.substr(index);
        previous = index + 1;
    }
    previous = tmp.find('.');
    while (true)
    {
        int index = tmp.find('.', previous + 1);
        if (index == -1)
            break;
        bool check = false;
        for (int i = previous + 1; i < index; i++) {
            if (!std::isdigit(tmp[i]))
            {
                check = true;
                break;
            }
        }
        if (!check)
            throw new std::runtime_error("Error");
        previous = index;
    }
    return tmp;
}

std::string SimplifyMul(std::string expr) {
    std::string tmp = expr;
    int previous = -1;
    while (true)
    {
        int index = tmp.find('x', previous + 1);
        if (index == -1)
            break;
        if (index != 0 && tmp[index - 1] == '*')
            tmp = tmp.substr(0, index - 1) + tmp.substr(index);
        else if (index != tmp.length() - 1 && tmp[index + 1] == '*')
        {
            int end = tmp.length();
            for (int i = index + 2; i < tmp.length(); i++)
                if (!std::isdigit(tmp[i]))
                {
                    end = i;
                    break;
                }
            if (index > 0)
                tmp = tmp.substr(0, index - 1) + tmp.substr(index + 2, end) + tmp.substr(end);
            else
                tmp = tmp.substr(index + 2, end) + tmp.substr(index, index + 1) + tmp.substr(end);
        }

        previous = index + 1;
    }
    return tmp;
}
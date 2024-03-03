package by.quantumquartet.quanthink.math;

import java.util.*;
import static java.lang.Math.abs;
import static java.lang.String.format;

/**
 * Класс Rational представляет рациональные (несокращаемые) дроби, задаваемые 2-мя целыми
 * числами числами - числителеми и знаменателей.
 * Предоставляет методы для работы с несокращаемыми дробями, включая арифметические операции,
 * реализацию интерфейсов Comparable, Comparator, Iterable и методы для
 * сохранения значений всех полей в строке текста.
 *
 * @author Костецкий Павел
 * @version 1.0
 */
public class Rational implements Comparable<Rational>, Iterable<Object>, Iterator<Object> {

    private int numerator;
    private int denominator;

    /**
     * Создает новый объект Rational с заданными числителем и знаменателем.
     *
     * @throws Exception - в случае неположительного знаменателя выкидывает ошибку
     * @param _numerator числитель дроби
     * @param _denominator знаменатель дроби
     */
    public Rational(int _numerator, int _denominator) throws Exception {
        setNumerator(_numerator);
        setDenominator(_denominator);
        this.reduct();
    }

    /**
     * Возвращает числитель дроби.
     *
     * @return numerator Rational
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Возвращает знаменатель дроби.
     *
     * @return denominator Rational
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Устанавливает числитель дроби.
     *
     * @param _numerator Rational
     */
    public void setNumerator(int _numerator) {
        this.numerator = _numerator;
    }

    /**
     * Устанавливает знаменатель дроби.
     *
     * @exception Exception в случае неположительного знаменателя генерируте ошибку
     * @param _denominator Rational
     */
    public void setDenominator(int _denominator) throws Exception {
        if (_denominator <= 0){
            throw new Exception("Denominator can't be equal or less than zero");
        }
        if (this.numerator != 0)
            this.denominator = _denominator;
        else
            this.denominator = 1;
    }

    /**
     * Функция превращающая дробь в несократимую
     */
    private void reduct() {
        if (this.numerator != 0){
            int x = abs(this.numerator), y = this.denominator;
            while (x != y) {
                if (x > y) {
                    x = x - y;
                }
                else {
                    y = y - x;
                }
            }
            this.numerator /= x;
            this.denominator /= x;
        }

    }

    /**
     * Складавает дробь с входной дробью.
     *
     * @param _r Rational
     */
    public void add(Rational _r) {
        this.numerator = this.numerator * _r.denominator + this.denominator * _r.numerator;
        this.denominator *= _r.denominator;
        this.reduct();
    }

    /**
     * Отнимает от дроби входную дробью.
     *
     * @param _r Rational
     */
    public void substrac(Rational _r) {
        this.numerator = this.numerator * _r.denominator - this.denominator * _r.numerator;
        this.denominator *= _r.denominator;
        this.reduct();
    }

    /**
     * Умножает дробь на входную дробью.
     *
     * @param _r Rational
     */
    public void multiply(Rational _r) {
        this.numerator *= _r.numerator;
        this.denominator *= _r.denominator;
        this.reduct();
    }

    /**
     * Делит дробь на входную дробью.
     *
     * @exception Exception в случае деления на 0 генерируте ошибку
     * @param _r Rational
     */
    public void divide(Rational _r) throws Exception {
        if (_r.numerator == 0)
            throw new Exception("Can't divide by zero");
        if (_r.numerator > 0){
            this.numerator *= _r.denominator;
            this.denominator *= _r.numerator;
        }
        else {
            this.numerator *= -_r.denominator;
            this.denominator *= -_r.numerator;
        }
        this.reduct();
    }

    /**
     * Сравнивает текущую дробь с указанной.
     *
     * @param entry Rational дробь для сравнения
     * @return результат сравнения (-1, 0 or 1)
     */
    @Override
    public int compareTo(Rational entry) {
        if ((this.numerator * entry.denominator) > (this.denominator * entry.numerator)) {
            return 1;
        }
        else if ((this.numerator * entry.denominator) < (this.denominator * entry.numerator)) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /**
     * Возвращает итератор для перебора всех полей объекта Rational.
     *
     * @return итератор для перевобра всех полей объекта
     */
    @Override
    public Iterator<Object> iterator() {
        resetIterator();
        return this;
    }

    private int iterator_idx = -1;

    private void resetIterator() {
        iterator_idx = -1;
    }

    /**
     * Проверяет, есть ли следующий элемент для перебора.
     *
     * @return true если есть следующий элемент; false иначе
     */
    @Override
    public boolean hasNext() {
        return iterator_idx < 1;
    }

    /**
     * Удаляет текущий элемент (операция не поддерживается).
     *
     * @throws UnsupportedOperationException если метод не поддерживается
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Возвращает следующий элемент при переборе всех полей объекта Rational.
     *
     * @return следующий элемент
     * @throws NoSuchElementException если достигнут конец счетчика
     */
    @Override
    public Object next() {
        ++iterator_idx;
        if (iterator_idx == 0) {
            return this.numerator;
        }
        if (iterator_idx == 1) {
            return this.denominator;
        }
        throw new NoSuchElementException();
    }

    /**
     * Возвращает строковое представление объекта Rational.
     *
     * @return строковое представления объекта Rational
     */
    @Override
    public String toString() {
        return format("%s/%s",this.numerator, this.denominator);
    }

    /**
     * Проверяет, равен ли данный объект Rational указанному объекту.
     *
     * @param o объект для сравнения
     * @return true если объекты равны; false иначе
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rational rational = (Rational) o;
        return Double.compare(rational.numerator * this.denominator, this.numerator * rational.denominator) == 0;
    }
}

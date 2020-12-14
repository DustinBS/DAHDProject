package numberlist.objectlist;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * This class represents a single complex number of the form x + yi.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
public final class Complex implements Copiable, Comparable<Complex>, Serializable {

    //fields
    private double real;
    private double imaginary;

    /**
     * Default constructor. Creates a new Complex object. Sets both real and
     * imaginary to zero.
     */
    public Complex() {
        real = 0;
        imaginary = 0;
    }

    /**
     * Full constructor. Creates a new Complex object.
     *
     * @param real the value of the real portion of the complex number
     * @param imaginary the value of the imaginary portion of the complex number
     */
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Provides access to the real portion of the complex number
     *
     * @return the value of the real portion of the complex number
     */
    public double getReal() {
        return real;
    }

    /**
     * Provides access to the imaginary portion of the complex number
     *
     * @return the value of the imaginary portion of the complex number
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Adds the current and the given complex numbers together, and stores the
     * sum in a new Complex object. The current and given complex numbers are
     * not altered in the process.
     *
     * @param other the other complex number to add to this one
     * @return the new Complex object that holds the result of the addition
     */
    public Complex add(Complex other) {
        Complex result = new Complex();
        result.real = this.real + other.real;
        result.imaginary = this.imaginary + other.imaginary;
        return result; //replace this return statement
    }

    /**
     * Subtracts the other complex number from this one, and stores the result
     * in a new Complex object. The current and given complex numbers are not
     * altered in the process.
     *
     * @param other the other complex number to subtract from this one
     * @return the new Complex object that holds the result of the subtraction
     */
    public Complex subtract(Complex other) {
        Complex result = new Complex();
        result.real = this.real - other.real;
        result.imaginary = this.imaginary - other.imaginary;
        return result; //replace this return statement
    }

    /**
     * Provides a string representation of the current complex number, in the
     * form x + yi.
     *
     * @return the string representation of the current complex number
     */
    @Override
    public String toString() {
        BigDecimal bd = new BigDecimal(real);
        bd = bd.round(new MathContext(2));
        real = bd.doubleValue();
        bd = new BigDecimal(imaginary);
        bd = bd.round(new MathContext(2));
        imaginary = bd.doubleValue();
        if (imaginary == 0) {
            return real + "";
        }
        if (real == 0) {
            return imaginary + "i";
        }
        if (imaginary < 0) {
            return real + " - " + (-imaginary) + "i";
        }
        return real + " + " + imaginary + "i";
    }

    /**
     * Returns a deepCopy Complex object: the same real and imaginary values as
     * the caller.
     *
     * @return the copy of the calling object.
     */
    @Override
    public Complex makeDeepCopy() {
        return new Complex(real, imaginary);
    }

    /**
     * Number identifier for current object.
     *
     * @return the number identifier
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.real) ^ (Double.doubleToLongBits(this.real) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.imaginary) ^ (Double.doubleToLongBits(this.imaginary) >>> 32));
        return hash;
    }

    /**
     * Compares the caller with the parameter object via real and imaginary
     * values.
     *
     * @param obj the object to compare to
     * @return true if the caller and the parameter object are equal, false
     * otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Complex other = (Complex) obj;
        if (Double.doubleToLongBits(this.real) != Double.doubleToLongBits(other.real)) {
            return false;
        }
        if (Double.doubleToLongBits(this.imaginary) != Double.doubleToLongBits(other.imaginary)) {
            return false;
        }
        return true;
    }

    /**
     * Compares two Complex objects via their real values or their imaginary
     * values if their reals are equal.
     *
     * @param other the object to compared to
     * @return 1 if this object is greater than the other; 0 if equal; -1 if
     * lesser
     */
    @Override
    public int compareTo(Complex other) {
        int sortReal = Double.compare(this.real, other.real);
        if (sortReal > 0) {
            return 1;
        } else if (sortReal == 0) {
            int sortImaginary = Double.compare(this.imaginary, other.imaginary);
            if (sortImaginary > 0) {
                return 1;
            } else if (sortImaginary == 0) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

}

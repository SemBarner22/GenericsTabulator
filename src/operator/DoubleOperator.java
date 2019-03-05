package operator;

public class DoubleOperator implements Operator<Double> {
    @Override
    public Double parseNumber(String number) {
        return Double.parseDouble(number);
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) {
        return x / y;
    }


    @Override
    public Double max(Double x, Double y) {
        return max(x, y);
    }

    @Override
    public Double min(Double x, Double y) {
        return min(x, y);
    }

    @Override
    public Double mod(Double x, Double y) {
        return x % y;
    }

    @Override
    public Double not(Double x) {
        return -x;
    }

    @Override
    public Double abs(Double x) {
        return Math.abs(x);
    }

    @Override
    public Double sqr(Double x) {
        return x * x;
    }
}

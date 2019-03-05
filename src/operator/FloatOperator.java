package operator;

public class FloatOperator implements Operator<Float> {

    @Override
    public Float parseNumber(String number) {
        return Float.parseFloat(number);
    }

    @Override
    public Float add(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float sub(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float mul(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float max(Float x, Float y) {
        return max(x, y);
    }

    @Override
    public Float min(Float x, Float y) {
        return min(x, y);
    }

    @Override
    public Float mod(Float x, Float y) {
        return x % y;
    }

    @Override
    public Float div(Float x, Float y)  {
        return x / y;
    }

    @Override
    public Float not(Float x) {
        return -x;
    }

    @Override
    public Float abs(Float x) {
        return Math.abs(x);
    }

    @Override
    public Float sqr(Float x) {
        return x * x;
    }
}

package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.MathUtil;

public class NumericLimit {
    public double minimum;
    public double maximum;
    public double current;

    public NumericLimit(double low, double high, double initialValue) {
        this.minimum = low;
        this.maximum = high;

        MathUtil.RangeState rangeOf = MathUtil.isInRange(initialValue, low, high);
        if(rangeOf == MathUtil.RangeState.BELOW_MINIMUM) this.current = low;
        else if(rangeOf == MathUtil.RangeState.WITHIN_RANGE) this.current = initialValue;
        else if(rangeOf == MathUtil.RangeState.ABOVE_MAXIMUM) this.current = high;
    }

    public double getCurrent() {
        return current;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    public MathUtil.RangeState inRange(double a) {
        return MathUtil.isInRange(a, minimum, maximum);
    }

    public double limitNumber(double num) {
        MathUtil.RangeState rangeOf = inRange(num);
        if(rangeOf == MathUtil.RangeState.BELOW_MINIMUM) return getMinimum();
        else if(rangeOf == MathUtil.RangeState.WITHIN_RANGE) return num;
        else if(rangeOf == MathUtil.RangeState.ABOVE_MAXIMUM) return getMaximum();
        else return getMinimum();
    }

    public void setCurrent(double num) {
        this.current = limitNumber(num);
    }
}

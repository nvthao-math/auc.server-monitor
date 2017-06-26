/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.ml.regression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.auc.core.utils.EUtils;

/**
 *
 * @author bigdata
 */
public class LinearRegression {

    protected double intercept;
    protected double slope;
    protected double r2;
    protected double svar00;
    protected double svar01;
    Map<?, ? extends Number> regressionMap;
    Map<Double, Double> dataRegression;

    private enum Type {
        NUMBER, STR;
    };

    // constructor
    public LinearRegression(Map<?, ? extends Number> regressionMap) {
        this.regressionMap = regressionMap;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Double> map = new HashMap<>();
        map.put("3.87", 4.87);
        map.put("3.61", 3.93);
        map.put("4.33", 6.46);
        map.put("3.43", 3.33);
        map.put("3.81", 4.38);
        map.put("3.83", 4.70);
        map.put("3.46", 3.50);
        map.put("3.76", 4.50);
        map.put("3.50", 3.58);
        map.put("3.58", 3.64);
        map.put("4.19", 5.90);
        map.put("3.78", 4.43);
        map.put("3.71", 4.38);
        map.put("3.73", 4.42);
        map.put("3.78", 4.25);
        List<String> list = Arrays.asList("3.87", "3.61", "4.33", "3.43");
        LinearRegression linearRegression = new LinearRegression(map);
        linearRegression._init();
        //
        Map<String, Double> mapR = (Map<String, Double>) linearRegression.predict(list);
        System.out.println(mapR);
    }

    public void _init() throws Exception {
        if (this.regressionMap == null || this.regressionMap.isEmpty()) {
            throw new IllegalArgumentException("Input map to regression null or empty");
        }
        Object keyType = EUtils.firstKey(this.regressionMap);
        if (keyType instanceof String) {
            if (!EUtils.isNumber((String) keyType)) {
                throw new IllegalArgumentException("Key type can not parse to Number");
            }
            this.dataRegression = getRegressionData(this.regressionMap, Type.STR);
        } else if (keyType instanceof Number) {
            this.dataRegression = getRegressionData(this.regressionMap, Type.NUMBER);
        } else {
            throw new IllegalArgumentException("Key type is neither String nor Number");
        }
        this.regressionMap.clear();
        int n = this.dataRegression.size();
        // first pass
        double sumx = 0.0;
        double sumy = 0.0;
        for (Map.Entry<Double, Double> entry : this.dataRegression.entrySet()) {
            sumx += entry.getKey();
            sumy += entry.getValue();
        }
        double xbar = sumx / n;
        double ybar = sumy / n;
        // second pass: compute summary statistics
        double xxbar = 0.0;
        double yybar = 0.0;
        double xybar = 0.0;
        for (Map.Entry<Double, Double> entry : this.dataRegression.entrySet()) {
            xxbar += (entry.getKey() - xbar) * (entry.getKey() - xbar);
            yybar += (entry.getValue() - ybar) * (entry.getValue() - ybar);
            xybar += (entry.getKey() - xbar) * (entry.getValue() - ybar);
        }
        this.slope = xybar / xxbar;
        this.intercept = ybar - this.slope * xbar;
        // more statistical analysis
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (Map.Entry<Double, Double> entry : this.dataRegression.entrySet()) {
            double fit = this.slope * entry.getKey() + this.intercept;
            rss += (fit - entry.getValue()) * (fit - entry.getValue());
            ssr += (fit - ybar) * (fit - ybar);
        }
        //
        int degreesOfFreedom = n - 2;
        this.r2 = ssr / yybar;
        double svar = rss / degreesOfFreedom;
        this.svar01 = svar / xxbar;
        this.svar00 = svar / n + xbar * xbar * this.svar01;
    }

    private Map<Double, Double> getRegressionData(Map<?, ? extends Number> map, Type type) {
        Map<Double, Double> result = new HashMap<>();
        if (type.equals(Type.STR)) {
            for (Object key : map.keySet()) {
                result.put(Double.parseDouble((String) key), map.get(key).doubleValue());
            }
        } else {
            for (Object key : map.keySet()) {
                result.put(((Number) key).doubleValue(), map.get(key).doubleValue());
            }
        }
        return result;
    }

    public double intercept() {
        return intercept;
    }

    public double slope() {
        return slope;
    }

    public double R2() {
        return r2;
    }

    public double interceptStdErr() {
        return Math.sqrt(svar00);
    }

    public double slopeStdErr() {
        return Math.sqrt(svar01);
    }

    public double predict(double x) {
        return this.slope * x + this.intercept;
    }

    public double predict(String x) {
        if (!EUtils.isNumber(x)) {
            throw new IllegalArgumentException("Argument to linear regression is not a number");
        }
        return this.slope * Double.parseDouble(x) + this.intercept;
    }

    public Map<?, ? extends Number> predict(List<?> list) throws Exception {
        Map<?, ? extends Number> result = new HashMap<>();
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Map to regression not null or empty");
        }
        Object key = list.get(0);
        if (key instanceof String) {
            Map<String, Double> map = new HashMap<>();
            for (Object val : list) {
                double ypred = predict((String) val);
                map.put((String) val, ypred);
            }
            result = map;
        } else if (key instanceof Number) {
            Map<Double, Double> map = new HashMap<>();
            for (Object val : list) {
                double ypred = predict((double) val);
                map.put((double) val, ypred);
            }
            result = map;
        } else {
            throw new IllegalArgumentException("Key type is neither String nor Number");
        }
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("%.2f n + %.2f", slope(), intercept()))
                .append(String.format("  (R^2 = %.3f )", R2()))
                .toString();
    }

}

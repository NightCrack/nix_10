package ua.com.alevel.level1.util;

import ua.com.alevel.level1.entity.Dot;

import java.math.BigDecimal;
import java.math.MathContext;

public final class AreaOfTriangular {

    private AreaOfTriangular() {
    }

    private static BigDecimal sizeLine(Dot firstDot, Dot secondDot) {
        long distanceX = firstDot.getCoordinateX() - secondDot.getCoordinateX();
        long distanceY = firstDot.getCoordinateY() - secondDot.getCoordinateY();
        BigDecimal lineSize = new BigDecimal(Math.sqrt((double) (distanceX * distanceX + distanceY * distanceY)));
        return lineSize;
    }

    public static BigDecimal getArea(Dot dotA, Dot dotB, Dot dotC) {
        BigDecimal edgeA = sizeLine(dotA, dotB);
        BigDecimal edgeB = sizeLine(dotA, dotC);
        BigDecimal edgeC = sizeLine(dotB, dotC);
        BigDecimal subLine = (((edgeA.multiply(edgeA)).subtract(edgeB.multiply(edgeB))).add(edgeC.multiply(edgeC))).divide((edgeC.add(edgeC)), MathContext.DECIMAL32);
        BigDecimal height = ((edgeA.multiply(edgeA)).subtract(subLine.multiply(subLine))).sqrt(MathContext.DECIMAL32);
        BigDecimal area = (edgeC.multiply(height)).divide(BigDecimal.valueOf(2), MathContext.DECIMAL32);
        return area;
    }

    public static BigDecimal getArea(Dot[] dots) {
        return getArea(dots[0], dots[1], dots[2]);
    }
}

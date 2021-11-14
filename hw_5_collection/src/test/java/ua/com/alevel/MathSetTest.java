package ua.com.alevel;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MathSetTest {

    private static final MathSet[] base = new MathSet[10];

    private static Number generateNumber() {
        Number returnNumber = (int) (Math.random() * 10);
        return returnNumber;
    }

    private static Number[] generateNumberArray() {
        Number[] returnArray = new Number[(int) (Math.random() * 8 + 2)];
        for (int index = 0; index < returnArray.length; index++) {
            returnArray[index] = generateNumber();
        }
        return returnArray;
    }

    @BeforeAll
    private static void populateBase() {
        for (int index = 0; index < base.length; index++) {
            base[index] = new MathSet(generateNumberArray());
        }
    }

    private Number[][] generateTwoLayerArray() {
        int deep = (int) (Math.random() * 9 + 1);
        Number[][] returnArray = new Number[deep][deep];
        for (int indexA = 0; indexA < deep; indexA++) {
            for (int indexB = 0; indexB < deep; indexB++) {
                returnArray[indexA][indexB] = generateNumber();
            }
        }
        return returnArray;
    }

    private double arrayValueSum(MathSet mathSet) {
        double sumBase = 0;
        for (int index = 0; index < mathSet.arraySize(); index++) {
            sumBase += mathSet.elementsArray[index].doubleValue();
        }
        return sumBase;
    }

    @Order(1)
    @Test
    public void shouldBeMathSetConstructorEmpty() {
        MathSet toCompare = new MathSet();
        for (int index = 0; index < toCompare.elementsArray.length; index++) {
            Assertions.assertNull(toCompare.elementsArray[index]);
        }
    }

    @Order(2)
    @Test
    public void shouldBeMathSetConstructorInt() {
        int capacity = (int) (Math.random() * 10);
        MathSet toCompare = new MathSet(capacity);
        Assertions.assertEquals(capacity, toCompare.elementsArray.length);
        for (int index = 0; index < capacity; index++) {
            Assertions.assertNull(toCompare.elementsArray[index]);
        }
    }

    @Order(3)
    @Test
    public void shouldBeMathSetConstructorArrayOfNumbers() {
        MathSet toCompare = new MathSet(base[0]);
        Assertions.assertEquals(toCompare.arraySize(), base[0].arraySize());
        int size = toCompare.arraySize();
        toCompare.join(base[0], base[1]);
        Assertions.assertEquals(base[0].arraySize() +
                base[1].arraySize() +
                size, toCompare.arraySize());
    }

    @Order(4)
    @Test
    public void shouldBeMathSetConstructorArrayOfSets() {
        Number[] array = generateNumberArray();
        MathSet toCompare = new MathSet(array);
        Assertions.assertEquals(toCompare.arraySize(), array.length);
        Number[][] arrayTwo = generateTwoLayerArray();
        toCompare = new MathSet(arrayTwo);
        int arrayTwoElementsAmount = 0;
        for (int index = 0; index < arrayTwo.length; index++) {
            arrayTwoElementsAmount += arrayTwo[index].length;
        }
        Assertions.assertEquals(arrayTwoElementsAmount, toCompare.arraySize());
        toCompare = new MathSet(base);
        arrayTwoElementsAmount = 0;
        for (int index = 0; index < base.length; index++) {
            arrayTwoElementsAmount += base[index].arraySize();
        }
        Assertions.assertEquals(arrayTwoElementsAmount, toCompare.arraySize());
    }

    @Order(5)
    @Test
    public void shouldBeMathSetIntersection() {
        base[3].intersection(base[0]);
    }

    @Order(6)
    @Test
    public void shouldBeMathSetIntersectionMultiple() {
        base[0] = new MathSet(generateNumberArray());
        base[0].intersection(base[1], base[2]);
    }

    @Order(7)
    @Test
    public void shouldBeMathSetSortAsc() {
        MathSet toCompare = new MathSet(base[1]);
        base[1].sortAsc();
        double sumBase = arrayValueSum(base[1]);
        double sumToCompare = arrayValueSum(toCompare);
        Assertions.assertEquals(sumToCompare, sumBase);
        Assertions.assertEquals(base[1].arraySize(), toCompare.arraySize());
        for (int index = 0; index < toCompare.arraySize() - 1; index++) {
            Assertions.assertFalse(base[1].elementsArray[index].doubleValue() >
                    base[1].elementsArray[index + 1].doubleValue());
        }
    }

    @Order(8)
    @Test
    public void shouldBeMathSetSortAscByIndexes() {
        MathSet toCompare = new MathSet(base[1]);
        base[1].sortAsc(1, (base[1].arraySize() - 1));
        double sumBase = arrayValueSum(base[1]);
        double sumToCompare = arrayValueSum(toCompare);
        Assertions.assertEquals(sumToCompare, sumBase);
        Assertions.assertEquals(base[1].arraySize(), toCompare.arraySize());
        for (int index = 1; index < toCompare.arraySize() - 1; index++) {
            Assertions.assertFalse(base[1].elementsArray[index].doubleValue() >
                    base[1].elementsArray[index + 1].doubleValue());
        }
    }

    @Order(9)
    @Test
    public void shouldBeMathSetSortAscByValue() {
        Number sortNumber = base[1].elementsArray[(int) (Math.random() * base[1].arraySize())];
        base[1].sortAsc(sortNumber);
        for (int index = 0; index < base[1].arraySize() - 1; index++) {
            Assertions.assertTrue(base[1].elementsArray[index].doubleValue() >= sortNumber.doubleValue());
        }
        Number sortNumberTwo = 11;
        base[1].sortAsc(sortNumberTwo);
        for (int index = 0; index < base[1].arraySize() - 1; index++) {
            Assertions.assertTrue(base[1].elementsArray[index].doubleValue() >= sortNumber.doubleValue());
        }
    }

    @Order(10)
    @Test
    public void shouldBeMathSetSortDesc() {
        MathSet toCompare = new MathSet(base[1]);
        base[1].sortDesc();
        double sumBase = arrayValueSum(base[1]);
        double sumToCompare = arrayValueSum(toCompare);
        Assertions.assertEquals(sumToCompare, sumBase);
        Assertions.assertEquals(base[1].arraySize(), toCompare.arraySize());
        for (int index = 0; index < toCompare.arraySize() - 1; index++) {
            Assertions.assertFalse(base[1].elementsArray[index].doubleValue() <
                    base[1].elementsArray[index + 1].doubleValue());
        }
    }

    @Order(11)
    @Test
    public void shouldBeMathSetSortDescByIndexes() {
        MathSet toCompare = new MathSet(base[1]);
        base[1].sortDesc(1, (base[1].arraySize() - 1));
        double sumBase = arrayValueSum(base[1]);
        double sumToCompare = arrayValueSum(toCompare);
        Assertions.assertEquals(sumToCompare, sumBase);
        Assertions.assertEquals(base[1].arraySize(), toCompare.arraySize());
        for (int index = 1; index < base[1].arraySize() - 1; index++) {
            Assertions.assertFalse(base[1].elementsArray[index].doubleValue() <
                    base[1].elementsArray[index + 1].doubleValue());
        }
    }

    @Order(12)
    @Test
    public void shouldBeMathSetSortDescByValue() {
        Number sortNumber;
        if (base[1].elementsArray != null) {
            sortNumber = base[1].elementsArray[(int) (Math.random() * base[1].arraySize())];
        } else {
            sortNumber = -1;
        }
        base[1].sortDesc(sortNumber);
        for (int index = 0; index < base[1].arraySize() - 1; index++) {
            Assertions.assertTrue(base[1].elementsArray[index].doubleValue() <= sortNumber.doubleValue());
        }
        Number sortNumberTwo = 10;
        base[1].sortDesc(sortNumberTwo);
        for (int index = 0; index < base[1].arraySize() - 1; index++) {
            Assertions.assertTrue(base[1].elementsArray[index].doubleValue() <= sortNumber.doubleValue());
        }
    }

    @Order(13)
    @Test
    public void shouldBeMathSetGet() {
        MathSet toCompare = new MathSet();
        Number[] elements = generateNumberArray();
        toCompare.elementsArray = elements;
        toCompare.sortAsc();
        int getIndex = (int) (toCompare.arraySize() * Math.random());
        Number min = toCompare.elementsArray[0];
        Assertions.assertEquals(toCompare.getMin(), min);
        Number max = toCompare.elementsArray[toCompare.elementsArray.length - 1];
        Assertions.assertEquals(toCompare.getMax(), max);
        Assertions.assertEquals(toCompare.get(getIndex), toCompare.elementsArray[getIndex]);
        Assertions.assertTrue((toCompare.getAverage().doubleValue() >=
                toCompare.getMin().doubleValue()) &&
                (toCompare.getAverage().doubleValue() <=
                        toCompare.getMax().doubleValue()));
        if (toCompare.arraySize() % 2 == 1) {
            Assertions.assertEquals(toCompare.elementsArray[toCompare.arraySize() / 2], toCompare.getMedian());
        }
    }

    @Order(13)
    @Test
    public void shouldBeMathSetToArray() {
        MathSet toCompare = new MathSet();
        Number[] elements = generateNumberArray();
        toCompare.elementsArray = elements;
        Assertions.assertArrayEquals(elements, toCompare.toArray());
        int firstIndex = (int) (Math.random() * (elements.length - 1));
        int lastIndex = elements.length - 1;
        Number[] toCompareArray = new Number[lastIndex - firstIndex + 1];
        System.arraycopy(elements, firstIndex, toCompareArray, 0, toCompareArray.length);
        for (int index = 0; index < toCompareArray.length; index++) {
            Assertions.assertEquals(toCompareArray[index], toCompare.toArray(firstIndex, lastIndex)[index]);
        }
        MathSet toCompareTwo = new MathSet(toCompare);
        lastIndex -= 1;
        toCompare.trim(firstIndex, lastIndex);
        for (int index = 0; index < firstIndex; index++) {
            if (toCompare.arraySize() != 0) {
                Assertions.assertEquals(toCompareTwo.elementsArray[index], toCompare.elementsArray[index]);
            }
        }
        for (int index = 0; index < toCompareTwo.arraySize() - lastIndex - 1; index++) {
            if (toCompare.arraySize() != 0) {
                Assertions.assertEquals(toCompareTwo.elementsArray[lastIndex + index + 1],
                        toCompare.elementsArray[firstIndex + index]);
            }
        }
    }

    @Order(14)
    @Test
    public void shouldBeMathSetClear() {
        MathSet toCompare = new MathSet();
        Number[] elements = generateNumberArray();
        toCompare.elementsArray = elements;
        Number[] toRemove = generateNumberArray();
        toCompare.clear(toRemove);
        for (int indexA = 0; indexA < toRemove.length; indexA++) {
            for (int indexB = 0; indexB < toCompare.elementsArray.length; indexB++)
                Assertions.assertNotEquals(String.valueOf(toRemove[indexA]), String.valueOf(toCompare.elementsArray[indexB]));
        }
        toCompare.clear();
        for (int index = 0; index < toCompare.elementsArray.length; index++) {
            Assertions.assertNull(toCompare.elementsArray[index]);
        }
    }

}

package ua.com.alevel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public final class MathSet<Any extends Number> {

    private final int DEFAULT_CAPACITY = 10;
    Number[] elementsArray = new Number[DEFAULT_CAPACITY];

    public MathSet() {
    }

    public MathSet(int capacity) {
        elementsArray = new Number[capacity];
    }

    public MathSet(Number[] numbers) {
        add(numbers);
    }

    public MathSet(Number[]... numbers) {
        elementsArray = numbers[0];
        for (int index = 1; index < numbers.length; index++) {
            add(numbers[index]);
        }
    }

    public MathSet(MathSet mathSet) {
        if (mathSet.elementsArray != null) {
            add(mathSet.elementsArray);
        } else {
            new MathSet();
        }
    }

    public MathSet(MathSet... mathSets) {
        for (int index = 0; index < mathSets.length; index++) {
            if (mathSets[index] != null) {
                add(mathSets[index].elementsArray);
            }
        }

    }

    private int findIndex(Number[] arrayToSearch, Number elementToFind) {
        for (int index = 0; index < arrayToSearch.length; index++) {
            if (arrayToSearch[index].equals(elementToFind)) {
                return index;
            }
        }
        return -1;
    }

    private Number[][] splitNumberArray(Number[] NumberArray, int firstIndex, int lastIndex) {
        if (firstIndex >= 0 && lastIndex >= firstIndex) {
            int startSize = firstIndex;
            int endSize = NumberArray.length - lastIndex - 1;
            int toSortSize = NumberArray.length - startSize - endSize;
            if (startSize >= NumberArray.length - 1) {
                startSize = NumberArray.length;
                endSize = 0;
                toSortSize = 0;
            } else if (lastIndex >= NumberArray.length) {
                endSize = 0;
                toSortSize = NumberArray.length - startSize;
            }
            Number[] start = new Number[startSize];
            Number[] end = new Number[endSize];
            Number[] toSort = new Number[toSortSize];
            for (int index = 0; index < start.length; index++) {
                start[index] = NumberArray[index];
            }
            for (int index = 0; index < end.length; index++) {
                end[index] = NumberArray[index + lastIndex + 1];
            }
            for (int index = 0; index < toSort.length; index++) {
                toSort[index] = NumberArray[index + firstIndex];
            }
            return new Number[][]{start, toSort, end};
        }
        return new Number[][]{null, null, null};
    }

    private Number[] gatherArray(Number[][] arrayToGather) {
        int returnArrayLength = 0;
        for (int index = 0; index < arrayToGather.length; index++) {
            returnArrayLength += arrayToGather[index].length;
        }
        Number[] returnArray = new Number[returnArrayLength];
        for (int indexA = 0, startIndex = 0; indexA < arrayToGather.length; indexA++) {
            for (int indexB = 0; indexB < arrayToGather[indexA].length; indexB++) {
                returnArray[startIndex + indexB] = arrayToGather[indexA][indexB];
            }
            startIndex += arrayToGather[indexA].length;
        }
        return returnArray;
    }

    private Number[] sortArrayAsc(Number[] numberArray) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            Number[] buffer = numberArray.clone();
            for (int index = 1; index < numberArray.length; index++) {
                buffer[index] = numberArray[index];
                if (buffer[index].doubleValue() < buffer[index - 1].doubleValue()) {
                    buffer[index] = buffer[index - 1];
                    buffer[index - 1] = numberArray[index];
                    sorted = false;
                }
            }
            numberArray = buffer;
        }
        return numberArray;
    }

    private Number[] reverseArray(Number[] numberArray) {
        Number[] returnArray = new Number[numberArray.length];
        for (int index = 0; index < numberArray.length; index++) {
            returnArray[index] = numberArray[numberArray.length - index - 1];
        }
        return returnArray;
    }

    private void removeElement(int position) {
        if (elementsArray != null) {
            Number[] returnArray = new Number[arraySize() - 1];
            for (int index = 0; index < position; index++) {
                returnArray[index] = elementsArray[index];
            }
            for (int index = position; index < arraySize() - 1; index++) {
                returnArray[index] = elementsArray[index + 1];
            }
            elementsArray = returnArray;
        }
    }

    private void trim(MathSet mathSet) {
        Number[] returnArray = new Number[mathSet.arraySize()];
        System.arraycopy(mathSet.elementsArray, 0, returnArray, 0, returnArray.length);
        mathSet.elementsArray = returnArray;
    }

    public int arraySize() {
        int size = 0;
        if (elementsArray != null) {
            for (Number number : this.elementsArray) {
                if (number == null) {
                    break;
                }
                size++;
            }
        }
        return size;
    }

    public void add(Number element) {
        int size = arraySize();
        if ((elementsArray == null) || (size == elementsArray.length)) {
            Number[] newMathSet = new Number[arraySize() + DEFAULT_CAPACITY];
            System.arraycopy(elementsArray, 0, newMathSet, 0, elementsArray.length);
            elementsArray = newMathSet;
        }
        elementsArray[size] = element;
    }

    public void add(Number... elements) {
        if ((elements != null) && (elements.length != 0)) {
            for (int index = 0; index < elements.length; index++) {
                if (elements[index] != null) {
                    add(elements[index]);
                }
            }
        }
    }

    public void join(MathSet mathSet) {
        trim(this);
        MathSet joinMathSet = new MathSet(mathSet);
        add(joinMathSet.elementsArray);
    }

    public void join(MathSet... mathSets) {
        if (elementsArray != null) {
            MathSet returnMathSet = new MathSet();
            for (int index = 0; index < mathSets.length; index++) {
                returnMathSet.join(mathSets[index]);
            }
            join(returnMathSet);
        }
    }

    public void intersection(MathSet mathSet) {
        if (elementsArray != null) {
            MathSet returnSet = new MathSet(0);
            MathSet intersectionMathSet = new MathSet(mathSet);
            for (int indexA = 0; indexA < arraySize(); indexA++) {
                for (int indexB = 0; indexB < intersectionMathSet.arraySize(); indexB++) {
                    if (Objects.equals(intersectionMathSet.elementsArray[indexB], elementsArray[indexA])) {
                        returnSet.add(intersectionMathSet.elementsArray[indexB]);
                        removeElement(indexA);
                        intersectionMathSet.removeElement(indexB);
                        indexA--;
                        break;
                    }
                }
            }
            elementsArray = returnSet.elementsArray;
        }
    }

    public void intersection(MathSet... mathSets) {
        if (mathSets != null) {
            for (int index = 0; index < mathSets.length; index++) {
                if (mathSets[index] != null) {
                    intersection(mathSets[index]);
                }
            }
        }
    }

    public void sortAsc() {
        if (elementsArray != null) {
            trim(this);
            elementsArray = sortArrayAsc(elementsArray);
        }
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        if (elementsArray != null) {
            Number[][] splitArray = splitNumberArray(elementsArray, firstIndex, lastIndex);
            splitArray[1] = sortArrayAsc(splitArray[1]);
            elementsArray = gatherArray(splitArray);
        }
    }

    public void sortAsc(Number number) {
        if (elementsArray != null) {
            sortAsc();
            int index = findIndex(elementsArray, number);
            if (index == -1) {
                elementsArray = null;
            } else {
                elementsArray = splitNumberArray(elementsArray, index, elementsArray.length - 1)[1];
            }
        }
    }

    public void sortDesc() {
        if (elementsArray != null) {
            sortAsc();
            elementsArray = reverseArray(elementsArray);
        }
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        if (elementsArray != null) {
            Number[][] splitArray = splitNumberArray(elementsArray, firstIndex, lastIndex);
            splitArray[1] = reverseArray(sortArrayAsc(splitArray[1]));
            elementsArray = gatherArray(splitArray);
        }
    }

    public void sortDesc(Number number) {
        if (elementsArray != null) {
            sortAsc();
            elementsArray = reverseArray(elementsArray);
            int index = findIndex(elementsArray, number);
            if (index == -1) {
                elementsArray = null;
            } else {
                elementsArray = splitNumberArray(elementsArray, index, elementsArray.length - 1)[1];
            }
        }
    }

    public Number get(int index) {
        if ((elementsArray != null) && (elementsArray.length > index)) {
            return elementsArray[index];
        }
        return null;
    }

    public Number getMax() {
        if (elementsArray != null) {
            return sortArrayAsc(elementsArray)[elementsArray.length - 1];
        }
        return null;
    }

    public Number getMin() {
        if (elementsArray != null) {
            return sortArrayAsc(elementsArray)[0];
        }
        return null;
    }

    public Number getAverage() {
        if (elementsArray != null) {
            BigDecimal returnDecimal = new BigDecimal(0);
            int amountOfElements = 0;
            for (int index = 0; index < elementsArray.length; index++) {
                if (elementsArray[index] != null) {
                    amountOfElements++;
                    returnDecimal = returnDecimal.add(new BigDecimal(elementsArray[index].toString()));
                }
            }
            returnDecimal = returnDecimal.divide(new BigDecimal(amountOfElements), MathContext.DECIMAL32);
            return returnDecimal;
        }
        return null;
    }

    public Number getMedian() {
        int size = arraySize();
        MathSet workMathSet = new MathSet(this);
        workMathSet.sortAsc();
        if (size == 0) {
            return null;
        } else if (size % 2 == 0) {
            BigDecimal first = new BigDecimal(workMathSet.elementsArray[size / 2 - 1].toString());
            BigDecimal second = new BigDecimal(workMathSet.elementsArray[size / 2].toString());
            return (first.add(second)).divide(new BigDecimal(2), MathContext.DECIMAL32);

        }
        return workMathSet.elementsArray[size / 2];
    }

    public Number[] toArray() {
        if (elementsArray != null) {
            return elementsArray;
        }
        return null;
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        if (elementsArray != null) {
            return splitNumberArray(elementsArray, firstIndex, lastIndex)[1];
        }
        return null;
    }

    public MathSet trim(int firstIndex, int lastIndex) {
        if (elementsArray != null) {
            Number[] toAdd = splitNumberArray(elementsArray, firstIndex, lastIndex)[2];
            elementsArray = splitNumberArray(elementsArray, firstIndex, lastIndex)[0];
            add(toAdd);
            return this;
        }
        return null;
    }

    public void clear() {
        elementsArray = new Number[elementsArray.length];
    }

    public void clear(Number[] numbers) {
        if (elementsArray != null && numbers.length > 0) {
            for (int indexA = 0; indexA < numbers.length; indexA++) {
                for (int indexB = 0; indexB < elementsArray.length; indexB++) {
                    if (((elementsArray[indexB] != null) &&
                            (numbers[indexA] != null)) &&
                            (elementsArray[indexB].toString()
                                    .equals(numbers[indexA].toString()))) {
                        elementsArray[indexB] = null;
                    }
                }
            }
        }
    }
}

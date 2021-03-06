package Task21;
import java.util.ArrayList;

/**
 * Code matrix from array to list
 *
 * @author Damir Davletshin and Rinat Akhmethanov
 * @version 1.0
 */
public class MatrixCode {

    /**
     * Fixed size of matrix
     */
    private final int SIZE;
    /**
     * Main element in matrix (left upper element)
     */
    private Element head;

    /**
     * Constructs a new object
     *
     * @param matrix matrix in array
     */
    public MatrixCode(int[][] matrix) {
        SIZE = matrix.length;
        code(matrix);
        decode();
    }

    /**
     * Decode matrix from list to array
     *
     * @return decoded matrix in array
     */
    public int[][] decode() {
        int[][] array = new int[SIZE][SIZE];
        Element element, elementI = head;
        for (int i = 0; i < SIZE; i++) {
            element = elementI;
            for (int j = 0; j < SIZE; j++) {
                array[i][j] = element.value;
                element = element.nextJ;
            }
            elementI = elementI.nextI;
        }
        return array;
    }

    /**
     * Set new value in matrix's element in i-j position
     *
     * @param i     element's line number
     * @param j     element's column number
     * @param value new element's value
     */
    public void insert(int i, int j, int value) {
        Element element = head;
        for (int k = 0; k <= i; k++) {
            element = element.nextI;
        }
        for (int l = 0; l <= j; l++) {
            element = element.nextJ;
        }
        element.value = value;
    }

    /**
     * Clear value in matrix's element in i-j position (set 0 value)
     *
     * @param i element's line number
     * @param j element's column number
     */
    public void delete(int i, int j) {
        insert(i, j, 0);
    }

    /**
     * Return ArrayList with minimum value in each column
     *
     * @return ArrayList with minimum value in each column
     */
    public ArrayList<Integer> minList() {
        ArrayList<Integer> list = new ArrayList<>(SIZE);
        Element element, elementJ = head;
        for (int i = 0; i < SIZE; i++) {
            element = elementJ;
            int min = element.value;
            element = element.nextI;
            for (int j = 1; j < SIZE; j++) {
                min = Integer.min(min, element.value);
                element = element.nextI;
            }
            list.add(min);
            elementJ = elementJ.nextJ;
        }
        return list;
    }

    /**
     * Return sum of elements in main diagonal
     *
     * @return sum of elements in main diagonal
     */
    public int sumDiag() {
        int sum = 0;
        Element element = head;
        sum += element.value;
        for (int i = 0; i < SIZE - 1; i++) {
            element = element.nextI.nextJ;
            sum += element.value;
        }
        return sum;
    }

    /**
     * Transpose matrix
     */
    public void transp() {
        Element elemMain = head, elemOnI, elemOnJ;
        for (int i = 0; i < SIZE - 1; i++) {
            elemOnI = elemOnJ = elemMain;
            for (int j = 0; j < SIZE - i; j++) {
                int value = elemOnI.value;
                elemOnI.value = elemOnJ.value;
                elemOnJ.value = value;

                elemOnI = elemOnI.nextI;
                elemOnJ = elemOnJ.nextJ;
            }
            elemMain = elemMain.nextI.nextJ;
        }
    }

    /**
     * Sum elements in j1 and j2 columns in matrix and result set to j1 column
     *
     * @param j1 number of column to set result
     * @param j2 number of second column
     */
    public void sumCols(int j1, int j2) {
        Element elem1 = head, elem2 = head;
        if (j1 > j2) {
            for (int i = 0; i < j1; i++) {
                if (i < j2) elem2 = elem2.nextJ;
                elem1 = elem1.nextJ;
            }
        } else {
            for (int i = 0; i < j2; i++) {
                if (i < j1) elem1 = elem1.nextJ;
                elem2 = elem2.nextJ;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            elem1.value += elem2.value;
            elem1 = elem1.nextI;
            elem2 = elem2.nextI;
        }
    }

    /**
     * Code matrix from array to list
     *
     * @param matrix matrix in array
     */
    private void code(int[][] matrix) {
        head = new Element(matrix[0][0], 0, 0);
        Element element = head, elementI = head;
        for (int j = 1; j < SIZE; j++) {
            element.nextJ = new Element(matrix[0][j], 0, j);
            element = element.nextJ;
        }
        for (int i = 1; i < SIZE; i++) {
            elementI = elementI.nextI = new Element(matrix[i][0], i, 0);
            element = elementI;
            for (int j = 1; j < SIZE; j++) {
                element.nextJ = new Element(matrix[i][j], i, j);
                element = element.nextJ;
            }
        }
        elementI = head;
        Element elementJ;
        for (int i = 0; i < SIZE - 1; i++) {
            element = elementI;
            elementJ = elementI.nextI;
            for (int j = 0; j < SIZE; j++) {
                element.nextI = elementJ;
                element = element.nextJ;
                elementJ = elementJ.nextJ;
            }
            elementI = elementI.nextI;
        }

    }

    /**
     * This method was written for Task21.2
     * @param indexI
     * @param indexJ
     * @return
     */
    public int get(int indexI, int indexJ){
        Element element = head;
        for (int k = 0; k <= indexI; k++) {
            element = element.nextI;
        }
        for (int l = 0; l <= indexJ; l++) {
            element = element.nextJ;
        }
        return element.value;
    }

    /**
     * Element of list of matrix
     */
    private class Element {
        /**
         * Element value in matrix; Element line number; Element column number
         */
        private int value, i, j;
        /**
         * Next element in matrix in line; Next element in matrix in column
         */
        private Element nextI, nextJ;

        /**
         * Create element in matrix
         *
         * @param value value of element
         * @param i     element's line index
         * @param j     element's column index
         */
        public Element(int value, int i, int j) {
            this.value = value;
            this.i = i;
            this.j = j;
        }
    }
}
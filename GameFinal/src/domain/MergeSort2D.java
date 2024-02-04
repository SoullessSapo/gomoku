package domain;
import java.util.ArrayList;

public class MergeSort2D {
	/*
	 * Metodo para ordenar segun numero
	 * @param sortByType tipo de ordenamiento
	 * @param ArrayList<int[]> lista de las posiciones del jugador
	 * @return ArrayList<int[]> lista ordenada de las posiciones del jugador
	 */
    public static ArrayList<int[]> mergeSort(ArrayList<int[]> arrayList, int sortByType) {
        if (arrayList == null || sortByType < 0 || sortByType > 3) {
            System.out.println("Lista nula o índice de columna no válido. Debe ser 0 o 1.");
            return null;
        }

        ArrayList<int[]> tempMatrix = new ArrayList<>(arrayList);
        mergeSort(tempMatrix, sortByType, 0, tempMatrix.size() - 1);
        return new ArrayList<>(tempMatrix);
    }
    /*
	 * Metodo para ordenar por filas o columnas segun numero
	 * @param sortByType tipo de ordenamiento
	 * @param limite por izquierda
	 * @param limite por derecha
	 */
    private static void mergeSort(ArrayList<int[]> tempMatrix, int sortByType, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(tempMatrix, sortByType, left, mid);
            mergeSort(tempMatrix, sortByType, mid + 1, right);
            merge(tempMatrix, sortByType, left, mid, right);
        }
    }
    /*
	 * Metodo para ordenar por diagonal principal o inversa segun numero
	 * @param sortByType tipo de ordenamiento
	 * @param limite por izquierda
	 * @param limite por derecha
	 */
    private static void merge(ArrayList<int[]> tempMatrix, int sortByType, int left, int mid, int right) {
        ArrayList<int[]> leftHalf = new ArrayList<>(tempMatrix.subList(left, mid + 1));
        ArrayList<int[]> rightHalf = new ArrayList<>(tempMatrix.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftHalf.size() && j < rightHalf.size()) {
            int compareResult;
            if (sortByType == 0) {
                compareResult = Integer.compare(leftHalf.get(i)[0], rightHalf.get(j)[0]);
            } else if (sortByType == 1) {
                compareResult = Integer.compare(leftHalf.get(i)[0], rightHalf.get(j)[0]);
            } else if (sortByType == 2) {
                // Comparar por diagonal (suma de índices)
                compareResult = Integer.compare(leftHalf.get(i)[0] + leftHalf.get(i)[1], rightHalf.get(j)[0] + rightHalf.get(j)[1]);
            } else {
                // Comparar por diagonal inversa (resta de índices)
                compareResult = Integer.compare(leftHalf.get(i)[0] - leftHalf.get(i)[1], rightHalf.get(j)[0] - rightHalf.get(j)[1]);
            }

            if (compareResult <= 0) {
                tempMatrix.set(k++, leftHalf.get(i++));
            } else {
                tempMatrix.set(k++, rightHalf.get(j++));
            }
        }

        while (i < leftHalf.size()) {
            tempMatrix.set(k++, leftHalf.get(i++));
        }

        while (j < rightHalf.size()) {
            tempMatrix.set(k++, rightHalf.get(j++));
        }
    }

    public static void printMatrix(ArrayList<int[]> matrix) {
        if (matrix != null) {
            for (int[] row : matrix) {
                for (int value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        }
    }


}

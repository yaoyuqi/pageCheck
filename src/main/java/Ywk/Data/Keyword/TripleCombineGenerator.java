package Ywk.Data.Keyword;

public class TripleCombineGenerator implements IGenerator {
    private final String[] arr1;
    private final String[] arr2;
    private final String[] arr3;
    private int idx1 = 0;
    private int idx2 = 0;
    private int idx3 = -1;

    public TripleCombineGenerator(String[] arr1, String[] arr2, String[] arr3) {
        this.arr1 = arr1;
        this.arr2 = arr2;
        this.arr3 = arr3;
    }

    @Override
    public int count() {
        return arr1.length * arr2.length * arr3.length;
    }

    @Override
    public boolean hasNext() {
        return (idx1 < arr1.length - 1)
                || (idx1 == arr1.length - 1 && idx2 < arr2.length - 1)
                || (idx1 == arr1.length - 1 && idx2 == arr2.length - 1 && idx3 < arr3.length - 1);
    }

    @Override
    public String next() {
        idx3++;

        if (idx3 >= arr3.length) {
            idx3 = 0;
            idx2++;
        }

        if (idx2 >= arr2.length) {
            idx2 = 0;
            idx1++;
        }

        return arr1[idx1] + arr2[idx2] + arr3[idx3];
    }

    @Override
    public void clear() {
        idx1 = 0;
        idx2 = 0;
        idx3 = -1;
    }
}

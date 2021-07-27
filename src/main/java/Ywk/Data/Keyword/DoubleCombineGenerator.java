package Ywk.Data.Keyword;

public class DoubleCombineGenerator implements IGenerator {
    private final String[] arr1;
    private final String[] arr2;

    private int idx1 = 0;
    private int idx2 = -1;

    public DoubleCombineGenerator(String[] arr1, String[] arr2) {
        this.arr1 = arr1;
        this.arr2 = arr2;
    }

    @Override
    public int count() {
//        if (arr1.length == 1 && arr1[0].isEmpty()
//        && arr2.length == 1 && arr2[0].isEmpty()) {
//            return 0;
//        }
        return arr1.length * arr2.length;
    }

    @Override
    public boolean hasNext() {
        return (idx1 < arr1.length - 1)
                || (idx1 == arr1.length - 1 && idx2 < arr2.length - 1);
    }

    @Override
    public String next() {
        idx2++;
        if (idx2 >= arr2.length) {
            idx2 = 0;
            idx1++;
        }
        return arr1[idx1] + arr2[idx2];
    }

    @Override
    public void clear() {
        idx1 = 0;
        idx2 = -1;
    }
}

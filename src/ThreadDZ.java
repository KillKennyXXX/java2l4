import java.util.Date;

public class ThreadDZ {
    static final int size = 10000000;
    static final int h = size / 2;
    private static final Object o = new Date();
    private volatile static int myInt = 0;

    public static void main(String[] args) {
        ThreadDZ.ArrayMethod1();
        System.out.println();
        ThreadDZ.ArrayMethod2();
    }

    public static void ArrayMethod1() {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        //System.out.println(a);
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        //a = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - a);
        /*for (int i = 0; i < size; i++) {
            System.out.print(arr[i]+", ");
        }*/

    }

    public static void ArrayMethod2() {
        float[] arr = new float[size];
        float[] a1 = new float[(int) (size / 2)];
        float[] a2 = new float[(int) (size / 2)];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }, "A"
        );
        Thread t2 = new Thread(() -> {
            int i;
            for (int j = 0; j < a2.length; j++) {
                i = j + (int) (size / 2);
                a2[j] = (float) (a2[j] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }, "B"
        );
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        //a = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - a);

       /* for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + ", ");
        }
        */

    }
}

package test;

public class ExceptionTest {
    public static void main(String[] args) {
        System.out.println(doTest());
    }

    private static int doTest() {
        try {
            throw new RuntimeException();
        } finally {
            return 10;
        }
    }
}

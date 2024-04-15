import java.util.Random;

public class GenerateArray {

    public static int[] generateRandomIntArray(int length) {

        if (length <= 0)
            return null;

        int[] array = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(Integer.MAX_VALUE); // Generates random integers between 0 and
        }
        return array;
    }
}

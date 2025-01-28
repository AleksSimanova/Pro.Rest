package supportive;
import java.util.Random;

public class TextRandom {
    public static String generateString(int length)
    {
        Random rng = new Random();
        String characters="qwertyuiop;lkjhgfdsazxcvbnm,.";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}

import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        try {
            StringBuffer inputText = new StringBuffer("This is an example text to sort words by the number of vowels");

            StringBuffer sortedText = sortWordsByVowelCount(inputText);

            System.out.println("Відсортований текст за кількістю голосних:");
            System.out.println(sortedText);
        } catch (Exception e) {
            System.err.println("Виникла помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static StringBuffer sortWordsByVowelCount(StringBuffer text) {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("Текст не може бути порожнім");
        }

        String[] words = text.toString().split("\\s+");

        Arrays.sort(words, Comparator.comparingInt(Main::countVowels));

        StringBuffer sortedText = new StringBuffer();
        for (String word : words) {
            sortedText.append(word).append(" ");
        }

        if (sortedText.length() > 0) {
            sortedText.setLength(sortedText.length() - 1);
        }

        return sortedText;
    }

    public static int countVowels(String word) {
        if (word == null || word.isEmpty()) {
            return 0;
        }

        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : word.toCharArray()) {
            if (vowels.indexOf(c) >= 0) {
                count++;
            }
        }
        return count;
    }
}

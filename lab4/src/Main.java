import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Клас, який представляє літеру.
 */
class Letter {
    private char value;

    public Letter(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}

/**
 * Клас, який представляє розділовий знак.
 */
class Punctuation {
    private char symbol;

    public Punctuation(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}

/**
 * Клас, який представляє слово, що складається з масиву літер.
 */
class Word {
    private List<Letter> letters;

    public Word(String word) {
        letters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public int countVowels() {
        String vowels = "AEIOUaeiou";
        int count = 0;
        for (Letter letter : letters) {
            if (vowels.indexOf(letter.getValue()) != -1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder word = new StringBuilder();
        for (Letter letter : letters) {
            word.append(letter);
        }
        return word.toString();
    }
}

/**
 * Клас, який представляє речення, що складається з масиву слів та розділових знаків.
 */
class Sentence {
    private List<Word> words;
    private List<Punctuation> punctuations;

    public Sentence(String sentence) {
        words = new ArrayList<>();
        punctuations = new ArrayList<>();

        String[] splitParts = sentence.split("(?=[,.!?])|(?<=[,.!?])|\s+");
        for (String part : splitParts) {
            if (part.matches("[,.!?]") && !part.isEmpty()) {
                punctuations.add(new Punctuation(part.charAt(0)));
            } else if (!part.isBlank()) {
                words.add(new Word(part));
            }
        }
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        StringBuilder sentence = new StringBuilder();
        int wordIndex = 0;
        int punctIndex = 0;

        while (wordIndex < words.size() || punctIndex < punctuations.size()) {
            if (wordIndex < words.size()) {
                sentence.append(words.get(wordIndex++));
            }
            if (punctIndex < punctuations.size()) {
                sentence.append(punctuations.get(punctIndex++));
            }
            if (wordIndex < words.size() && punctIndex < punctuations.size()) {
                sentence.append(" ");
            }
        }
        return sentence.toString();
    }
}

/**
 * Клас, який представляє текст, що складається з масиву речень.
 */
class Text {
    private List<Sentence> sentences;

    public Text(String text) {
        sentences = new ArrayList<>();
        text = text.replaceAll("\\s+", " ");
        String[] splitSentences = text.split("(?<=[.!?])");
        for (String part : splitSentences) {
            if (!part.isBlank()) {
                sentences.add(new Sentence(part.trim()));
            }
        }
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public String sortWordsByVowelCount() {
        List<Word> allWords = new ArrayList<>();
        for (Sentence sentence : sentences) {
            allWords.addAll(sentence.getWords());
        }
        allWords.sort((w1, w2) -> Integer.compare(w1.countVowels(), w2.countVowels()));

        StringBuilder sortedWords = new StringBuilder();
        for (Word word : allWords) {
            sortedWords.append(word).append(" ");
        }
        return sortedWords.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (Sentence sentence : sentences) {
            text.append(sentence).append(" ");
        }
        return text.toString().trim();
    }
}

public class Main {
    public static void main(String[] args) {
        // Вихідний текст
        String inputText = "This is a test text. It includes, multiple sentences, words and punctuation marks.";

        // Створення тексту
        Text text = new Text(inputText);

        // Виведення початкового тексту
        System.out.println("Початковий текст:");
        System.out.println(text);

        // Сортування слів за кількістю голосних
        String sortedWords = text.sortWordsByVowelCount();

        // Виведення результату сортування
        System.out.println("\nСлова, відсортовані за кількістю голосних:");
        System.out.println(sortedWords);
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single character in a word.
 * <p>
 * This class encapsulates a character value and provides a method to retrieve it.
 * It is used as a building block for the Word class, allowing more structured handling
 * of individual letters.
 */
class Letter {
    private char value;
    /**
     * Constructs a Letter object with the specified character value.
     *
     * @param value the character value of the letter
     */
    public Letter(char value) {
        this.value = value;
    }
    /**
     * Returns the character value of the letter.
     *
     * @return the character value
     */
    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}


/**
 * Represents a punctuation mark in a sentence.
 * <p>
 * This class encapsulates a punctuation symbol and provides methods to retrieve its value.
 */
class Punctuation {
    private char symbol;
    /**
     * Constructs a Punctuation object with the specified symbol.
     *
     * @param symbol the punctuation symbol
     */
    public Punctuation(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}

/**
 * Represents a word composed of multiple letters.
 * <p>
 * This class manages a list of Letter objects to form a word and provides methods
 * to analyze the word, such as counting vowels.
 */
class Word {
    private List<Letter> letters;
    /**
     * Constructs a Word object from the given string.
     *
     * @param word the string representation of the word
     */
    public Word(String word) {
        letters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    /**
     * Counts the number of vowels in the word.
     *
     * @return the count of vowels in the word
     */
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
 * Represents a sentence composed of words and punctuation marks.
 * <p>
 * This class organizes words and punctuation into a coherent structure and
 * provides methods to access and format the sentence.
 */
class Sentence {
    private List<Object> elements;
    /**
     * Constructs a Sentence object by parsing a given string.
     *
     * @param sentence the string representation of the sentence
     */
    public Sentence(String sentence) {
        elements = new ArrayList<>();
        String[] splitParts = sentence.split("(?=[,.!?])|(?<=[,.!?])|\s+");
        for (String part : splitParts) {
            if (part.matches("[,.!?]")) {
                elements.add(new Punctuation(part.charAt(0)));
            } else if (!part.isBlank()) {
                elements.add(new Word(part));
            }
        }
    }
    /**
     * Returns the list of words in the sentence.
     *
     * @return the list of Word objects
     */
    public List<Object> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sentence = new StringBuilder();
        boolean lastWasWord = false;

        for (Object element : elements) {
            if (element instanceof Word) {
                if (lastWasWord) {
                    sentence.append(" ");
                }
                sentence.append(element);
                lastWasWord = true;
            } else if (element instanceof Punctuation) {
                sentence.append(element);
                lastWasWord = false;
            }
        }
        return sentence.toString();
    }

}

/**
 * Represents a text composed of multiple sentences.
 * <p>
 * This class provides functionality for organizing and manipulating sentences,
 * including methods for text transformation and word analysis.
 */
class Text {
    private List<Sentence> sentences;
    /**
     * Constructs a Text object by parsing a given string into sentences.
     *
     * @param text the string representation of the text
     */
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
    /**
     * Sorts all words in the text by their vowel count and returns a concatenated string of sorted words.
     *
     * @return a string of words sorted by the number of vowels
     */
    public String sortWordsByVowelCount() {
        List<Word> allWords = new ArrayList<>();
        for (Sentence sentence : sentences) {
            for (Object element : sentence.getElements()) {
                if (element instanceof Word) {
                    allWords.add((Word) element);
                }
            }
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
        for (int i = 0; i < sentences.size(); i++) {
            text.append(sentences.get(i));
            if (i < sentences.size() - 1) {
                text.append(" ");
            }
        }
        return text.toString().trim();
    }

}

/**
 * Entry point of the application to demonstrate the Text processing.
 */
public class Main {
    public static void main(String[] args) {
        String inputText = "This is a test text. It includes multiple sentences, words and punctuation marks.";

        Text text = new Text(inputText);

        System.out.println("Початковий текст:");
        System.out.println(text);

        String sortedWords = text.sortWordsByVowelCount();

        System.out.println("\nСлова, відсортовані за кількістю голосних:");
        System.out.println(sortedWords);
    }
}

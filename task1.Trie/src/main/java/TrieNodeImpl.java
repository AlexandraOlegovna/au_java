import java.util.HashMap;

public class TrieNodeImpl {
    private boolean isTerminal;
    private int numberOfWordsWithThisPrefix;
    private HashMap<Character, TrieNodeImpl> dict = new HashMap<>();

    public TrieNodeImpl() {
        this.isTerminal = false;
        this.numberOfWordsWithThisPrefix = 0;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }

    public int getNumberOfWordsWithThisPrefix() {
        return numberOfWordsWithThisPrefix;
    }

    public void incNumberOfWordsWithThisPrefix() {
        ++numberOfWordsWithThisPrefix;
    }

    public void decNumberOfWordsWithThisPrefix() {
        --numberOfWordsWithThisPrefix;
    }

    public TrieNodeImpl getNext(char ch) {
        return dict.get(ch);
    }

    public void createNext(char ch) {
        dict.put(ch, new TrieNodeImpl());
    }

    public void removeNext(char ch) {
        dict.remove(ch);
    }

    public boolean containsNext(char ch) {
        return dict.containsKey(ch);
    }

}
public class TrieImpl implements Trie {

    private TrieNodeImpl root = new TrieNodeImpl();

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     * null and empty string return false because add ended with error
     */
    @Override
    public boolean add(String element) {
        if (isNullOrEmpty(element) || contains(element)) {
            return false;
        }
        TrieNodeImpl node = root;
        for (char ch : element.toCharArray()) {
            if (!node.containsNext(ch)) {
                node.createNext(ch);
            }
            node.incNumberOfWordsWithThisPrefix();
            node = node.getNext(ch);
        }
        node.setTerminal(true);
        node.incNumberOfWordsWithThisPrefix();
        return true;
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     */
    @Override
    public boolean contains(String element) {
        if (isNullOrEmpty(element)) {
            return false;
        }
        TrieNodeImpl node = find(element);
        return node != null && node.isTerminal();
    }

    /**
     * Expected complexity: O(|element|)
     *
     * @param element
     * @return <tt>true</tt> if this set contained the specified element
     */
    @Override
    public boolean remove(String element) {
        if (isNullOrEmpty(element) || !contains(element)) {
            return false;
        }
        TrieNodeImpl node = root;
        for (char ch : element.toCharArray()) {
            node.decNumberOfWordsWithThisPrefix();
            if (node.getNext(ch).getNumberOfWordsWithThisPrefix() == 1) {
                node.removeNext(ch);
                return true;
            }
            node = node.getNext(ch);
        }
        node.decNumberOfWordsWithThisPrefix();
        node.setTerminal(false);
        return true;
    }

    /**
     * Expected complexity: O(1)
     */
    @Override
    public int size() {
        return root.getNumberOfWordsWithThisPrefix();
    }

    /**
     * Expected complexity: O(|prefix|)
     *
     * @param prefix
     */
    @Override
    public int howManyStartsWithPrefix(String prefix) {
        if (isNullOrEmpty(prefix)) {
            return 0;
        }
        TrieNodeImpl node = find(prefix);
        if (node == null) {
            return 0;
        }
        return node.getNumberOfWordsWithThisPrefix();
    }

    private boolean isNullOrEmpty(String element) {
        return element == null || element.equals("");
    }

    private TrieNodeImpl find(String element) {
        TrieNodeImpl node = root;
        for (char ch : element.toCharArray()) {
            if (!node.containsNext(ch)) {
                return null;
            }
            node = node.getNext(ch);
        }
        return node;
    }
}

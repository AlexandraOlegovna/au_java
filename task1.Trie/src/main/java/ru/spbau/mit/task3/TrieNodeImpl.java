package ru.spbau.mit.task3;

import java.io.*;
import java.util.*;

public class TrieNodeImpl implements StreamSerializable {
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

    @Override
    public void serialize(OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeInt(numberOfWordsWithThisPrefix);
        dataOutputStream.writeBoolean(isTerminal);

        dataOutputStream.writeInt(dict.size());
        for (Map.Entry<Character, TrieNodeImpl> entry : dict.entrySet()) {
            dataOutputStream.writeChar(entry.getKey());
            entry.getValue().serialize(dataOutputStream);
        }
    }

    /**
     * Replace current state with data from input stream
     *
     * @param in
     */
    @Override
    public void deserialize(InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int newNumberOfWords = dataInputStream.readInt();
        boolean newTerminal = dataInputStream.readBoolean();

        HashMap<Character, TrieNodeImpl> newDict = new HashMap<>();
        int newDictSize = dataInputStream.readInt();
        for (int i = 0; i < newDictSize; ++i) {
            char key = dataInputStream.readChar();
            TrieNodeImpl values = new TrieNodeImpl();
            values.deserialize(dataInputStream);
            newDict.put(key, values);
        }

        dict = newDict;
        isTerminal = newTerminal;
        numberOfWordsWithThisPrefix = newNumberOfWords;

    }
}
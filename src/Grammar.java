import javafx.util.Pair;

import java.util.*;

/**
 * Created by Diana on 05/12/2016.
 */
public class Grammar {
    private Set<String> setOfNonterminals;
    private Set<String> setOfTerminals;
    private Map<String, List<String>> productions;
    private String startSymbol;

    public Grammar() {
        this.setOfNonterminals = new HashSet<>();
        this.setOfTerminals = new HashSet<>();
        this.productions = new HashMap<>();
    }

    public Set<String> getSetOfNonterminals() {
        return setOfNonterminals;
    }

    public void setSetOfNonterminals(Set<String> setOfNonterminals) {
        this.setOfNonterminals = setOfNonterminals;
    }

    public Set<String> getSetOfTerminals() {
        return setOfTerminals;
    }

    public void setSetOfTerminals(Set<String> setOfTerminals) {
        this.setOfTerminals = setOfTerminals;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public void setProductions(Map<String, List<String>> productions) {
        this.productions = productions;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }
}

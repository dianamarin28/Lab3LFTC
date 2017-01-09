import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Diana on 06/12/2016.
 */
public class Controller {
    private static Grammar grammar = new Grammar();

    private List<String> readFromFile(String filename) {
        List<String> sourceCode = new ArrayList<>();

        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sourceCode.add(line);
            }

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sourceCode;
    }

    public void readGrammarFromFile() {
        List<String> grammarFromFile = readFromFile("Grammar.txt");

        Set<String> nonterminals = new HashSet<>();
        Set<String> terminals = new HashSet<>();
        Map<String, List<String>> productions = new HashMap<>();

        for (int i=0; i<grammarFromFile.size(); i++) {
            StringTokenizer tokens = new StringTokenizer(grammarFromFile.get(i), ",", false);

            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();

                if (i == 0) {
                    token = token.replaceAll("\\s+","");
                    nonterminals.add(token);
                }
                else if (i == 1) {
                    token = token.replaceAll("\\s+","");
                    terminals.add(token);
                }
                else if (i == 2) {
                    StringTokenizer tokensP1 = new StringTokenizer(token, "->", false);

                    String left = tokensP1.nextToken();
                    left = left.replaceAll("\\s+","");

                    String right = tokensP1.nextToken();
                    right = right.replaceAll("\\s+","");
                    List<String> rights = new ArrayList<>();
                    StringTokenizer tokensP2 = new StringTokenizer(right, "|", false);
                    while (tokensP2.hasMoreTokens()) {
                       rights.add(tokensP2.nextToken());
                    }

                    productions.put(left, rights);
                }
                else if (i == 3) {
                    grammar.setStartSymbol(token);
                }
            }
        }

        grammar.setSetOfNonterminals(nonterminals);
        grammar.setSetOfTerminals(terminals);
        grammar.setProductions(productions);
    }

    private List<String> getProductionsForNonterminal(String nonterminal) {
        return grammar.getProductions().get(nonterminal);
    }

    private boolean isNonterminal(String token) {
        for (String s : grammar.getSetOfNonterminals()) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSingleAndNonterminal(String token) {
        if (isNonterminal(token) && token.length() == 1) {
            return true;
        }
        return false;
    }

    private Set<String> buidSetForNonterminal(String nonterminal) {
        Set<String> nonterminalSet = new LinkedHashSet<>();
        nonterminalSet.add(nonterminal);

        boolean flag = false;
        while (!flag) {
            int changesCount = 0;
            for (String n : nonterminalSet) {
                for (String p : getProductionsForNonterminal(n)) {
                    if (isSingleAndNonterminal(p) && !nonterminalSet.contains(p)) {
                        changesCount++;
                        nonterminalSet.add(p);
                    }
                }
            }
            if (changesCount == 0) {
                flag = true;
            }
        }

        return nonterminalSet;
    }

    public Grammar buildGrammarWithoutSingleProductions() {
        Grammar g = new Grammar();

        g.setSetOfNonterminals(grammar.getSetOfNonterminals());
        g.setSetOfTerminals(grammar.getSetOfTerminals());
        g.setStartSymbol(grammar.getStartSymbol());

        Map<String, List<String>> newProductionsForGrammar = new HashMap<>();
        for (String nonterminal : grammar.getSetOfNonterminals()) {
            List<String> newProductions = new ArrayList<>();

            for (String i : buidSetForNonterminal(nonterminal)) {
                for (String j : getProductionsForNonterminal(i)) {
                    if (!isSingleAndNonterminal(j)) {
                        newProductions.add(j);
                    }
                }
            }

            newProductionsForGrammar.put(nonterminal, newProductions);
        }
        g.setProductions(newProductionsForGrammar);

        return g;
    }
}

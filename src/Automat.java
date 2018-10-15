import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Automat
{
    private ArrayList<ArrayList<Character>> words;
    private ArrayList<ArrayList<Integer>> stateTransition;
    private boolean wordExist = true;
    Scanner sc = new Scanner(System.in);
    String glTmp = "";
    char[] glTmpCh;
    boolean cont = false;

    public Automat()
    {
        words = new ArrayList<>();
        stateTransition = new ArrayList<>();
        do{
        enterWord();
        checkSequence();
            System.out.print("==============================\r" + "Continue? Y/N");
            String tmp = sc.next();
            if(tmp.toCharArray()[0]=='Y' || tmp.toCharArray()[0]=='y') cont=true;
            else cont = false;
        }while(cont);
    }

    private void enterWord()
    {
        glTmp = null; glTmpCh = null;
        System.out.print("Enter your word: ");
        glTmp = sc.next();
        glTmpCh = glTmp.toCharArray();
    }

    private void checkSequence()
    {
        if(!words.isEmpty())
        {
            int iterWo = 0;
            int iterCh = 0;
            //если введенная буква совпадает с имеющимся словом
            for(char c:glTmpCh)
            {
                if (c == words.get(iterWo).get(iterCh))
                {
                    iterCh++;
                    wordExist = true;
                }
                else
                {
                    System.out.println("FAILURE");
                    wordExist = false;
                    break;
                }
            }
        }
        else{
            System.out.println("Dictionary is empty.");
            wordExist = false;
        }

        afterChecking();
    }

    private void afterChecking()
    {
        if(wordExist) System.out.println("Word exist.");
        else if(!wordExist)
        {
            System.out.println("Word not exist.");
            addIfWanted();
        }
    }

    private void addIfWanted()
    {
        System.out.print("Do you want to add word? Y/N ");
        String tmp = sc.next();
        if(tmp.toCharArray()[0]=='Y' || tmp.toCharArray()[0]=='y') addWord();
    }

    private void addWord()
    {
        //указание на другую последовательность
        ArrayList<Character> tmpList = new ArrayList<>();
        for(char c:glTmpCh) tmpList.add(c);
        if(words.add(tmpList))System.out.println("Word added successfully.");
        showWords();
    }

    private void showWords()
    {
        if(!words.isEmpty())
        {
            for (ArrayList<Character> b : words)
                for (Character c : b)
                    System.out.println(c);
        }
        else
        {
            System.out.println("Dictionary is empty.");
        }
    }
}
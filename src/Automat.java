import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Automat
{
    private ArrayList<ArrayList<Character>> words;
    private ArrayList<ArrayList<Integer>> stateTransition;
    private boolean wordExist = true;
    private Scanner sc = new Scanner(System.in);
    private String glTmp = "";
    private char[] glTmpCh;
    private boolean cont = false;

    private int iterWo = 0;
    private int iterCh = 0;
    private int steps = 0;

    public Automat()
    {
        words = new ArrayList<>();
        stateTransition = new ArrayList<>();
        //
        do{
        enterWord();
        checkSequence();
            System.out.print("==============================\r" + "Continue? Y/N");
            String tmp = sc.next();
            if(tmp.toCharArray()[0]=='Y' || tmp.toCharArray()[0]=='y') cont=true;
            else cont = false;

           lineI();
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
        steps = 0;
        int state = 0;//номер проверяем. ячейки
        iterCh = 0;
        if(!words.isEmpty())//если словарь не пустой
        {
            for(char c: glTmpCh)//каждый символ в введенном слове
            {
                if(c==words.get(state).get(iterCh))
                {
                    wordExist = true;//проверяемые буквы одинаковы
                    iterCh++; //итерация для след. шага
                    steps++;
                }
                else if(c!=words.get(state).get(iterCh))
                {
                    if(stateTransition.get(state).get(iterCh)!=null)
                    {
                        state = stateTransition.get(state).get(iterCh);
                        iterCh = 0;
                    }
                    else if (stateTransition.get(state).get(iterCh) == null)
                    {
                        addTransition(state);//добавление посылания на продолжение нового слова
                        addWord();//добавление оставшихся букв нового слова
                        steps++;
                        break;
                    }
                }
            }
        }
        else
        {
            System.out.println("Dictionary is empty.");
            wordExist = false;
            addIfWanted();
        }
    }

    private void addTransition(int state)
    {
        stateTransition.get(state).set(iterCh, words.size());
    }

    private void addIfWanted()
    {
        System.out.println("Word not exist.");
        System.out.print("Do you want to add? Y/N ");
        String tmp = sc.next();
        if (tmp.toCharArray()[0] == 'Y' || tmp.toCharArray()[0] == 'y')
            addWord();
    }

    private void addWord()
    {
        //указание на другую последовательность
        ArrayList<Character> tmpList = new ArrayList<>();
        ArrayList<Integer> tmpStList = new ArrayList<>();

        for (int i = steps; i < glTmpCh.length; i++)
        {
            tmpList.add(glTmpCh[i]);
            tmpStList.add(null);
        }
        if(words.add(tmpList))System.out.println("Word added successfully.");
        else System.out.println("Something went wrong.Words");

        if(stateTransition.add(tmpStList)) System.out.println("States initiated successfully.");
        else System.out.println("Something went wrong.States");

        showWords();
    }

    private void showWords()
    {
        int tmpIter = 0;
        lineI();
        if (!words.isEmpty())
        {
            System.out.println("Your dictionary.");
            for (ArrayList<Character> b : words)
            {
                System.out.println("[" + tmpIter + "]");
                for (Character c : b)
                {
                    System.out.println(c);
                }
                thinLineI();
                tmpIter++;
            }
            lineI();
            tmpIter = 0;
            System.out.println("Your state dictionary.");
            for (ArrayList<Integer> i : stateTransition)
            {
                System.out.println("[" + tmpIter + "]");
                for (Integer a : i)
                {
                    if (a == null)
                    {
                        System.out.println("null");
                    }
                    else
                    {
                        System.out.println(a);
                    }
                }
                thinLineI();
                tmpIter++;
            }
            lineI();
        }
        else
        {
            System.out.println("Dictionary is empty.");
        }
    }

    private void lineI()
    {
        System.out.println("======================");
    }
    private void thinLineI()
    {
        System.out.println("----------------------");
    }
}
/*
* EnterWord()
        ArrayList<Integer> tmp = new ArrayList<>();

        for (int i = 0; i < glTmpCh.length; i++) {
            tmp.add(null);
        }
        stateTransition.add(tmp);*/
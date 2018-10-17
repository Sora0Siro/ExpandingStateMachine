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
        if(dividing())checkSequence();//только первое слов из всех возможно введнены0х
    }

    private boolean dividing()
    {
        int match = 0;//проверка условий на принадлежность каждого символа к букве?
        String[] tokens = glTmp.split("\\*");//разделение всех возможных слов при вводе
        if(!glTmp.isEmpty() && tokens.length!=0)
        {
            glTmp = tokens[0];
            glTmpCh = glTmp.toCharArray();

            for(int i =0;i<tokens[0].length();i++)
            {
                if(Character.isLetter(glTmpCh[i]))match++;
            }
        }
        if(!glTmp.isEmpty() && tokens.length!=0 && match==glTmpCh.length)
        {
            glTmp +="*";
            glTmpCh = glTmp.toCharArray();
            return true;
        }
        else
        {
            System.out.println("Your word is not match the requirements.");
            return false;
        }
    }

    private void checkSequence()
    {
        steps = 0;
        int state = 0;//номер проверяем. ячейки
        iterCh = 0;
        //
        if(!words.isEmpty())//если словарь не пустой
        {
            for (int i = steps; i < glTmpCh.length; i++)//1;4
            {
                if (glTmpCh[i] == words.get(state).get(iterCh))//true;true;true;false
                {
                    wordExist = true;//проверяемые буквы одинаковы
                    iterCh++; //итерация для след. шага
                    steps++;//кол-во совп. букв
                }
                else if (glTmpCh[i] != words.get(state).get(iterCh))
                {
                    if (stateTransition.get(state).get(iterCh) != null)
                    {
                        state = stateTransition.get(state).get(iterCh);
                        iterCh = 0;
                        i-=1;
                    }
                    else if (stateTransition.get(state).get(iterCh) == null)
                    {
                        if(addIfWanted())
                        {
                            steps++;
                            addTransition(state);//добавление посылания на продолжение нового
                        }//добавление оставшихся букв нового слова

                        break;
                    }
                }
            }
        }
        else
        {
            System.out.println("Dictionary is empty.");
            wordExist = false;
            if(addIfWanted()) wordExist = true;
        }
        exOrNo();
        showParallelWords();
    }

    private void addTransition(int state)
    {
        stateTransition.get(state).set(iterCh, words.size()-1);
    }

    private boolean addIfWanted()
    {
        System.out.println("Word not exist.");
        System.out.print("Do you want to add? Y/N ");
        String tmp = sc.next();
        if (tmp.toCharArray()[0] == 'Y' || tmp.toCharArray()[0] == 'y')
        {
            addWord();
            return true;
        }
        else return false;
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
    }

    private void showParallelWords()
    {
        lineI();
        System.out.println("Parallel output of dictionary");
        for(int i =0;i<words.size();i++)
        {
            System.out.println("[" + i + "]");
            System.out.println(words.get(i) + "|" + stateTransition.get(i));
        }
        lineI();
    }

    private void lineI()
    {
        System.out.println("======================");
    }

    private void thinLineI()
    {
        System.out.println("----------------------");
    }

    private void exOrNo()
    {
        if(wordExist)System.out.println("Word exist.");
        else if(!wordExist) System.out.println("Word not exist.");
    }
}

/* private void showWords()
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
    }*/
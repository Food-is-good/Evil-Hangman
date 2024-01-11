//Henry Zhang
//3rd Period
//CS3
//

import java.util.*;

public class HangmanManager
{
  private Set<String> words = new HashSet<String>();
  private int newMax;
  private Set<Character> guesses = new HashSet<Character>();
  private char[] patternChar;

  public HangmanManager( List<String> dictionary, int length, int max )
  {	
    newMax = max;
    if(length < 1 || max < 0)
    {
      throw new IllegalArgumentException();
    }

    for(String str : dictionary)
    {
      if(str.length() == length)
      {
        words.add(str);
      }
    }

    patternChar = new char[length];

    for(int i = 0; i < length; i++)
    {
      patternChar[i] = '-';
    }
  }

  public Set<String> words()
  {
    return words;
  }	

  public int guessesLeft()
  {	
    return newMax;
  }

  public Set<Character> guesses()
  {
    return guesses;
  }

  public String pattern()
  {
    if(words.isEmpty())
    {
      throw new IllegalStateException();
    }

    return new String(patternChar);
  }

  public int record( char guess )
  {	
    if(guessesLeft() < 1 || words.isEmpty())
    {
      throw new IllegalStateException();
    }

    if(guesses().contains(guess))
    {
      throw new IllegalArgumentException();
    }

    int count = 0;

    guesses.add(guess);
    Map<String, Set<String>> stored = new TreeMap<>();

    for(String str : words)
    {
    	char[] c = patternChar.clone();
    	for(int i = 0; i < str.length(); i++) 
       {
           if(str.charAt(i) == guess)
           {
             c[i] = guess;
           }
       }

      String newPattern = new String(c);
      
        if(!stored.containsKey(newPattern))
        {
          Set<String> newSet = new TreeSet<String>();
          newSet.add(str);
          stored.put(newPattern, newSet);
        }
        else
        {
          stored.get(newPattern).add(str);
        }
    }

    int largest = 0;
    String largestKey = "";

    for (Map.Entry<String, Set<String>> e : stored.entrySet())
    {
      if (e.getValue().size() > largest)
      {
            largest = e.getValue().size();
            largestKey = e.getKey();
      }
    }
    
    ArrayList<String> al = new ArrayList<String>(stored.get(largestKey));
    words = new HashSet<>(al);

    for(int i = 0; i < largestKey.length(); i++)
    {
      patternChar[i] = largestKey.charAt(i);
    }
    
    for(String e : words)
    {
      if(e.contains(Character.toString(guess)))
      {
        count++;
      }
    }

    if(count == 0)
    {
      newMax--;
    }

    return count;
    
  }
}

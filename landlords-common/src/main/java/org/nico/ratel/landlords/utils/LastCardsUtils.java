package org.nico.ratel.landlords.utils;

import org.nico.ratel.landlords.entity.Poker;

import java.util.*;

public class LastCardsUtils {

    public static String getLastCards(List<List<Poker>> pokers){
        StringBuffer lastCards = new StringBuffer();
        Map<String, Integer> lastCardMap = initLastCards();
        for(int i = 0; i < pokers.size(); i++){
            List<Poker> pokerList = pokers.get(i);
            for(int a = 0; a < pokerList.size(); a++){
                Poker poker = pokerList.get(a);
                lastCardMap.put(poker.getLevel().getName(),(lastCardMap.get(poker.getLevel().getName())+1));
            }
        }
        List<String> defSort = new ArrayList<>();
        initDefSort(defSort);
        for(int i = 0; i < defSort.size(); i++){
            String key = defSort.get(i);
            lastCards.append(key + "["+lastCardMap.get(key)+"] ");
        }

        return lastCards.toString();
    }

    private static void initDefSort(List<String> defSort){
        defSort.add("3");
        defSort.add("4");
        defSort.add("5");
        defSort.add("6");
        defSort.add("7");
        defSort.add("8");
        defSort.add("9");
        defSort.add("10");
        defSort.add("J");
        defSort.add("Q");
        defSort.add("K");
        defSort.add("A");
        defSort.add("2");
        defSort.add("S");
        defSort.add("X");

    }

    private static Map<String, Integer> initLastCards(){
        Map<String, Integer> lastCards = new HashMap<>();
        lastCards.put("A",0);
        lastCards.put("2",0);
        lastCards.put("3",0);
        lastCards.put("4",0);
        lastCards.put("5",0);
        lastCards.put("6",0);
        lastCards.put("7",0);
        lastCards.put("8",0);
        lastCards.put("9",0);
        lastCards.put("10",0);
        lastCards.put("J",0);
        lastCards.put("Q",0);
        lastCards.put("K",0);
        lastCards.put("S",0);
        lastCards.put("X",0);
        return lastCards;
    }
}

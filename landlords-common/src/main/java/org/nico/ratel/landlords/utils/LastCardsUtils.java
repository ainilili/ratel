package org.nico.ratel.landlords.utils;

import org.nico.ratel.landlords.entity.Poker;

import java.util.*;

public class LastCardsUtils {

    private static final List<String> defSort = new ArrayList(){{
        add("3");
        add("4");
        add("5");
        add("6");
        add("7");
        add("8");
        add("9");
        add("10");
        add("J");
        add("Q");
        add("K");
        add("A");
        add("2");
        add("S");
        add("X");
    }};

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
        for(int i = 0; i < defSort.size(); i++){
            String key = defSort.get(i);
            lastCards.append(key + "["+lastCardMap.get(key)+"] ");
        }

        return lastCards.toString();
    }


    private static Map<String, Integer> initLastCards(){
        Map<String, Integer> lastCardMap = new HashMap<>();
        lastCardMap.put("A",0);
        lastCardMap.put("2",0);
        lastCardMap.put("3",0);
        lastCardMap.put("4",0);
        lastCardMap.put("5",0);
        lastCardMap.put("6",0);
        lastCardMap.put("7",0);
        lastCardMap.put("8",0);
        lastCardMap.put("9",0);
        lastCardMap.put("10",0);
        lastCardMap.put("J",0);
        lastCardMap.put("Q",0);
        lastCardMap.put("K",0);
        lastCardMap.put("S",0);
        lastCardMap.put("X",0);
        return lastCardMap;
    }
}

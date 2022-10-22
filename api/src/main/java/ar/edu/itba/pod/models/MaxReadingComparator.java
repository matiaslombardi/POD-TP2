package ar.edu.itba.pod.models;

import java.util.Comparator;
import java.util.Map;

public class MaxReadingComparator<K extends Comparable<? super K>, V extends Comparable<? super V>> implements Comparator<Map.Entry<K, V>> {

    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
        int toReturn = o1.getValue().compareTo(o2.getValue());
        if (toReturn == 0)
            toReturn = o1.getKey().compareTo(o2.getKey());
        return toReturn;
    }
}

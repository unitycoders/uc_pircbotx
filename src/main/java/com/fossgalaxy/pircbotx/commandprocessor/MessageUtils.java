package com.fossgalaxy.pircbotx.commandprocessor;

import java.util.Collection;
import java.util.Iterator;

public class MessageUtils {

    /**
     * Convert a java collection to a comma seperated list.
     *
     * @param collection the collection to print
     * @param <T>        the type of the collection to comma seperate
     * @return the string reperesentation of the collection
     */
    public static <T> String buildList(Collection<T> collection) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> itr = collection.iterator();

        while (itr.hasNext()) {
            T item = itr.next();
            sb.append(item.toString());
            if (itr.hasNext()) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

}

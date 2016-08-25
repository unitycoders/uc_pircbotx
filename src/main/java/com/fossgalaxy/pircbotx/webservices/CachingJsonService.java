/**
 * Copyright © 2015 Unity Coders
 * <p>
 * This file is part of uc_pircbotx.
 * <p>
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fossgalaxy.pircbotx.webservices;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A JSON service which enforces rate limiting.
 *
 * This version of the client will allow for caching of results.
 */
public class CachingJsonService<T> extends JsonService<T> {
    private static final Integer CACHE_MAX_AGE = 3600000;
    private Map<URL, CacheEntry> cache;

    public CachingJsonService() {
        this.cache = new HashMap<>();
    }

    @Override
    public T makeRequest(URL url, Class<? extends T> responseClass) throws IOException {
        CacheEntry entry = cache.get(url);
        System.out.println(entry + " " + cache);
        if (entry != null && entry.isValid()) {
            System.out.println("[debug] read from cache: " + entry.data);
            assert responseClass.isAssignableFrom(entry.data.getClass());
            return entry.data;
        }

        T data = super.makeRequest(url, responseClass);
        notifyCaching(data);
        updateCache(url, data);
        return data;
    }

    protected void notifyCaching(T object) {

    }

    protected void updateCache(URL url, T object) {
        CacheEntry entry = new CacheEntry();
        entry.data = object;
        entry.storeTime = System.currentTimeMillis();
        cache.put(url, entry);
    }

    private class CacheEntry {
        long storeTime;
        T data;

        public boolean isValid() {
            long delta = System.currentTimeMillis() - storeTime;
            return delta < CACHE_MAX_AGE;
        }

        @Override
        public String toString() {
            return data + " from " + storeTime;
        }
    }

}

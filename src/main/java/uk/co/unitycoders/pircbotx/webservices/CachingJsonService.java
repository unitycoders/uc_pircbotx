package uk.co.unitycoders.pircbotx.webservices;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A JSON service which enforces rate limiting.
 * 
 * This version of the client will allow for caching of results.
 */
public class CachingJsonService <T> extends JsonService<T> {
	private static final Integer CACHE_MAX_AGE = 3600000;
	private Map<URL, CacheEntry> cache;
	
	public CachingJsonService() {
		this.cache = new HashMap<>();
	}
	
	public T makeRequest(URL url, Class<? extends T> responseClass) throws IOException {
		CacheEntry entry = cache.get(url);
		System.out.println(entry+" "+cache);
		if(entry != null && entry.isValid()) {
			System.out.println("[debug] read from cache: "+entry.data);
			assert responseClass.isAssignableFrom(entry.data.getClass());
			return (T)entry.data;
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
		
		public String toString() {
			return data+" from "+storeTime;
		}
	}

}

package com.reveregroup.gwt.imagepreloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class ImagePreloader {
	private static Map<String, Dimensions> dimensionCache = new HashMap<String, Dimensions>();
	
	private static List<ImageLoader> pool = new ArrayList<ImageLoader>();
	
	private static int activeLoaderCount = 0;
	
	private static FlowPanel loadingArea;
	
	public static Dimensions getCachedDimensions(String url) {
		return dimensionCache.get(url);
	}
	
	public static void load(String url, ImageLoadHandler loadHandler) {
		if (dimensionCache.containsKey(url)) {
			if (loadHandler != null) {
				Dimensions cachedDimensions = dimensionCache.get(url);
				if (cachedDimensions != null) {
					ImageLoadEvent event = new ImageLoadEvent(url, cachedDimensions);
					loadHandler.imageLoaded(event);
				} else {
					int index = findUrlInPool(url);
					pool.get(index).addHander(loadHandler);
				}
			}
			return;
		}
		dimensionCache.put(url, null); //mark url as currently loading
		
		init();
		
		ImageLoader loader = null;
		if (pool.size() == activeLoaderCount) {
			//there are no free images in the pool - create a new one
			loader = new ImageLoader();
			pool.add(loader);
		} else {
			//the pool has free image - grab the next one
			loader = pool.get(activeLoaderCount);
		}
		activeLoaderCount++;
		loader.clearHandlers();
		loader.addHander(loadHandler);
		loader.start(url);
	}
	
	private static final LoadHandler onLoad = new LoadHandler() {
		public void onLoad(LoadEvent event) {
			Image image = (Image) event.getSource();
			Dimensions dim = new Dimensions(image.getWidth(), image.getHeight());
			dimensionCache.put(image.getUrl(), dim);
			
			int index = findImageInPool(image);
			ImageLoader loader = pool.get(index);
			
			ImageLoadEvent evt = new ImageLoadEvent(image, dim);
			loader.fireHandlers(evt);
			
			activeLoaderCount--;
			
			pool.set(index, pool.get(activeLoaderCount));
			
			if (evt.isImageTaken()) {
				pool.remove(activeLoaderCount);
			}
		}
	};
	
	public static void retireLoader(Image image) {
		int index = findImageInPool(image);
		if (index >= 0)
			pool.get(index).retire();
	}
	
	private static void init() {
		if (loadingArea == null) {
			loadingArea = new FlowPanel();
			loadingArea.getElement().getStyle().setProperty("visibility", "hidden");
			loadingArea.getElement().getStyle().setProperty("position", "absolute");
			loadingArea.getElement().getStyle().setProperty("width", "1px");
			loadingArea.getElement().getStyle().setProperty("height", "1px");
			loadingArea.getElement().getStyle().setProperty("overflow", "hidden");
			RootPanel.get().add(loadingArea);
		}
	}
	
	private static int findImageInPool(Image image) {
		for (int index = 0; index < activeLoaderCount; index++) {
			if (pool.get(index).imageEquals(image)) {
				return index;
			}
		}
		return -1;
	}
	
	private static int findUrlInPool(String url) {
		for (int index = 0; index < activeLoaderCount; index++) {
			if (pool.get(index).urlEquals(url)) {
				return index;
			}
		}
		return -1;
	}
	
	private static class ImageLoader {
		Image image = new Image();
		HandlerRegistration hr;
		List<ImageLoadHandler> handlers;
		
		public ImageLoader() {
			hr = image.addLoadHandler(onLoad);
			loadingArea.add(image);
		}
		
		public void clearHandlers() {
			if (handlers != null)
				handlers.clear();
		}
		
		public void addHander(ImageLoadHandler handler) {
			if (handler != null) {
				if (handlers == null) {
					handlers = new ArrayList<ImageLoadHandler>(1);
				}
				handlers.add(handler);
			}
		}
		
		public void fireHandlers(ImageLoadEvent event) {
			if (handlers != null) {
				for (ImageLoadHandler handler : handlers) {
					handler.imageLoaded(event);
				}
			}
		}
		
		public void start(String url) {
			image.setUrl(url);
		}
		
		public void retire() {
			hr.removeHandler();
			loadingArea.remove(image);
		}
		
		public boolean imageEquals(Image image) {
			return this.image == image;
		}
		
		public boolean urlEquals(String url) {
			return this.image.getUrl().equals(url);
		}
	}
	
}

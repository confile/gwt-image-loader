package com.reveregroup.gwt.imagepreloader;

import com.google.gwt.event.shared.EventHandler;

public interface ImageLoadHandler extends EventHandler {
	public void imageLoaded(ImageLoadEvent event);
}
package com.reveregroup.gwt.imagepreloader;

import com.google.gwt.event.shared.EventHandler;

public interface ImageLoadHandler extends EventHandler {
	/**
	 * The status of the Facebook connection has changed. For example the user
	 * has logged in or logged out.
	 */
	public void imageLoaded(ImageLoadEvent event);
}
package com.reveregroup.gwt.imagepreloader.demo;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;

public class Demo extends FlexTable implements EntryPoint {

	public void onModuleLoad() {
		ImagePreloader.load("TestImage.gif", new OnLoad(0));
		setWidget(0, 0, new Image("TestImage.gif"));
		
		ImagePreloader.load("TestImage.gif", new OnLoad(1) {
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				setWidget(row, 0, event.takeImage());
				super.imageLoaded(event);
			}
		});

		ImagePreloader.load("TestImage_X.gif", new OnLoad(2));
		setWidget(2, 0, new Image("TestImage_X.gif"));
		
		ImagePreloader.load("TestImage_X.gif", new OnLoad(3) {
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				setWidget(row, 0, event.takeImage());
				super.imageLoaded(event);
			}
		});
		
		
		RootPanel.get("content").add(this);
	}

	private class OnLoad implements ImageLoadHandler {
		
		public OnLoad(int row) {
			this.row = row;
		}
		
		int row;
		
		public void imageLoaded(ImageLoadEvent event) {
			if (event.isLoadFailed()) {
				setText(row, 1, "Image failed to load.");
			} else {
				setText(row, 1, "Image dimensions: " + event.getDimensions().getWidth() + " x " + event.getDimensions().getHeight());
			}
		}
	}
}

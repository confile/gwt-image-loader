package com.reveregroup.gwt.imagepreloader.demo;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.reveregroup.gwt.imagepreloader.FitImage;
import com.reveregroup.gwt.imagepreloader.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.FitImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;

public class Demo extends FlexTable implements EntryPoint {

	public void onModuleLoad() {
		ImagePreloader.load("TestImage.gif", new OnLoad(0));
		setWidget(0, 0, new Image("TestImage.gif"));
		
		ImagePreloader.load("TestImage2.gif", new OnLoad(1) {
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				setWidget(row, 0, event.takeImage());
				super.imageLoaded(event);
			}
		});

		ImagePreloader.load("TestImage_X.gif", new OnLoad(2));
		setWidget(2, 0, new Image("TestImage_X.gif"));
		
		ImagePreloader.load("TestImage_Y.gif", new OnLoad(3) {
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				setWidget(row, 0, event.takeImage());
				super.imageLoaded(event);
			}
		});
		
		FitImage fi = new FitImage("TestImage3.gif", new OnLoad(4));
		fi.setMaxWidth(100);
		fi.setMaxHeight(100);
		setWidget(4, 0, fi);
		
		fi = new FitImage();
		fi.addFitImageLoadHandler(new OnLoad(5));
		fi.setMaxSize(100, 50);
		fi.setUrl("TestImage4.gif");
		setWidget(5, 0 , fi);
		
		setWidget(6, 0, new FitImage("TestImage_Z.gif", 100, 100, new OnLoad(6)));
		
		// --------------------------------------
		
		ImagePreloader.load("TestImage.gif", new OnLoad(7));
		setWidget(7, 0, new Image("TestImage.gif"));
		
		ImagePreloader.load("TestImage.gif", new OnLoad(8) {
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				setWidget(row, 0, event.takeImage());
				super.imageLoaded(event);
			}
		});

		ImagePreloader.load("TestImage_X.gif", new OnLoad(9));
		setWidget(9, 0, new Image("TestImage_X.gif"));
		
		ImagePreloader.load("TestImage_X.gif", new OnLoad(10) {
			@Override
			public void imageLoaded(ImageLoadEvent event) {
				setWidget(row, 0, event.takeImage());
				super.imageLoaded(event);
			}
		});
		
		fi = new FitImage("TestImage.gif", new OnLoad(11));
		fi.setMaxWidth(100);
		fi.setMaxHeight(100);
		setWidget(11, 0, fi);
		
		fi = new FitImage();
		fi.addFitImageLoadHandler(new OnLoad(12));
		fi.setMaxSize(100, 50);
		fi.setUrl("TestImage.gif");
		setWidget(12, 0 , fi);
		
		setWidget(13, 0, new FitImage("TestImage_X.gif", 100, 100, new OnLoad(13)));
		
		RootPanel.get("content").add(this);
	}

	private class OnLoad implements ImageLoadHandler, FitImageLoadHandler {
		
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
		
		public void imageLoaded(FitImageLoadEvent event) {
			if (event.isLoadFailed()) {
				setText(row, 1, "Image failed to load.");
			} else {
				setText(row, 1, "Image dimensions: " + event.getFitImage().getOriginalWidth() + " x " + event.getFitImage().getOriginalHeight());
			}
		}
	}
}

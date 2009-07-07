package com.reveregroup.gwt.imagepreloader;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Image;

public class ImageLoadEvent extends GwtEvent<ImageLoadHandler> {
		private static final Type<ImageLoadHandler> TYPE = new Type<ImageLoadHandler>();

		public ImageLoadEvent(Image image, Dimensions dimensions) {
			this.image = image;
			this.dimensions = dimensions;
		}
		
		public ImageLoadEvent(String url, Dimensions dimensions) {
			this.url = url;
			this.dimensions = dimensions;
		}
		
		protected Image image;
		protected String url;
		protected Dimensions dimensions;
		protected boolean imageTaken;
		
		public Dimensions getDimensions() {
			return dimensions;
		}
		
		public Image takeImage() {
			imageTaken = true;
			if (image == null) {
				return new Image(url);
			} else {
				ImagePreloader.retireLoader(image);
				Image ret = image;
				image = null;
				return ret;
			}
		}
		
		public String getImageUrl() {
			if (url != null)
				return url;
			return image.getUrl();
		}
		
		public boolean isImageTaken() {
			return imageTaken;
		}

		@Override
		protected void dispatch(ImageLoadHandler handler) {
			handler.imageLoaded(this);
		}

		@Override
		public Type<ImageLoadHandler> getAssociatedType() {
			return TYPE;
		}

		public static Type<ImageLoadHandler> getType() {
			return TYPE;
		}
			
	}
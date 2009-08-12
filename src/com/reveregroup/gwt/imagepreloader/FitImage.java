package com.reveregroup.gwt.imagepreloader;

import com.google.gwt.user.client.ui.Image;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;

/**
 * Sub-class of Image that allows maxWidth and maxHeight to be specified. When
 * the image loads it automatically resizes itself to maintain the correct
 * aspect ratio and fit within the maximum dimensions.
 *  
 * @author David Wolverton
 */
public class FitImage extends Image
{
    private Integer maxWidth, maxHeight, fixedWidth, fixedHeight;

    private Double aspectRatio;

    private void resize()
    {
	if (fixedWidth != null)
	{
	    setWidth(fixedWidth);
	    if (fixedHeight != null)
	    {
		setHeight(fixedHeight);
	    }
	    else if (aspectRatio != null)
	    {
		setHeight((int)Math.round(fixedWidth * aspectRatio));
	    } else
	    {
		setHeight(fixedWidth);
	    }
	} else if (fixedHeight != null) {
	    setHeight(fixedHeight);
	    if (aspectRatio != null)
	    {
		setWidth((int)Math.round(fixedHeight / aspectRatio));
	    } else
	    {
		setWidth(fixedHeight);
	    }
	} else if (maxWidth != null) {
	    if (maxHeight != null) {
		if (aspectRatio != null) {
		    double maxAR = ((double)maxHeight) / ((double)maxWidth);
		    if (aspectRatio > maxAR) {
			setHeight(maxHeight);
			setWidth((int)Math.round(maxHeight / aspectRatio));
		    } else {
			setWidth(maxWidth);
			setHeight((int)Math.round(maxWidth * aspectRatio));
		    }
		} else {
		    setWidth(maxWidth);
		    setHeight(maxHeight);
		}
	    } else {
		setWidth(maxWidth);
		if (aspectRatio != null)
		    setHeight((int)Math.round(maxWidth * aspectRatio));
		else
		    setHeight(maxWidth);
	    }
	} else if (maxHeight != null) {
	    setHeight(maxHeight);
	    if (aspectRatio != null)
		setWidth((int)Math.round(maxHeight / aspectRatio));
	    else
		setWidth(maxHeight);
	} else {
	    setWidth((Integer)null);
	    setHeight((Integer)null);
	}
    }

    public FitImage(String url)
    {
	super();
	setUrl(url);
    }
    
    public FitImage(String url, int maxWidth, int maxHeight)
    {
	super();
	this.maxWidth = maxWidth;
	this.maxHeight = maxHeight;
	setUrl(url);
	resize();
    }

    @Override
    public void setUrl(String url)
    {
	super.setUrl(url);
	ImagePreloader.load(url, new ImageLoadHandler()
	{
	    public void imageLoaded(ImageLoadEvent event)
	    {
		aspectRatio = ((double)event.getDimensions().getHeight()) / ((double)event.getDimensions().getWidth());
		resize();
	    }
	});
    }

    public Integer getMaxWidth()
    {
	return maxWidth;
    }

    /**
     * The width of the image will never exceed this number of pixels.
     */
    public void setMaxWidth(Integer maxWidth)
    {
	this.maxWidth = maxWidth;
	resize();
    }

    public Integer getMaxHeight()
    {
	return maxHeight;
    }

    /**
     * The height of the image will never exceed this number of pixels.
     */
    public void setMaxHeight(Integer maxHeight)
    {
	this.maxHeight = maxHeight;
	resize();
    }

    public Integer getFixedWidth()
    {
	return fixedWidth;
    }

    /**
     * The exact width (in pixels) for the image. This overrides the max dimension
     * behavior, but preserves aspect ratio if fixedHeight is not also specified.
     */
    public void setFixedWidth(Integer fixedWidth)
    {
	this.fixedWidth = fixedWidth;
	resize();
    }

    public Integer getFixedHeight()
    {
	return fixedHeight;
    }

    /**
     * The exact height (in pixels) for the image. This overrides the max dimension
     * behavior, but preserves aspect ratio if fixedWidth is not also specified.
     */
    public void setFixedHeight(Integer fixedHeight)
    {
	this.fixedHeight = fixedHeight;
	resize();
    }

    private void setHeight(Integer px)
    {
	if (px == null)
	    setHeight(""); //getElement().removeAttribute("height");
	else
	    setHeight(px + "px"); //getElement().setAttribute("height", px + "px");
    }

    private void setWidth(Integer px)
    {
	if (px == null)
	    setWidth(""); //getElement().removeAttribute("width");
	else
	    setWidth(px + "px"); //getElement().setAttribute("width", px + "px");
    }
}

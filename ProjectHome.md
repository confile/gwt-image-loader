This utility has two purposes

1. To pre-load images with a single line of code, and reliably inform your code when loading is complete.

2. To get the original dimensions of an image. This is particularly useful for resizing images while maintaining aspect ratio. In fact, this case is so common that the library also includes a subclass of Image that automatically resizes itself and preserves aspect ratio.

### Usage ###
Preload an image.
```
ImagePreloader.load(<IMAGE URL>, null);
```

Preload an image, and display the dimensions when it finishes loading.
```
ImagePreloader.load(<IMAGE URL>, new ImageLoadHander() {
    public void imageLoaded(ImageLoadEvent event) {
        if (event.isLoadFailed())
            Window.alert("Image " + event.getImageUrl() + " failed to load.");
        else
            Window.alert("Image " + event.getImageUrl() + " loaded ("
                + event.getDimensions.getWidth() + " x "
                + event.getDimensions.getHeight() + ")");
    }
});
```

Display an image that fits in a 100px by 100px box, but maintains correct aspect ratio.
```
//syntax option 1
FitImage image = new FitImage();
image.setUrl(<IMAGE_URL>);
image.setMaxSize(100, 100);
yourPanel.add(image);

//syntax option 2 (specify parameters in constructor)
FitImage image = new FitImage(<IMAGE_URL>, 100, 100);
yourPanel.add(image);
```

Display an image that is exactly 100px wide, but adjusts height automatically to maintain aspect ratio.
```
FitImage image = new FitImage(<IMAGE_URL>);
image.setFixedWidth(100);
yourPanel.add(image);
```

Wait for the FitImage to load, and display the original dimensions.
```
FitImage image = new FitImage();
image.addFitImageLoadHandler(new FitImageLoadHander() {
    public void imageLoaded(FitImageLoadEvent event) {
        if (event.isLoadFailed())
            Window.alert("Image " + event.getFitImage().getUrl() + " failed to load.");
        else
            Window.alert("Image " + event.getFitImage().getUrl() + " loaded ("
                + event.getFitImage.getOriginalWidth() + " x "
                + event.getFitImage.getOriginalHeight() + ")");
    }
});
image.setUrl(<IMAGE_URL>);
image.setMaxSize(100, 100);
yourPanel.add(image);
```

### Installation ###
  1. Download JAR from right sidebar
  1. Add the jar to your GWT project build path
  1. Add this line to your `*`.gwt.xml file
> `<inherits name="com.reveregroup.gwt.imagepreloader.ImagePreloader" />`

Also check out the demo code here that uses ImagePreloader and FitImage http://code.google.com/p/gwt-image-loader/source/browse/#svn/trunk/src/com/reveregroup/gwt/imagepreloader/demo
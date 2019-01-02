# jlibvips

A Java interface to [llibvips](http://libvips.github.io/libvips/), the fast image processing library with low memory needs.

**Example: Generate a Thumbnail for a PDF.**

```java
package jlibvips.example;

import org.jlibvips.*;
import java.nio.file.Paths;
import java.nio.file.Files;

public class PDFThumbnailExample {
    
    public static void main(String[] args) {
        var image = VipsImage.formPdf(Paths.get(args[0]), 0); // Second Parameter is the Page Number
        var thumbnail = image.thumbnail()
            .autoRotate()
            .create();
        var thumbnailFile = thumbnail.jpeg()
            .quality(100)
            .strip()
            .save();
        System.out.printf("Thumbnail generated in '%s'.%n", thumbnailFile.toString());
        System.out.println("Done!");
    }
    
}
```

**Example: Create an Image Pyramid form a large PNG File.**


```java
package jlibvips.example;

import org.jlibvips.VipsImage;
import java.nio.file.Paths;

public class ImagePyramidExample {
    
    public static void main(String[] args) {
        var image = VipsImage.formFile(Paths.get(args[0]));
        var directory = Files.createTempDirectory("example-pyramid");
        image.deepZoom(directory)
            .layout(DeepZoomLayouts.Google)
            .container(DeepZoomContainer.FileSystem)
            .suffix(".jpg[Q=100]")
            .save();
        System.out.printf("Pyramid generated in folder '%s'.%n", directory.toString());
        System.out.println("Done.");
    }
    
}
```
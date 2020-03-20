# SRE

SRE is a 2d graphics library for Java.

## Installation

Head over to the releases tab and download the latest jar. Once that's done, add this to your pom.xml


```xml
    <dependencies>
        <dependency>
            <groupId>me.legend</groupId>
            <artifactId>SRE</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependency>
```

## Usage
Basic application example, with drawing a single line in the through the center
```java
public class Main {
    public static void main(String... args){
        Application application = new Application(1280, 720);
        application.setKeypressListener(new keyCallBack());
        application.setRenderLoop(new RenderLoop());
        application.init();
    }
}

class keyCallBack implements KeypressListener {
    @Override
    public void keyPressed(int keycode) {
        System.out.println("Keycode: " + keycode);
    }
}

class RenderLoop implements Renderable {
    @Override
    public void render(GraphicsManager graphics) {
        graphics.colour(1, 1, 1);
        graphics.drawLine(0, 400, 1280, 400);
    }
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)

#### Notes
```
// Coordinate System
(-1, -1) : Top Left
(1, 1) : Bottom Right
```
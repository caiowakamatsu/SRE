package me.legend;

import sun.applet.AppletListener;

public class Main {

    public static void main(String... args){
        Application application = new Application(1280, 720);
        application.enableVSync();
    }

}

package com.inzynier.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inzynier.game.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

            config.title = "Engineer";
            config.width = MyGame.WIDTH * MyGame.SCALE;
            config.height = MyGame.HEIGHT * MyGame.SCALE;

            new LwjglApplication(new MyGame(), config);
	}
}

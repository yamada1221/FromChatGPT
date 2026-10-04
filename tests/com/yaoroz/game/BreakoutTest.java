package com.yaoroz.game;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import javax.swing.SwingUtilities;

public class BreakoutTest {
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			try {
				Breakout game = new Breakout();
				placeBall(game, 110, 30, 5);
				game.update();
				check(!(boolean) get(game, "blockExistence"), "hit must remove block");
				check((int) get(game, "dy") < 0, "first hit must bounce");
				placeBall(game, 110, 30, 5);
				game.update();
				check((int) get(game, "dy") > 0, "removed block must not bounce again");
				placeBall(game, 350, 510, 5);
				game.update();
				game.update();
				check((int) get(game, "dy") < 0, "paddle bounce must not reverse again on the next frame");
				check(((Rectangle) get(game, "ball")).y < 500, "ball must move away from paddle");
				check(game.isFocusable(), "keyboard panel must be focusable");
			} catch (Exception error) { throw new RuntimeException(error); }
		});
		System.out.println("PASS: removed blocks, paddle separation and keyboard focus");
	}
	private static void placeBall(Breakout game, int x, int y, int dy) throws Exception {
		set(game, "ball", new Rectangle(x, y, 20, 20));
		set(game, "ballCenter", new Point(x + 10, y + 10));
		set(game, "dx", 0);
		set(game, "dy", dy);
	}
	private static Object get(Object object, String name) throws Exception {
		Field field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.get(object);
	}
	private static void set(Object object, String name, Object value) throws Exception {
		Field field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, value);
	}
	private static void check(boolean value, String message) {
		if (!value) throw new AssertionError(message);
	}
}

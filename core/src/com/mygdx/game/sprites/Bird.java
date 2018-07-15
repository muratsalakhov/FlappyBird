package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Flappy;

public class Bird {
    public static final float MOVEMENT = 0.23f * Flappy.WIDTH;
    public static final float GRAVITY = -Flappy.HEIGHT * 0.03125f;
    private Vector2 velocity;
    private Rectangle bounds;
    public static int status = 1;
    private Texture bird;


    public Bird(float x, float y) {
        this((int) x, (int) y);
    }

    public Bird(int x, int y) {
        velocity = new Vector2(0, 0);
        switch (status) {
            case 1:
                bird = new Texture("steve.png");
                break;
            case 2:
                bird = new Texture("vanessa.png");
                break;
            case 3:
                bird = new Texture("joe.png");
                break;
            default:
                bird = new Texture("steve.png");
                break;
        }
        bounds = new Rectangle(x, y, 0.07f * Flappy.WIDTH, 0.07f * Flappy.WIDTH);// bird.getWidth(), bird.getHeight());
    }


    public Vector2 getPosition() {
        return new Vector2(bounds.getX(), bounds.getY());
    }

    public Texture getBird() {
        return bird;
    }

    public void update(float dt) {
        Vector2 position = getPosition();
        if (position.y > 0)
            velocity.add(0, GRAVITY);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y);
        if (position.y < 0)
            position.y = 0;
        if (position.y > (Flappy.HEIGHT / 2f - bounds.getHeight()))
            position.y = (Flappy.HEIGHT / 2f - bounds.getHeight());
        velocity.scl(1f / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 0.57f * Flappy.HEIGHT;
    }

    public float getWidth() {
        return bounds.getWidth();
    }

    Rectangle getBounds() {
        return bounds;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(bird, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void dispose() {
        bird.dispose();
    }
}

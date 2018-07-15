package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Flappy;

import java.util.Random;

import static com.mygdx.game.states.PlayState.TUBE_SPACING;

public class Tube {
    public static final float TUBE_WIDTH = Flappy.WIDTH * 0.1f;
    public static final float TUBE_HEIGHT = 0.4f * Flappy.HEIGHT;

    public final int FLUCTUATION = (int) (Flappy.HEIGHT * 0.1625f);
    public final float TUBE_GAP = Flappy.HEIGHT * 0.1625f;
    public final float LOWEST_OPENING = Flappy.HEIGHT * 0.1625f;


    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Random rand;

    private Rectangle boundsTop, boundsBot;

    public float getTopTubeX() {
        return posTopTube.x;
    }

    public Tube(float x, int a) {
        if (a < 0 || a >= 9)
            a = 0;

        topTube = new Texture("tube" + (a + 1) + "-2.png");
        bottomTube = new Texture("tube" + (a + 1) + ".png");

        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - TUBE_HEIGHT);

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, TUBE_WIDTH, TUBE_HEIGHT);
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, TUBE_WIDTH, TUBE_HEIGHT);
    }

    public void reposition(float x) {
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - TUBE_HEIGHT);
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(topTube, posTopTube.x, posTopTube.y, TUBE_WIDTH, TUBE_HEIGHT);
        batch.draw(bottomTube, posBotTube.x, posBotTube.y, TUBE_WIDTH, TUBE_HEIGHT);
    }

    public boolean collision(Bird player) {
        return player.getBounds().overlaps(boundsTop) || player.getBounds().overlaps(boundsBot);
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }

    public boolean success(Bird bird) {
        if(bird.getPosition().x > posTopTube.x + TUBE_WIDTH * 1.1f &&
                bird.getPosition().x - posTopTube.x - TUBE_WIDTH * 1.1f < TUBE_SPACING) {
            return true;
        }
        return false;
    }

    public boolean inside(Bird bird) {
        return bird.getPosition().x > posTopTube.x && bird.getPosition().x < posTopTube.x + TUBE_WIDTH;
    }
}

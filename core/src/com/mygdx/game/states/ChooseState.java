package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Flappy;
import com.mygdx.game.sprites.Bird;

public class ChooseState extends State {

    private Texture background;
    private Sprite chooseTable;

    private Sprite[] birds;

    public ChooseState(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.png");

        chooseTable = new Sprite(new Texture("choose.png"));
        chooseTable.setPosition(0, Flappy.HEIGHT / 2f);
        chooseTable.setSize(Flappy.WIDTH, 0.3f * Flappy.HEIGHT);


        float chooseBirdHeight = Flappy.HEIGHT * 0.23f;
        float chooseBirdWidth = Flappy.WIDTH * 0.29f;
        float deltaBird = (Flappy.WIDTH - chooseBirdWidth * 3) / 4f;
        float birdY = Flappy.HEIGHT / 2f - 2 * deltaBird - chooseBirdHeight;


        birds = new Sprite[3];
        for (int i = 0; i < birds.length; i++) {
            birds[i] = new Sprite(new Texture("choose" + (i + 1) + ".png"));
            birds[i].setPosition((i + 1) * deltaBird + chooseBirdWidth * i, birdY);
            birds[i].setSize(chooseBirdWidth, chooseBirdHeight);
        }
    }

    @Override
    protected void handleInput() {
        Vector2 touchPos = new Vector2();
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Flappy.HEIGHT - Gdx.input.getY());

            for (int i = 0; i < birds.length; i++) {
                if (birds[i].getBoundingRectangle().contains(touchPos)) {
                    Bird.status = i + 1;
                    gsm.set(new PlayState(gsm));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Flappy.WIDTH, Flappy.HEIGHT);

        chooseTable.draw(sb);
        for (Sprite bird : birds) {
            bird.draw(sb);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        chooseTable.getTexture().dispose();
        for (Sprite bird : birds) {
            bird.getTexture().dispose();
        }
    }
}

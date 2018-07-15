package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Flappy;

public class MenuState extends State {

    private Texture background;
    private Sprite playBtn;
    private Sprite chooseBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Flappy.WIDTH / 2f, Flappy.HEIGHT / 2f);
        background = new Texture("background.png");

        playBtn = new Sprite(new Texture("playbtn.png"));
        playBtn.setSize(0.52f * Flappy.WIDTH, 0.15625f * Flappy.HEIGHT);
        playBtn.setPosition((Flappy.WIDTH / 2f) - playBtn.getWidth() / 2f, (Flappy.HEIGHT / 2f));

        chooseBtn = new Sprite(new Texture("chooseMenu.png"));
        chooseBtn.setSize(0.52f * Flappy.WIDTH, 0.15625f * Flappy.HEIGHT);
        chooseBtn.setPosition((Flappy.WIDTH / 2f) - chooseBtn.getWidth() / 2f, (Flappy.HEIGHT / 2f) - playBtn.getHeight() - 10);
    }

    @Override
    protected void handleInput() {
        Vector2 touchPos = new Vector2();
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Flappy.HEIGHT - Gdx.input.getY());
            //camera.unproject(touchPos);

            if (playBtn.getBoundingRectangle().contains(touchPos)) {
                gsm.set(new PlayState(gsm));
            } else if (chooseBtn.getBoundingRectangle().contains(touchPos)) {
                gsm.set(new ChooseState(gsm));
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Flappy.WIDTH, Flappy.HEIGHT);
        playBtn.draw(sb);
        chooseBtn.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.getTexture().dispose();
        chooseBtn.getTexture().dispose();
    }
}

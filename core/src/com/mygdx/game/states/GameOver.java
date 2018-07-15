package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Flappy;
import com.mygdx.game.sprites.FontUtils;
import com.mygdx.game.sprites.MyText;

import javax.xml.soap.Text;

public class GameOver extends State{

    private Texture background;
    private Texture gameover;
    private MyText pointsText;
    public SpriteBatch textBatch;

    public GameOver(GameStateManager gsm, int points) {
        super(gsm);
        camera.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2);
        background = new Texture("background.png");
        gameover = new Texture("gameover.png");

        pointsText = new MyText(FontUtils.generateFont(), "Ваш счет: " + points, Flappy.HEIGHT * 0.06f);
        pointsText.setPosition(Flappy.WIDTH / 2f - pointsText.getWidth() / 2f,
                Flappy.HEIGHT / 2f - gameover.getHeight() / 2f - pointsText.getHeight() * 1.2f);
        textBatch = new SpriteBatch();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            gsm.set(new PlayState(gsm));
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Flappy.WIDTH, Flappy.HEIGHT);
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y - gameover.getHeight() / 2f);
        sb.end();

        textBatch.begin();
        pointsText.draw(textBatch);
        textBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
    }
}

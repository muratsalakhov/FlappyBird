package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Flappy;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.FontUtils;
import com.mygdx.game.sprites.MyText;
import com.mygdx.game.sprites.Tube;

public class PlayState extends State {

    public static final float TUBE_SPACING = Flappy.WIDTH * 0.26f;
    private final int TUBE_COUNT = 9;

    private Bird bird;
    private Texture background;

    private int points;
    private Array<Tube> tubes;
    private MyText pointsText;
    public SpriteBatch textBatch;

    private boolean freezePointInc;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        points = 0;
        freezePointInc = false;
        bird = new Bird(Flappy.WIDTH * 0.1f, Flappy.HEIGHT * 0.375f);
        camera.setToOrtho(false, Flappy.WIDTH / 2f, Flappy.HEIGHT / 2f);
        background = new Texture("background.png");

        tubes = new Array<Tube>();

        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH) + bird.getWidth() * 10, i));
        }

        textBatch = new SpriteBatch();
        pointsText = new MyText(FontUtils.generateFont(), "0", Flappy.HEIGHT * 0.06f);
        pointsText.setPosition(Flappy.WIDTH - pointsText.getWidth() * 1.2f, Flappy.HEIGHT * 0.98f);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            bird.jump();

    }

    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + Flappy.WIDTH * 0.167f;

        for (int i = 0; i < tubes.size; i++) {

            Tube tube = tubes.get(i);
            if (camera.position.x - (camera.viewportWidth / 2f) > tube.getTopTubeX() + Tube.TUBE_WIDTH) {
                tube.reposition(tube.getTopTubeX() + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collision(bird))
                gsm.set(new GameOver(gsm, points));

            if(!freezePointInc && tube.success(bird)) {
                points++;
                pointsText.setMessage(String.valueOf(points));
                pointsText.setPosition(Flappy.WIDTH - pointsText.getWidth() * 1.2f, Flappy.HEIGHT * 0.98f);
                freezePointInc = true;
            }

            if(freezePointInc && tube.inside(bird)) {
                freezePointInc = false;
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2f), 0, Flappy.WIDTH, Flappy.HEIGHT);
        bird.draw(sb);
        for (Tube tube : tubes) {
            tube.draw(sb);
        }
        sb.end();

        textBatch.begin();
        pointsText.draw(textBatch);
        textBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        pointsText.dispose();
    }
}

package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import utilz.LoadSave;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private HashMap<String, BufferedImage[]> mapAni;
	private int aniTick, aniIndex, aniSpeed = 25;
	private String playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	private float xDrawOffset = 28 * Game.SCALE;
	private float yDrawOffset = 65 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -3.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);

	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		//g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
//		drawHitbox(g);

		g.drawImage(mapAni.get(playerAction)[aniIndex], (int)(hitbox.x - xDrawOffset),(int)(hitbox.y - yDrawOffset), (int)(95 * Game.SCALE),  (int)(95 * Game.SCALE), null);
		drawHitbox(g);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}

	private void setAnimation() {
		String startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

//		if (inAir) {
//			if (airSpeed < 0)
//				playerAction = JUMP;
//			else
//				playerAction = FALLING;
//		}

		if (attacking)
			playerAction = ATTACK;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();
		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

//		animations = new BufferedImage[9][6];
//		for (int j = 0; j < animations.length; j++)
//			for (int i = 0; i < animations[j].length; i++)
//				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

		// load hanh dong
		mapAni = new HashMap<>();

		//1 chem
		String name = ATTACK;
		BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.GOKU_CHEM);
		BufferedImage[] chem = new BufferedImage[4];
		for(int i = 0; i < chem.length; i++){
			chem[i] = image.getSubimage(i * 95, 0, 95, 95);
		}
		mapAni.put(name, chem);

		//idle
		String name1 = IDLE;
		BufferedImage image1 = LoadSave.GetSpriteAtlas(LoadSave.GOKU_IDLE);
		BufferedImage[] idle = new BufferedImage[5];
		for(int i = 0; i < idle.length; i++){
			idle[i] = image1.getSubimage(i * 95, 0, 95, 95);
		}
		mapAni.put(name1, idle);

		//run
		String name2 = RUNNING;
		BufferedImage image2 = LoadSave.GetSpriteAtlas(LoadSave.GOKU_RUN);
		BufferedImage[] run = new BufferedImage[3];
		for(int i = 0; i < run.length; i++){
			run[i] = image2.getSubimage(i * 95, 0, 95, 95);
		}
		mapAni.put(name2, run);

	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;

	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

}
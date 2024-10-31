package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

	public static final String PLAYER_ATLAS = "res/player_sprites.png";
	public static final String LEVEL_ATLAS = "res/outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "res/level_one_data_long.png";
	public static final String GOKU_CHEM = "res/chem.png";
	public static final String GOKU_IDLE = "res/idle.png";
	public static final String GOKU_RUN = "res/walk.png";
	public static final String MENU_BUTTONS = "res/button_atlas.png";
	public static final String MENU_BACKGROUND = "res/menu_background.png";
	public static final String PAUSE_BACKGROUND = "res/pause_menu.png";
	public static final String SOUND_BUTTONS = "res/sound_button.png";
	public static final String URM_BUTTONS = "res/urm_buttons.png";
	public static final String VOLUME_BUTTONS = "res/volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "res/background_menu.png";
	public static final String PLAYING_BACKGROUND_IMG = "res/playing_bg_img.png";
	public static final String BIG_CLOUDS = "res/big_clouds.png";
	public static final String SMALL_CLOUDS = "res/small_clouds.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static int[][] GetLevelData() {

		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
}

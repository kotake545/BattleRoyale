package com.tyoku.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import com.tyoku.BattleRoyale;
import com.tyoku.util.BRUtils;

/**
 * ゲーム用のマップ絵画
 * @author tyoku
 *
 */
public class BrMapRender extends MapRenderer {
	private BattleRoyale plugin;

	public BrMapRender(BattleRoyale plugin) {
		this.plugin = plugin;
	}

	@Override
	public void render(MapView paramMapView, MapCanvas paramMapCanvas, Player paramPlayer) {
		//格子絵画
		int canvassize = 128;
		int padding = 10;
		int innersize = 8;
		byte lineColor = MapPalette.LIGHT_GRAY;
		for(int i = 0; i < canvassize; i+=padding){
			for(int j = 0; j < canvassize; j++){
				paramMapCanvas.setPixel( i, j, lineColor );
				paramMapCanvas.setPixel( j, i, lineColor );
			}
		}

		//デッドゾーンを赤塗り
		for(String brk : plugin.getDeadAreaBlocks()){
			int[] pos = BRUtils.brMapBlok2XYs(brk);
			for(int i = pos[0]; i < pos[0]+innersize; i++){
				for(int j = pos[1]; i < pos[1]+innersize; i++){
					paramMapCanvas.setPixel( i, j, MapPalette.RED );
				}
			}

		}

		//座標文字列絵画
		int count = 0;
		String[] numary = new String[]{"1","2","3","4","5","6","7","8","9","1","2","3","4","5"};
		String[] alphaary = new String[]{"a","b","c","d","e","f","j","h","i","j","k","l","m","n"};
		for(int i = 0; i < canvassize; i+=padding){
			paramMapCanvas.drawText(i+4, 0, MinecraftFont.Font, numary[count]);
			paramMapCanvas.drawText(0, i+4, MinecraftFont.Font, alphaary[count]);
			count++;
		}

		//Scale.FARで大体1マス80x80ブロック



		//既存カーソル削除
//        MapCursorCollection mcc = paramMapCanvas.getCursors();
//        while (mcc.size() > 0) {
//        	mcc.removeCursor(mcc.getCursor(0));
//        }
	}
}

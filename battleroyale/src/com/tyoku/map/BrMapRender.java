package com.tyoku.map;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import com.tyoku.BattleRoyale;

public class BrMapRender extends MapRenderer {
	@SuppressWarnings("unused")
	private Logger log;
	@SuppressWarnings("unused")
	private BattleRoyale plugin;

	public BrMapRender(BattleRoyale plugin) {
		this.plugin = plugin;
		this.log = plugin.getLogger();
	}

	@Override
	public void render(MapView paramMapView, MapCanvas paramMapCanvas, Player paramPlayer) {
		//log.info(String.format("マップレンダー：%s", paramPlayer.getName()));
		int canvassize = 127;
		int padding = 10;
		byte lineColor = MapPalette.LIGHT_GRAY;
		for(int i = 0; i < canvassize; i+=padding){
			for(int j = 0; j < canvassize; j++){
				paramMapCanvas.setPixel( i, j, lineColor );
				paramMapCanvas.setPixel( j, i, lineColor );
			}
		}
	}
}

package com.tyoku.listener;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

import com.tyoku.BattleRoyale;

public class MapListener implements Listener {
	private Logger log;
	@SuppressWarnings("unused")
	private BattleRoyale plugin;

	public MapListener(BattleRoyale battleRoyale) {
		this.plugin = battleRoyale;
		this.log = battleRoyale.getLogger();
	}

	@EventHandler
	public void onMapInit(MapInitializeEvent event){
		MapView mv = event.getMap();
		mv.setScale(Scale.FAR);
		this.log.info(String.format("マップRenderSize=%d", mv.getRenderers().size()));
		//mv.addRenderer(new BrMapRender());

		int cx = mv.getCenterX();
		int cz = mv.getCenterZ();
		this.log.info(String.format("マップの中央座標(X:%d,Z:%d)", cx,cz));
	}
}

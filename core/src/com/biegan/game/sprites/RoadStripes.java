package com.biegan.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class RoadStripes {

    public void updateRoadStripes(TiledMap map) {

        TiledMapTileLayer roadLayer = (TiledMapTileLayer) map.getLayers().get("lanes");
    }
}
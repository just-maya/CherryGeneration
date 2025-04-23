package me.justmaya.cherryGeneration;

import me.justmaya.cherryGeneration.generator.CherryChunkGenerator;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public final class CherryGeneration extends JavaPlugin {
    @Override
    public void onEnable() {
        WorldCreator worldCreator = new WorldCreator("CherryWorld");
        worldCreator.generator(new CherryChunkGenerator());
        getServer().createWorld(worldCreator);
        getLogger().info("CherryWorld was created!");
    }
}

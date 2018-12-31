package pl.daffit.pinkantibot;

import net.md_5.bungee.api.plugin.Plugin;

public class PinkAntiBotPlugin extends Plugin {

    @Override
    public void onEnable() {
        this.getProxy().getPluginManager().registerListener(this, new PinkAntiBotListener());
    }
}

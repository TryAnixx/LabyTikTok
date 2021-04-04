package de.labytiktok;

import de.labytiktok.modules.FollowerModule;
import de.labytiktok.utils.WebUtils;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.net.MalformedURLException;
import java.util.List;

public class LabyTikTok extends LabyModAddon {
    private WebUtils webUtils;
    private String username;
    private boolean init;
    private static LabyTikTok instance;
    @Override
    public void onEnable() {
        instance = this;
        webUtils = new WebUtils();

        api.registerModule(new FollowerModule());
        webUtils.setURL("https://api.tiktokcounter.com/?type=stats&username=");
        webUtils.setRequestInterval(15);
        api.registerForgeListener(this);
    }

    @Override
    public void loadConfig() {
        this.username = getConfig().has("username") ? getConfig().get("username").getAsString() : "labymod";
        webUtils.setUsername(username.toLowerCase());
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        list.add(new StringElement("Username", new ControlElement.IconData(Material.NAME_TAG), username, new Consumer<String>() {
            @Override
            public void accept(String s) {
                username = s.toLowerCase();
                getConfig().addProperty("username", username.toLowerCase());
                saveConfig();
                webUtils.setUsername(username.toLowerCase());
            }
        }));
    }

    public static LabyTikTok getInstance() {
        return instance;
    }

    public WebUtils getWebUtils() {
        return webUtils;
    }

    @SubscribeEvent
    public void handle(TickEvent.ClientTickEvent event){
        if(!init){
            try {
                webUtils.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            init = true;
        }
    }
}
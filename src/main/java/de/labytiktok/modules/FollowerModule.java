package de.labytiktok.modules;

import de.labytiktok.LabyTikTok;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.enums.EnumDisplayType;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class FollowerModule extends SimpleModule {
    @Override
    public String getDisplayName() {
        return "TikTok Follower";
    }

    @Override
    public String getDisplayValue() {
        return String.valueOf(LabyTikTok.getInstance().getWebUtils().getFollower());
    }

    @Override
    public String getDefaultValue() {
        return "loading...";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.NAME_TAG);
    }

    @Override
    public void loadSettings() {

    }

    @Override
    public boolean isShown() {
        return LabyTikTok.getInstance().isEnabled();
    }

    @Override
    public String getSettingName() {
        return "TikTok Follower";
    }

    @Override
    public String getDescription() {
        return "Shows any user´s TikTok follower count";
    }

    @Override
    public String getControlName() {
        return "TikTok Follower";
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_EXTERNAL_SERVICES;
    }

    @Override
    public int getSortingId() {
        return 0;
    }
}

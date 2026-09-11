package com.noinvbg;

import cpw.mods.fml.relauncher.IFMLLoadPlugin;
import java.util.Map;

@IFMLLoadPlugin.MCVersion("1.7.10")
@IFMLLoadPlugin.TransformerExclusions({"com.noinvbg"})
public class NoInvBGCoreMod implements IFMLLoadPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.noinvbg.NoInvBGTransformer" };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}

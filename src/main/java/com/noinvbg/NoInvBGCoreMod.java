package com.noinvbg;

import java.util.Map;

public class NoInvBGCoreMod {

    public String[] getASMTransformerClass() {
        return new String[] { "com.noinvbg.NoInvBGTransformer" };
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }
}

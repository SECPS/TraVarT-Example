package at.jku.cps.travart;

import at.jku.cps.travart.core.helpers.TraVarTPluginManager;

public class TraVarT {
    public static void main(String[] args) {
        TraVarTPluginManager.startPlugins();
        TraVarTPluginManager.getAvailablePlugins();
        // whatever we want to do
        // TraVarTPluginManager.availablePlugins.get("ppr-dsl-plugin")
        TraVarTPluginManager.stopPlugins();
    }
}

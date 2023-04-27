/*******************************************************************************
 * TODO: explanation what the class does
 *  
 * @author Kevin Feichtinger
 *  
 * Copyright 2023 Johannes Kepler University Linz
 * LIT Cyber-Physical Systems Lab
 * All rights reserved
 *******************************************************************************/
package at.jku.cps.travart;

import static at.jku.cps.travart.Constants.IVML_PLUGIN_NAME;
import static at.jku.cps.travart.Constants.RESOURCES_PATH;

import at.jku.cps.travart.core.common.IPlugin;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.core.exception.PluginNotFoundException;
import at.jku.cps.travart.core.helpers.TraVarTPluginManager;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TraVarT {
    static {
        TraVarTPluginManager.startPlugins();
    }

    public static void main(final String[] args)
        throws NotSupportedVariabilityTypeException, IOException, PluginNotFoundException {
        final Path baseFolderPath =
            Paths.get(Paths.get(".").toRealPath().toString(), RESOURCES_PATH);

        final TraVarTTransformer traVarTTransformer = new TraVarTTransformer();
        runTransformation(baseFolderPath, traVarTTransformer, IVML_PLUGIN_NAME, IVML_PLUGIN_NAME);

        // Final step: Deactivate Plugin Manager
        TraVarTPluginManager.stopPlugins();
    }

    private static void runTransformation(final Path baseFolder, final TraVarTTransformer traVarTTransformer, final String inputPluginName,
                                          final String outputPluginName)
        throws NotSupportedVariabilityTypeException, IOException, PluginNotFoundException {
        // checks if the plugin is there in the system
        final IPlugin inputPlugin = TraVarTPluginManager.getAvailablePlugins().get(inputPluginName);
        if (inputPlugin == null) {
            throw new PluginNotFoundException("Plugin not found with name - " + inputPluginName);
        }

        final IPlugin outputPlugin = TraVarTPluginManager.getAvailablePlugins().get(outputPluginName);
        if (outputPlugin == null) {
            throw new PluginNotFoundException("Plugin not found with name - " + outputPluginName);
        }

        traVarTTransformer.transform(
            inputPlugin,
            outputPlugin,
            baseFolder
        );
    }
}

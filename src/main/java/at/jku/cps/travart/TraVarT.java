package at.jku.cps.travart;

import static at.jku.cps.travart.Constants.RESOURCES_PATH;

import at.jku.cps.travart.core.common.IPlugin;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.core.exception.PluginNotFoundException;
import at.jku.cps.travart.core.helpers.TraVarTPluginManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TraVarT {

    static {
        TraVarTPluginManager.startPlugins();
    }

    public static void main(final String[] args)
        throws NotSupportedVariabilityTypeException, IOException, PluginNotFoundException,
        URISyntaxException {
        TraVarTPluginManager.startPlugins();
        TraVarTPluginManager.getAvailablePlugins();
        final Path baseFolderPath =
            Paths.get(Paths.get(".").toRealPath().toString(), RESOURCES_PATH);

        final TraVarTTransformer traVarTTransformer = new TraVarTTransformer();
        run_IVML_UVL_IVML(baseFolderPath, traVarTTransformer);

        // Final step: Deactivate Plugin Manager
        TraVarTPluginManager.stopPlugins();
    }

    //TODO: it doesn't run; needs fixing
    private static void run_IVML_UVL_IVML(final Path baseFolder,
                                          final TraVarTTransformer traVarTTransformer)
        throws NotSupportedVariabilityTypeException, IOException, PluginNotFoundException {
        // checks if the plugin is there in the system
        final IPlugin ivmlPlugin = TraVarTPluginManager.getAvailablePlugins().get("ivml-plugin");
        traVarTTransformer.transform(
            ivmlPlugin,
            ivmlPlugin,
            baseFolder
        );
    }
}

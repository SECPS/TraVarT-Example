package at.jku.cps.travart;

import static at.jku.cps.travart.Constants.INPUT_RESOURCE_FOLDER;
import static at.jku.cps.travart.Constants.OUTPUT_RESOURCE_FOLDER;

import at.jku.cps.travart.core.FeatureModelStatistics;
import at.jku.cps.travart.core.common.IPlugin;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.core.helpers.TraVarTUtils;
import at.jku.cps.travart.core.io.UVLWriter;
import de.vill.model.FeatureModel;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Logger;

public class TraVarTTransformer<T, V> {
    public void transform(final IPlugin<T> input, final IPlugin<V> output, final Path basePath)
        throws NotSupportedVariabilityTypeException, IOException {
        //initializations
        final Logger logger = TraVarTUtils.getSimpleLogger(TraVarTTransformer.class.getName(), "transformation");
        final Path inputFolderPath = Paths.get(basePath.toString(), INPUT_RESOURCE_FOLDER, input.getName().toLowerCase());
        final Set<Path> inputPathSet = TraVarTUtils.getPathSet(
            inputFolderPath,
            input.getSupportedFileExtensions().get(0)
        );
        final Path intermediateBasePath = Paths.get(basePath.toString(), OUTPUT_RESOURCE_FOLDER, "uvl");
        if (!intermediateBasePath.toFile().exists()) {
            intermediateBasePath.toFile().mkdir();
        }
        final Path outputBasePath = Paths.get(basePath.toString(), OUTPUT_RESOURCE_FOLDER, output.getName().toLowerCase());
        if (!outputBasePath.toFile().exists()) {
            outputBasePath.toFile().mkdir();
        }
        final UVLWriter uvlWriter = new UVLWriter();
        final FeatureModelStatistics featureModelStatistics = new FeatureModelStatistics();

        // transformations
        for (final Path inp : inputPathSet) {
            final String fileName = inp.getFileName().toString().split("\\.")[0];
            final Path intermediatePath = Paths.get(intermediateBasePath + "/" + fileName + ".uvl");
            final Path outputPath = Paths.get(outputBasePath + "/" + fileName + output.getSupportedFileExtensions().get(0));

            final T inputModel = input.getReader().read(inp);
            input.getStatistics();

            final FeatureModel intermediateFeatureModel = input.getTransformer().transform(inputModel);
            uvlWriter.write(intermediateFeatureModel, intermediatePath);
            featureModelStatistics.logModelStatistics(logger, intermediateFeatureModel);

            final V outputModel = output.getTransformer().transform(intermediateFeatureModel);
            output.getWriter().write(outputModel, outputPath);
            output.getStatistics();
        }
    }
}

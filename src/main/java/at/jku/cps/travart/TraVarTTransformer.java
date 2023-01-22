package at.jku.cps.travart;

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

import static at.jku.cps.travart.Constants.INPUT_RESOURCE_FOLDER;
import static at.jku.cps.travart.Constants.OUTPUT_RESOURCE_FOLDER;

public class TraVarTTransformer {
    public void transform(final IPlugin input, final IPlugin output, final Path basePath) throws NotSupportedVariabilityTypeException, IOException {
        final Logger logger = TraVarTUtils.getSimpleLogger(TraVarTTransformer.class.getName(), "transformation");
        final Path inputFolderPath = Paths.get(basePath.toString(), INPUT_RESOURCE_FOLDER, input.getName().toLowerCase());
        final Set<Path> inputPathSet = TraVarTUtils.getPathSet(
                inputFolderPath,
                (String) input.getSupportedFileExtensions().get(0)
        );
        final Path intermediatePath = Paths.get(basePath.toString(), OUTPUT_RESOURCE_FOLDER, "uvl");
        if (!intermediatePath.toFile().exists()) {
            intermediatePath.toFile().mkdir();
        }
        final Path outputPath = Paths.get(basePath.toString(), OUTPUT_RESOURCE_FOLDER, output.getName().toLowerCase());
        if (!outputPath.toFile().exists()) {
            outputPath.toFile().mkdir();
        }

        final UVLWriter uvlWriter = new UVLWriter();
        for (final Path inp : inputPathSet) {
            System.out.println(inp);
            final Object inputModel = input.getReader().read(inp);
            input.getStatistics();
            final FeatureModel intermediateFeatureModel = input.getTransformer().transform(inputModel);
            uvlWriter.write(intermediateFeatureModel, intermediatePath);
//            FeatureModelStatistics.logModelStatistics(logger, intermediateFeatureModel);
            final Object outputModel = output.getTransformer().transform(intermediateFeatureModel);
            output.getWriter().write(outputModel, outputPath);
            output.getStatistics();
        }
    }
}

package at.jku.cps.travart;

import at.jku.cps.travart.core.helpers.TraVarTPluginManager;

public class TraVarT {

	static {
		TraVarTPluginManager.startPlugins();
	}

	public static void main(final String[] args) {

		// whatever we want to do
		// TraVarTPluginManager.getAvailablePlugins().size();
		// IPlugin pprPlugin =
		// TraVarTPluginManager.getAvailablePlugins().get("ppr-dsl-plugin");
		// IModelTransformer transformer = pprPlugin.getTransformer();
//		try {
//			transformer.transform(new FeatureModel());
//		} catch (NotSupportedVariabilityTypeException e) {
//			e.printStackTrace();
//		} finally {
		// Final step: Deactivate Plugin Manager
		TraVarTPluginManager.stopPlugins();
//		}

	}
}

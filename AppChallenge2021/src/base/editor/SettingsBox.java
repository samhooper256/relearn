package base.editor;

import java.util.List;

import base.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import topics.settings.TopicSetting;

public class SettingsBox extends VBox implements IndependentlyVerifiable {
	
	private static final String SETTINGS_BOX_CSS = "settings-box";
	
	public SettingsBox(List<TopicSetting> settings) {
		for(TopicSetting setting : settings)
			getChildren().add(TopicSetting.settingNodeFor(setting));
		getStyleClass().add(SETTINGS_BOX_CSS);
	}
	
	@Override
	public VerificationResult verify() {
		VerificationResult result = VerificationResult.success();
		for(Node node : getChildren()) {
			if(node instanceof Verifiable) {
				if(!(node instanceof IndependentlyVerifiable))
					throw new IllegalStateException(String.format("No context to give to %s", node));
				IndependentlyVerifiable iv = (IndependentlyVerifiable) node;
				result = result.and(iv.verify());
			}
		}
		return result;
	}
	
}

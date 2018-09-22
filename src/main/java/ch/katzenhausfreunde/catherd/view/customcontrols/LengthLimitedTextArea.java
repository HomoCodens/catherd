package ch.katzenhausfreunde.catherd.view.customcontrols;

import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

public class LengthLimitedTextArea extends AnchorPane {
	@FXML
	private Label limitLabel;
	
	@FXML
	private TextArea textArea;
	
	private StringProperty text;
	private IntegerProperty limit;
			
	public LengthLimitedTextArea() {
		this(100);
	}
	
	public LengthLimitedTextArea(Integer l) {
		limit = new SimpleIntegerProperty(l);
		text = new SimpleStringProperty(null);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("LengthLimitedTextArea.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
			
	@FXML
	public void initialize() {
		limitLabel.textProperty().bind(Bindings.createStringBinding(() -> {
			return textArea.textProperty().length().get() + "/" + limit.get();
		}, limit, textArea.lengthProperty()));
		
		text.bindBidirectional(textArea.textProperty());
		
		textArea.setTextFormatter(new TextFormatter<>((change) -> {
			change.setText(change.getText().replaceAll("\n", ""));
			if(change.getControlNewText().length() > limit.get()) {
				change.setText(change.getText().substring(0, limit.get() - change.getControlText().length()));
				change.setAnchor(change.getControlAnchor());
				change.setCaretPosition(change.getControlCaretPosition());
				change.selectRange(change.getCaretPosition(), change.getCaretPosition());
				Toolkit.getDefaultToolkit().beep();
			}
			return change;
		}));
		
		// cf https://stackoverflow.com/questions/23728517/blurred-text-in-javafx-textarea
		Platform.runLater(() -> {
			textArea.setCache(false);
			ObservableList<Node> children = textArea.getChildrenUnmodifiable();
			if(children.size() > 0) {
				ScrollPane sp = (ScrollPane)children.get(0);
				sp.setCache(false);
				for (Node n : sp.getChildrenUnmodifiable()) {
				    n.setCache(false);
				}
			}
		});
	}
	
	public String getText() {
		return text.get();
	}
	
	public void setText(String text) {
		this.text.set(text);
	}
	
	public StringProperty textProperty() {
		return text;
	}
	
	public Integer getLimit() {
		return limit.get();
	}
	
	public void setLimit(Integer limit) {
		this.limit.set(limit);
	}
	
	public IntegerProperty limitProperty() {
		return limit;
	}
}

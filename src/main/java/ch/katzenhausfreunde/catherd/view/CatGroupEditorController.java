package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.FinancialItem;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CatGroupEditorController {
	@FXML
	private TextField catGroupName;
	
	@FXML
	private GridPane financialOverview;
	
	public CatGroupEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setCatGroup(CatGroup group) {
		// Set inital text
		catGroupName.setText(group.getName());

		// Bind group's name to the field
		group.nameProperty().bind(catGroupName.textProperty());
		
		int row = 1;
		for(Cat c : group.getCats()) {
			// Add title 
			// Add gridpane w rows according to
			// downpayment, remaining payment, chip donation, donation
			// With name, amount, tickbox which binds to financialitems done property
			boolean anyItems = false;
			int startRow = row;
			
			FinancialItem downPayment = c.getDownPayment();
			if(downPayment != null && downPayment.getAmount() > 0) {
				financialOverview.add(new Label("Anzahlung"), 1, row);
				Label amount = new Label(new Float(downPayment.getAmount()).toString());
				amount.textFillProperty().bind(Bindings.when(downPayment.sentToKHFProperty()).then(Color.GREEN).otherwise(Color.RED));
				financialOverview.add(amount, 2, row);
				CheckBox itemDone = new CheckBox();
				itemDone.selectedProperty().bindBidirectional(downPayment.sentToKHFProperty());
				financialOverview.add(itemDone, 3, row);
				anyItems = true;
				row += 1;
			}
			
			FinancialItem remainingPayment = c.getRemainingPayment();
			if(remainingPayment != null && remainingPayment.getAmount() > 0) {
				financialOverview.add(new Label("Restzahlung"), 1, row);
				Label amount = new Label(new Float(remainingPayment.getAmount()).toString());
				amount.textFillProperty().bind(Bindings.when(remainingPayment.sentToKHFProperty()).then(Color.GREEN).otherwise(Color.RED));
				financialOverview.add(amount, 2, row);
				CheckBox itemDone = new CheckBox();
				itemDone.selectedProperty().bindBidirectional(remainingPayment.sentToKHFProperty());
				financialOverview.add(itemDone, 3, row);
				anyItems = true;
				row += 1;
			}
			
			FinancialItem chipDonation = c.getChipDonation();
			if(chipDonation != null && chipDonation.getAmount() > 0) {
				financialOverview.add(new Label("Chipspende"), 1, row);
				Label amount = new Label(new Float(chipDonation.getAmount()).toString());
				amount.textFillProperty().bind(Bindings.when(chipDonation.sentToKHFProperty()).then(Color.GREEN).otherwise(Color.RED));
				financialOverview.add(amount, 2, row);
				CheckBox itemDone = new CheckBox();
				itemDone.selectedProperty().bindBidirectional(chipDonation.sentToKHFProperty());
				financialOverview.add(itemDone, 3, row);
				anyItems = true;
				row += 1;
			}
			
			FinancialItem donation = c.getDonation();
			if(donation != null && donation.getAmount() > 0) {
				financialOverview.add(new Label("Spende"), 1, row);
				Label amount = new Label(new Float(donation.getAmount()).toString());
				amount.textFillProperty().bind(Bindings.when(donation.sentToKHFProperty()).then(Color.GREEN).otherwise(Color.RED));
				financialOverview.add(amount, 2, row);
				CheckBox itemDone = new CheckBox();
				itemDone.selectedProperty().bindBidirectional(donation.sentToKHFProperty());
				financialOverview.add(itemDone, 3, row);
				anyItems = true;
				row += 1;
			}
			
			if(anyItems) {
				Label catName = new Label(c.getName());
				catName.setFont(Font.font("System", FontWeight.BOLD, 12));
				financialOverview.add(catName, 0, startRow);
			}
		}
	}
}

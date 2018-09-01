A cat cataloging and (hopefully) document filling helper app for foster homes of the **Schweizerische Vereinigung der Katzenhaus-Freunde**.
http://www.katzenhaus-freunde.ch

Half curiosity to learn JavaFX, half pro bono work. Half fun.

# TODO:
* Document the living daylight out of everything (it's high time)
* Ask for confirmation when quitting if data changed
* Bind saving to UI
  * Enable/Disable save button when state changes
  * Ask for confirmation when quitting with unsaved changes
* Bind generation of documents to UI
* Lots&Lots of fields
  * Custom "person" input
* Ask for name when creating new item
* Custom stack trace error dialog instead of just printStackTrace
* Factor menu into its own FXML/Controller if it gets too complex  
* Tests?
  * I think we'll manage without
* Special characters in stored data
* IDs keep changing on load
* icons galore
* Modality and parent for fileChoosers and dialogs
* static state holder feels wonky, maybe move state (and dirty flag) back into main
 
 Thanks to [code.makery.ch](https://code.makery.ch/library/javafx-tutorial/) for getting me up top speed on JavaFX.
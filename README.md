A cat cataloging and (hopefully) document filling helper app for foster homes of the **Schweizerische Vereinigung der Katzenhaus-Freunde**.
http://www.katzenhaus-freunde.ch

Half curiosity to learn JavaFX, half pro bono work. Half fun.

# TODO:
*a lot as you can see*

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
* static state holder feels wonky, maybe move state (and dirty flag) back into main
* custom styling?
* custom classloader / splash screen
* add proper titles to filechoosers
* set proper starting dirs in choosers
* use separate paths for documents and store?
* add default home to store when loading empty
* Progress indicator when rendering docs for multiple cats. even single one?
* Input validation? Much of the data is "suit yourself" anyway
  * Maybe for phone numbers, email & chip nrs
 
 
 Thanks to [code.makery.ch](https://code.makery.ch/library/javafx-tutorial/) for getting me up top speed on JavaFX.
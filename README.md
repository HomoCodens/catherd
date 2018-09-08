A cat cataloging and (hopefully) document filling helper app for foster homes of the **Schweizerische Vereinigung der Katzenhaus-Freunde**.
http://www.katzenhaus-freunde.ch

Half curiosity to learn JavaFX, half pro bono work. Half fun.

# TODO:
*a lot as you can see*

* Document the living daylight out of everything (it's high time)
* Ask for confirmation when quitting with unsaved changes
* Bind generation of documents to UI
* Lots&Lots of fields
  * Custom "person" input
* Ask for name when creating new item
* Custom stack trace error dialog instead of just printStackTrace
* Tests?
  * I think we'll manage without
* Special characters in stored data
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
* lambdaify listenery
  * How prevalent is java8 anyway? I mean, it's 2018
* Small overview in editors?
* use context menu items in main menu
* menu separators?
* use custom file extension to avoid confusion? Sounds a bit eevil though.
* MoneyField exceptions on null value
* doublecheck fvr vs frv
* limit amount of text/lines in textareas
* Aks them to properly label the PDF form fields
* indicate currently open file in title
* width of stuff
* chipImplantedDate is not read in??
* i18n? Nah... xD
* rendering starts even if dirchooser closed
 
 
## Custom controls

If you are working with SceneBuilder, add the controls in the customcontrols package to it before beginning.
 
 Thanks to [code.makery.ch](https://code.makery.ch/library/javafx-tutorial/) for getting me up top speed on JavaFX.
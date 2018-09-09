A cat cataloging and (hopefully) document filling helper app for foster homes of the **Schweizerische Vereinigung der Katzenhaus-Freunde**.
http://www.katzenhaus-freunde.ch

Half curiosity to learn JavaFX, half pro bono work. Half fun.

# TODO:
*a lot as you can see*

## NOW
* use separate paths for documents and store?
	* set proper starting dirs in choosers
* Ask for name when creating new item
* lambdaify listenery
  * How prevalent is java8 anyway? I mean, it's 2018
* chipImplantedDate is not read in??
* Can "Save" even if load failed
* Ask which kind of contract to render

## Later
* Document the living daylight out of everything (it's high time)
* icons galore
* add default home to store when loading empty
* Small overview in editors?
* use context menu items in main menu
* menu separators?
* width of stuff
* content of Datenblätter (vet visits)
* "About" window
* keyboard shortcuts
* Defautl button type on quit confirmation


## Maybe
* Custom stack trace error dialog instead of just printStackTrace
* Tests?
  * I think we'll manage without
* static state holder feels wonky, maybe move state (and dirty flag) back into main
* custom styling?
* custom classloader / splash screen
* Input validation? Much of the data is "suit yourself" anyway
  * Maybe for phone numbers, email & chip nrs
* Aks them to properly label the PDF form fields
* i18n? Nah... xD
* textformatters instead of listeners on moneyfields, textareas

 
 
## Custom controls

If you are working with SceneBuilder, add the controls in the customcontrols package to it before beginning.
 
 Thanks to [code.makery.ch](https://code.makery.ch/library/javafx-tutorial/) for getting me up top speed on JavaFX.
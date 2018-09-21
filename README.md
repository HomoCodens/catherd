A cat cataloging and (hopefully) document filling helper app for foster homes of the **Schweizerische Vereinigung der Katzenhaus-Freunde**.
http://www.katzenhaus-freunde.ch

Half curiosity to learn JavaFX, half pro bono work. Half fun.

# TODO:
*a lot as you can see*

## NOW
* icons galore
* add default home to store when loading empty
* Rethink naming of fields
* Add metadata to created pdfs

## Later
* Make it preety
 * behavior of PersonEditory
 * some padding 
* Custom stack trace error dialog instead of just printStackTrace
 * or at least go through all the trycatch blocks and add meaningful errors (e.g. FileNotFoundException when pdf is open in other program)
* Document the living daylight out of everything (it's high time)
* Small overview in editors?
	* Stats & reports! (can be done w/ other app via json too though)
* use context menu items in main menu
* menu separators?


## Maybe

* Tests?
  * I think we'll manage without
* static state holder feels wonky, maybe move state (and dirty flag) back into main
* custom styling?
* custom classloader / splash screen
* Input validation? Much of the data is "suit yourself" anyway
  * Maybe for phone numbers, email & chip nrs
* Aks them to properly label the PDF form fields
* i18n? Nah... xD

 
 
## Custom controls

If you are working with SceneBuilder, add the controls in the customcontrols package to it before beginning.
 
 Thanks to [code.makery.ch](https://code.makery.ch/library/javafx-tutorial/) for getting me up top speed on JavaFX.
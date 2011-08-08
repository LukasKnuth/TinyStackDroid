## Overview ##
`TinyStackDroid` is a tiny Android-Widget which shows your StackOverflow-statics.

I was playing around with Widgets and wanted to do something with the Internet.
So I used the Stack-Exchange-API to get the profile-data of a specific user.

## Features ##
List of features which are currently implemented:

* Displays the user-infos which are displayed by the original SO-Flair(TM) cards
* Automatically refreshes the infos.
* If no connection is available, it refreshes the infos when it becomes available

## Installation ##
If you have the Android SDK installed and use Eclipse to develop, you can simply
import the cloned repo as a project and build it.

I'll add a Ant buildscript later on.

## ToDo List ##
Things that will and/or should be improved:

* The other Flair(TM) styles
* Set the update-frequency in the `Config`-Acitvity

## License ##
Feel free to do everything you like with it. No special License.
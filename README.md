# Pickture
This is a backup repository for a previous Android homework project of the course ECE 150/251 (UCSB 2016 Winter).

# Specification
Create an Android application that does the following:
```
    A. The first activity displays your name (hardcoded) and a profile photo box. Underneath it should be 
    a button with the label “Change Picture”. Clicking on the button should take you to the second activity.
```
```
    B. The second activity shows an image gallery. Whether you choose to implement it by yourself and fill it 
    with your own images or use an intent is up to you. This allows you to select an image and brings you 
    back to the first activity, displaying the selected image in the profile picture box.
```
```
    C. Although the application sounds simple, try to make sure that your app will gracefully end on every 
    occasion. When we test your app, we will try selecting images iteratively (so that your app will switch 
    between the 1st and 2nd activities iteratively). We will also test whether your app will remember the 
    image last chosen once it’s shut down and brought up again, or between two usage sessions.
```

# Description
The first half contains a Image View, showing the picture chosen.
The second half contains a Button, which will send an intent to start a new Image Gallery Activity to choose picture when clicked, and also a text label showing my name.
Except for these standard function, it has two additional features:
  1. Image chosen will be stored in Internal Storage automatically. No matter what happens, it persists.
    This is achieved by using shared Preferences.
  2. Added a reset button that could clear and reset current choice.
  3. The linear layout's orientation changes with screen rotations.
    This is achieved by overriding the onConfigurationChanged even handler and listening the screen changes event.

# License

    Copyright 2016.

# 501 Assignment 5-2

### Description

3-page app with bottom bar navigation and animated transitions. Uses separate view models for different screens.

### Data Persistence and Navigation

I conducted some experiments to see what would happens when I use the back arrow for navigation.
First I input data on the notes and tasks pages so that I could verify data persistence.

| Before presing "back"      | After pressing "back" |
|----------------------------|-----------------------|
| Notes -> Tasks -> Calendar | Notes                 |
| Tasks -> Notes -> Calendar  | Notes                 |
| Calendar -> Notes -> Tasks | Notes                 |

This shows that no matter where you are, since the start screen is the notes screen and we're using
popUpTo(startDestination) the backstack will always just consist of the current screen and the start
screen.

### AI use

I mainly used AI to create the Notes and Tasks screens. One interesting thing was that it recommended 
using two ViewModels and passing them to the screens in the routes. I hadn't seen that before, 
but it seemed to work well because data was still persisting properly. The only other issue
I had was with animation and the appropriate imports for that, but it seemed to work out after some
trial and error.

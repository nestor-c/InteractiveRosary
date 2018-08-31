Interactive-Rosary

First project.
Project Overview:

This is an app idea given to me by my girlfriend. She wants me to create an interactive rosary that features a way to keep track of which bead the user is on. Another important feature she wants is for the rosary to be 3d and interactive.

Addendum #1: I have a better idea of the app and the features I should implement. As previously stated the app should feature a 3D interactive rosary. The following is a list of all features I want to include:

Next: Pushing this will allow the user to navigate to the next bead and will also present the user with the corresponding prayer for that bead.

Back: Similarly, this button will allow the user to go back one prayer/bead.

Counter: This should be implemented for every "Hail Mary" page. The counter will increase with the number of Hail Mary's prayed. If the user decides to go back one prayer via the back button the counter should keep track of this as well by subtracting from the counter.
Progress

Currently I am looking into making the rosary using opengl.

Update #1 12/30/2017
I am going to try to consistently write to and update the readme file. So far I've been able to get the prayers in a FragmentStatePagerAdapter. As it stands now I have a string array containing each prayer that is in the rosary. Some prayers are repeated multiple times such as the Hail Mary, the most repeated prayer. To avoid having to enter the same prayer in the string array I wanted to manipulate the viewpager so that I can manage the order the pages are viewed in. This would have the benefit of being able to recall a page that was already created. I thought this would lower the amount of storage space needed for the prayers. In the end this proved too tedious of a job and I decided to keep it as is with multiple prayers showing up in the array. Now I am working on getting the 3D rosary on the app. I am working with OpenGL and am familiarizing myself with its pipeline and the different methods used to achieve orthographic projection. I still need to continue my research. Orthographic projection is a subject I have yet to fully comprehend.

Update #2 1/13/18
I understand the use of the orthographic projection, finally! This is a subject that took me a longer time to understand than I care to admit. Having understood the use of orthograpic projection I can finally continue with my learning of OpenGL

Orthographic Projection - This seems to be a way to show perspective in a flat 2D plane as I understand it. To do so we extend

the rays coming from the 3D object and have them be perpendicular to the viewing plane.

Comprehension:

As I continue my reading from "OpenGL ES 2 for Android: A quick start guide" I don't really understand how this will allow

us to account for our phone's aspect ratio just yet or how this will also allow us to go from a virtual coordinate space

back to a normalized one. I'm going to press onwards in hopes that the book will explain this in more detail in a later

portion.

1/13/2018

I understand it now but the concept is still a bit difficult to wrap my head around fully. From what I understand, the viewport or

"screen" will always be a normalized device space(NDS) with the following range -1 <= x,y,z <= 1. Therefore we can't map our

object coordinates directly to the NDS. This has the issue of not taking the device aspect ratio into account. By using a projection

we can "grab" what is in our virtual coordinate space (our 3D world) and project it onto the NDS whilst still compensating

for our device's aspect ratio.

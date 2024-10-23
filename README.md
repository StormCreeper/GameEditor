# Game editor de la Muerta
By Enzo, Lou and Telo.

## How to run
The entry point is in App.java

## Features
- Load and save levels to the disk
- Restart a level (reloads the current level from the disk)
- Switch to game mode or editor mode
- Enable/disable debug mode: debug mode shows hitboxes and other information
- Show help
### Game
- Arrow keys to move around
- S to shoot
- D to pick ammo: press to show the selection box, release to confirm
- The bullets can interact with the map:
  - Water on lava solidifies the block and makes ground
  - Ground on water fills the hole and makes ground
  - Lava on a tree burns it
  - Water on a crack fills the crack and make water
### Map Editor
- The system automatically select the correct tiles to make the frontiers between water/lava and the ground
    Internally, the borders are a set of 16 tiles, that can be placed on top of another. When the map is changed, the editor recomputes which border tiles are shown depending on the neighboring tiles.
- Auto-fill: if enabled, will fill the tiles inside the shape drawn by the user to make a closed filled shape.
- Layer system: ground layer, blocks layer and decorations layer
  - Allows to place tiles on top of other tiles
  - Each layer has a set of allowed tiles
  - In a given layer, the eraser only erases the tile from the selected layer
- Map size options: add column, add row, reset to specific size
- The starting point is unique, so placing it wil delete any other starting tiles
- There can be multiple winning tile

## Code organisation
### UI elements
  The UI elements (in package editor) are JComponents that are used for the interface (especially for the editor): toolbar, tile selection panel...
  
### Game elements
  The game panel is a JPanel, where all game elements are drawn using the Graphics object.  
  The game elements are java classes, that implement one or both of the interfaces GameDrawable and GameUpdatable. GameDrawable is for elements that can be drawn on the Graphics object, and GameUpdatable is for thoses that need to be updated each frame.  
  The Tileset class load an image that contains all tile images in a grid pattern, get the sub images, rescale them and makes them accessible to the rest of the program  
  The Tilemap contains all the tiles in a grid, and draw them in the correct order and place. It also provides the bounding boxes of the 9 tiles surrounding a point.
  The Tile contains the IDs of the images used to draw it: the base material (ground/water/lava), the border tile(s), and the two other layers. It also provides its bounding box in local space.
  The Character handles all the player movement and collisions, and controls the gun.
  The Camera transforms the Graphics to simulate a 2D camera, and have an ease and a screen shake effect

## Extensions
There are several thing we could add to the map editor to make it complete:
- For the moment, the tileset, the layers, and the collision shapes are hardcoded, we could add the ability to load a tileset, and edit its collisions and layers
- We could make it so that the user can modify the interactions between bullets and tiles
- Add a tool to copy an paste a region of the map
- Add support to non-square collisions for tiles

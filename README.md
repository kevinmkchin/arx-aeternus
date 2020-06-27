# rapture

### Scenes
All Scenes have:
- ModelLoader modelLoader
- MainRenderer renderer
- Camera camera
- Light light

MainGameLoop must call *Scene.updateScene()* and *Scene.renderScene()* every frame.
*Scene.closeScene()* must be called when scene is no longer active.

Scenes should have a constructor with the following:
- initScene()
- setCamera()
- setLighting(float x, float y, float z, float r, float g, float b, float ambientLight, boolean isDirectional)

Scenes must implement:
- loadContent()
- updateScene()

To process entities to render, *updateScene()* must call *renderer.processEntity(Entity)* for every entity.
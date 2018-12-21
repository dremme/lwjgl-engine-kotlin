# LWJGL Engine

A LWJGL based graphics engine written in Kotlin for learning und prototyping.

### Prerequisites

Java JDK 9+ needs to be installed on your machine.
An OpenGL 4 capable GPU needs to be installed as well.

Only the OpenGL rendering path is supported as of now. Vulkan is planned for the future.

### Run

The test game can be launched with:

```bash
./gradlew run
```

This will launch `hamburg.remme.lwjgl.game.ParticleGameKt`. It is a basic particle simulated physics game.

# Plague-engine
_If can't choose from a few, choose all_

> At the moment I am in a search for the better engine structure.
> As I've started pushing to `main`, I will keep pushing to `main` for now.
> Some of the ideas were reimagined, but I'll keep README the same for the time being.
> Currently the engine basis is under development. FABRIK is ready, but (I think) I won't use it for now.
> Pretty soon the structure will be ready for further development.

The main goals of the project are:
- Pipeline the development of games I tend to make, but can't finish them because of technical issues with existing engines
- Utilize **Dependency Inversion** in such a way, that any system/component could be remade without breaking the other systems
- Leave space for developing some unplanned mechanics, if needed at some moment
- Allow changing the rules right during a game session; for example, switch camera from Isometric 2D to Spacial First-Person, or switch between turn-based and real-time gameflow
- In the end, allow adding some 30-60 lines of code (aside from graphics and story-lines) to make a new game, without the need to reinvent the wheel / try to shove the existing (but not optimized for a specific goal) code inside a new project

The architecture of the engine inherits from both the DOP (Data-Oriented-Programming) of ECS (Entity-Component-System) and the classical OOP patterns. It is meant to be used via the OOP approach (i.e. while systems define the basic relationships between components (`apply Damage to Health`), the core logic is implemented by the components themselves), but the components' fields will still remain `public`, allowing anyone to work with them directly.

The engine is not meant to proccess thousands of entities (at least not until it is proved to be possible);

The engine is not meant to render performance-heavy 3D graphics, neither complex physics.

Instead, its purpose is to provide the easy way of making 2D/2.5D/basic 3D RPGs, Rogue-likes, Sandboxes, Old-School Shooters, and such; all is needed is to define some new logic and plug it in*.

*_(the name of the engine was originally Plug-It-In, but it was used because nothing else had come to mind; later, while thinking to myself "How bad these ideas sound!", I remembered about **Plug-and-plague**, so the final name was chosen)_.

## Roadmap

- FABRIK for procedural animations
- 3D Spacial Camera
- Chunk system
- Agent-based AI relationships
- Very simple, cell-driven (for environment) / sphere-driven (for characters) collision detection
- Height map for 3D
- TBA

# Introduction

## What is clj-ecs?
Clj-ecs is clojure library for creating components, entities, and systems. This paradigm is often used in games instead of object oriented programming because it allows systems to handle different properties of objects appropriately.

## What is a component?
A component is a property of a larger entity. In essence, a component could be stats, location/position, the graphics component of a sprite, etc.

## What is an entity?
An entity is simply some sort of identifier that is common for all components. It's the thing that identifies what a component is part of. For example, the main character entity id of 123, and its components also have that id to signify that it is part of the main character as well.

## What is a system?
A system handles several components of a certain type and runs an update function on its components that changes based on the specific system. A system contains several "entities" (components that belong to different entities), an update function, and the list of component types it can handle.

# Syntax

## Defining an entity
Let's start with creating an entity. Creating an entity is a matter of calling `(new-entity)`. What `(new-entity)` returns is a map with `:id` as the key, and a random `java.util.UUID` as the value. It's that simple, no joke. Seriously. The source of this function is literally

```
(defn new-entity []
	{:id (java.util.UUID/randomUUID)})
```

## Defining a component
Defining a component is as such:

```
(component test [x y])
```

 `(component)` is a macro which in this case translates `test` into a function that takes `x` and `y` and returns a map `{:x x, :y y}`. In essence,
`(component test [x y])` translates into 

```
(defn test [x y]
	{:x x, :y y})
```

## Defining a system
This is where the real magic starts to happen. Systems are useful because they handle multiple components even though many of these components aren't really a part of the entity. Systems in clj-ecs are atomic maps with three keys: `:components` corresponds to a list of all the typs of components the system can handle, `:update!` is the update function that runs once every 1/60th of a second, and `:entities` contains the actual components that "belong" to an entity. Essentially an entity in a system is a map with the actual component and the id of the entity, e.g. `{:id java.util.UUID, :x 1}`.

Defining a system is as follows:

```
(system collision [x])
```
This defines a system `collision` which contains and handles the x component for all entities. 


# clj-ecs

A simple Entity-Component-System library for Clojure. It's currently a work in progress, and is meant to be light and simple.

## Usage

In order to create a component, simply define it as such:

```
(component position [x y]) 
```

This code transforms into:

```
(defn position [x y]
  {:x x
   :y y})
```

In order to create a system:

```
(system collision [position])
```

This creates a system that handles the position of entities.

For more: [go here.](/tree/master/doc/intro.md)

## License

Copyright © 2014 Thorium Games LLC

Distributed under the LGPL License.

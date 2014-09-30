(ns clj-ecs.core
  (:use [clojure.string :exclude [replace reverse]]))

(defmacro component [name args & body]
  "Defines a new component. Simply a function that returns a map."
  `(defn ~name ~args (hash-map ~@body)))

(defmacro system [sys-name comps]
  "Creates a system for entities and components"
  `(def ~(symbol (str sys-name "-system"))
     (atom {:components ~(into [] (for [comp comps] (keyword comp)))
            :update! ()
            :observers []
            :entities ~(reduce conj (for [comp comps]
                                      {(keyword comp) []}))})))

(defn new-entity []
  {:id (java.util.UUID/randomUUID)})

(defmacro add-component [entity comp]
  "Assocs the component with the entity, where the key is the component name/type"
  `(assoc ~entity ~(keyword (lower-case (first comp))) ~comp))

(defmacro add-component-as [comp-name entity comp]
  "Assocs an already existing component with the entity"
  `(assoc ~entity ~(keyword (lower-case comp-name)) ~comp))

(defn- name->system [name]
  (symbol (str name "-system")))

(defn- add-observer-to* [system observer]
  (swap! system update-in [:observers] conj observer))

(defmacro add-observer-to [system observer]
  "Adds an observer function to the system for events."
  `(add-observer-to* ~(name->system system) ~observer))

(defmacro set-update-fn [system update-fn]
  "Sets the update function in the system."
  `(swap! ~(name->system system) assoc-in [:update!] ~update-fn))

(defmacro update-system [system & args]
  "The function to run when you want to manually update the system."
  `((:update! ~(name->system system)) ~@args))

(defmacro get-all-with-comp [system component]
  "Finds all the individual components of type \"component\" in \"system\""
  `(~(keyword component) (:entities @~(name->system system))))

(defn- add-entity-to* [system entity]
  (doseq [component (:components @system)]
    (swap! system update-in [:entities component] conj (component entity))))

(defmacro add-entity-to [system entity]
  "Adds entity to a system"
  `(add-entity-to* ~(name->system system) ~entity))


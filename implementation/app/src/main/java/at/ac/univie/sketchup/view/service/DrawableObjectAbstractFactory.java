package at.ac.univie.sketchup.view.service;

import at.ac.univie.sketchup.model.drawable.DrawableObject;

public abstract class DrawableObjectAbstractFactory{

    //Calling the DrawableObjectFactory method to create a DrawableObject

    public abstract DrawableObject getDrawableObject(Class c);

}

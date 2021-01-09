package at.ac.univie.sketchup.model;

import java.util.ArrayList;

public class LayerIterator implements Iterator {
    int index=0;
   ArrayList<Layer> layers= new ArrayList<>();
  public LayerIterator(ArrayList<Layer> layers) {
      this.layers=layers;
  }
    @Override
    public boolean hasNext() {

        if(index < layers.size()){
            return true;
        }
        return false;
    }

    @Override
    public Object next() {

        if(this.hasNext()){
            return layers.get(index++);
        }
        return null;
    }
}


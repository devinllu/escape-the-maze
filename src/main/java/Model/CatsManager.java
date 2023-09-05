package Model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

// manages all the three cats
public class CatsManager implements Iterable<Cat>{

    private List<Cat> cats = new ArrayList<>();
    public void add(Cat cat){
        cats.add(cat);
    }

    @Override
    public Iterator<Cat> iterator() {
        return cats.iterator();
    }
}

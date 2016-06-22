package mypackage;

import java.util.ArrayList;

/**
 * Created by root on 02.06.16.
 */
public class FirstFollowSet {
    static class FirstFollowSetElement {
        String t;
        Rule.RuleDescr descr;

        public FirstFollowSetElement(String t, Rule.RuleDescr descr) {
            this.t = t;
            this.descr = descr;
        }

        @Override
        public int hashCode() {
            return t.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof FirstFollowSetElement)) {
                return false;
            }
            FirstFollowSetElement other = (FirstFollowSetElement) obj;
            return t.equals(other.t);
        }
    }

    ArrayList<FirstFollowSetElement> elements = new ArrayList<>();

    boolean merge(FirstFollowSet other, Rule.RuleDescr descr) {
        boolean addEps = false;
        if (other.hasEps) {
            addEps = addEps();
        }
        boolean addEnd = false;
        if (other.hasEnd) {
            addEnd = addEnd();
        }
        return mergeWithoutEps(other, descr) || addEps || addEnd;
    }

    boolean mergeWithoutEps(FirstFollowSet other, Rule.RuleDescr descr) {
        int oldSize = elements.size();
        ArrayList<FirstFollowSetElement> others = other.elements;
        for (FirstFollowSetElement forAdd : others) {
            boolean found = false;
            for (FirstFollowSetElement cur : elements) {
                if (forAdd.t.equals(cur.t)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                elements.add(new FirstFollowSetElement(forAdd.t, descr));
            }
        }
        return oldSize < elements.size();
    }

    boolean add(FirstFollowSetElement forAdd, Rule.RuleDescr descr) {
        boolean found = false;
        for (FirstFollowSetElement cur : elements) {
            if (forAdd.t.equals(cur.t)) {
                found = true;
                break;
            }
        }
        if (!found) {
            elements.add(new FirstFollowSetElement(forAdd.t, descr));
            return true;
        }
        return false;
    }

    boolean hasEps = false;
    boolean addEps() {
        boolean old = hasEps;
        hasEps = true;
        return !old;
    }

    boolean hasEnd = false;
    boolean addEnd() {
        boolean old = hasEnd;
        hasEnd = true;
        return !old;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.auc.core.data.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author thaonv
 */
public class ConcurrentSet<T> implements Set<T> {

    protected Map<T, Object> map;

    // constructor
    public ConcurrentSet() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean contains(Object element) {
        return this.map.keySet().contains(element);
    }

    @Override
    public Iterator<T> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] arr) {
        return this.map.keySet().toArray(arr);
    }

    @Override
    public boolean add(T el) {
        boolean containsObj = map.containsKey(el);
        if (containsObj == false) {
            map.put(el, Boolean.TRUE);
        }
        return !containsObj;
    }

    @Override
    public boolean remove(Object obj) {
        return this.map.keySet().remove(obj);
    }

    @Override
    public boolean containsAll(Collection<?> colls) {
        return map.keySet().containsAll(colls);
    }

    @Override
    public boolean addAll(Collection<? extends T> colls) {
        boolean changed = false;
        for (T item : colls) {
            changed = add(item) || changed;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> colls) {
        return this.map.keySet().retainAll(colls);
    }

    @Override
    public boolean removeAll(Collection<?> colls) {
        return this.map.keySet().removeAll(colls);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

}

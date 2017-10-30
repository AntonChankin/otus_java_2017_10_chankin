package com.antonchankin.otus.hw03;

import java.lang.reflect.Array;
import java.util.*;

public class OtusArrayList<E> implements List<E> {
    private static final int DEFAULT_SIZE = 10;
    private Object[] array;
    private int size = 0;

    public OtusArrayList() {
        array = new Object[DEFAULT_SIZE];
    }

    public OtusArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            array = new Object[initialCapacity];
        } else {
            array = new Object[0];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    @Override
    public boolean contains(Object o) {
        boolean answer = false;
        if (o != null && size > 0) {
            for (int i = 0; i < array.length; i++) {
                if (o.equals(array[i])) {
                    answer = true;
                    break;
                }
            }
        }
        return answer;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, array.length);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(array, array.length, a.getClass());
    }


    @Override
    public boolean add(E e) {
        boolean answer = false;
        if (e != null) {

        }
        return answer;
    }

    @Override
    public boolean remove(Object o) {
        boolean answer = false;

        return answer;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        array = new Object[0];
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}

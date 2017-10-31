package com.antonchankin.otus.hw03;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Custom implementation of array based List. Not thread safe.
 * @param <E> Element of List
 */
public class OtusArrayList<E> implements List<E> {
    private static final int DEFAULT_SIZE = 10;
    private static final float THRESHOLD_DOWN = 0.5F;
    private static final float THRESHOLD_UP = 0.75F;
    private static final int RESIZE_MULTIPLIER = 2;
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
        final Iterator<E> iterator = new Iterator<E>() {
            private int idx = 0;
            @Override
            public boolean hasNext() {
                return size > idx;
            }

            @Override
            public E next() {
                return (E)array[idx++];
            }
        };
        return iterator;
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
            int pos = size + 1;
            updateArraySize(pos);
            array[pos] = e;
        }
        return answer;
    }

    @Override
    public boolean remove(Object o) {
        boolean answer = false;
        if (o != null) {
            int pos = -1;
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])){
                    pos = i;
                    break;
                }
            }
            if (pos > 0) {
                System.arraycopy(array, pos + 1, array, pos, size - pos);
                answer = true;
            }
        }
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

    private boolean updateArraySize(int newSize){
        boolean isChanged = false;
        Object[] newArray = null;
        if (newSize > size && newSize > array.length * THRESHOLD_UP) {
            newArray = new Object[RESIZE_MULTIPLIER * array.length];
            isChanged = true;
        } else if (newSize < size && newSize < array.length * THRESHOLD_DOWN) {
            newArray = new Object[array.length / RESIZE_MULTIPLIER];
            isChanged = true;
        }
        if (isChanged) {
            Arrays.copyOf(array, array.length, newArray.getClass());
            size = newSize;
        }
        return isChanged;
    }

    private class OtusListIterator<E> implements ListIterator<E> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }
}

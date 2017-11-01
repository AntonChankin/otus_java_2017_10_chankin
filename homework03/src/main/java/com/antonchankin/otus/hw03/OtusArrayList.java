package com.antonchankin.otus.hw03;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;

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

    private OtusArrayList(E[] initArray){
        if (Objects.isNull(initArray)){
            throw new IllegalArgumentException("Initial array cannot be null");
        }
        array = initArray;
        size = array.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
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
        boolean result = false;
        if (e != null) {
            int pos = size;
            updateArraySize(pos + 1);
            array[pos] = e;
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = false;
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
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = c != null;
        if (result) {
            for (Object o : c) {
                if(!contains(o)){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = c != null;
        if (result) {
            addAll(size, c);
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean result = c != null;
        if (result) {
            Object[] newArray = new Object[size + c.size()];
            Object[] collection = c.toArray();
            System.arraycopy(array, 0 , newArray, 0, index);
            System.arraycopy(collection, 0 , newArray, index, collection.length);
            System.arraycopy(array, index+1 , newArray, collection.length, size - index);
            array = newArray;
            size = newArray.length;
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object element : c) {
            result = remove(element) || result;
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        Object[] newArray = new Object[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])){
                newArray[index++] = array[i];
            }
            result = true;
        }
        array = newArray;
        size = index;
        return result;
    }

    @Override
    public void clear() {
        size = 0;
        array = new Object[0];
    }

    @Override
    public E get(int index) {
        return (E)array[index];
    }

    @Override
    public E set(int index, E element) {
        E prev = (E)array[index];
        array[index] = element;
        return prev;
    }

    @Override
    public void add(int index, E element) {
        updateArraySize(size + 1);
        System.arraycopy(array, index , array, index + 1, size - index);
        array[index] = element;
    }

    @Override
    public E remove(int index) {
        E prev = (E)array[index];
        updateArraySize(size - 1);
        System.arraycopy(array, index + 1, array, index, size - index);
        return prev;
    }

    @Override
    public int indexOf(Object o) {
        int pos = -1;
        if (o != null && size > 0) {
            for (int i = 0; i < array.length; i++) {
                if (o.equals(array[i])) {
                    pos = i;
                    break;
                }
            }
        }
        return pos;
    }

    @Override
    public int lastIndexOf(Object o) {
        int pos = -1;
        if (o != null && size > 0) {
            for (int i = 0; i < array.length; i++) {
                if (o.equals(array[i])) {
                    pos = i;
                }
            }
        }
        return pos;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new OtusListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new OtusListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList(this,fromIndex, toIndex);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        cutToSize();
        Arrays.sort((E[])array, c);
    }

    private void cutToSize(){
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size );
        array = newArray;
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
            System.arraycopy(array, 0, newArray, 0, size );
            array = newArray;
        }
        size = newSize;
        return isChanged;
    }

    private class OtusListIterator implements ListIterator<E> {
        private int idx = 0;

        public OtusListIterator(int start) {
            this.idx = start;
        }

        public OtusListIterator() {
        }

        @Override
        public boolean hasNext() {
            return idx < size;
        }

        @Override
        public E next() {
            return (E)array[idx++];
        }

        @Override
        public boolean hasPrevious() {
            return idx > 0;
        }

        @Override
        public E previous() {
            return (E)array[idx--];
        }

        @Override
        public int nextIndex() {
            return idx + 1;
        }

        @Override
        public int previousIndex() {
            return idx - 1;
        }

        @Override
        public void remove() {
            OtusArrayList.this.remove(idx);
        }

        @Override
        public void set(E e) {
            OtusArrayList.this.set(idx, e);
        }

        @Override
        public void add(E e) {
            OtusArrayList.this.add(idx,e);
        }
    }

    private class SubList extends AbstractList<E> implements RandomAccess {
        private final OtusArrayList<E> parent;
        private final int start;
        private final int finish;

        public SubList(OtusArrayList<E> parent, int start, int finish) {
            this.parent = parent;
            this.start = start;
            this.finish = finish;
        }

        @Override
        public E get(int index) {
            return parent.get(start + index);
        }

        @Override
        public int size() {
            return finish - start;
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            for (int i = start; i < finish + 1; i++) {
                array[i] = operator.apply((E)array[i]);
            }
        }

        @Override
        public void sort(Comparator<? super E> c) {
            E[] sorting = (E[])new Object[finish - start];
            System.arraycopy(array, start, sorting, 0, finish - start);
            Arrays.sort(sorting, c);
            System.arraycopy(sorting, 0, array, start, finish - start);
        }
    }
}

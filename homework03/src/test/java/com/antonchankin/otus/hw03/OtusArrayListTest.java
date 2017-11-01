package com.antonchankin.otus.hw03;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtusArrayListTest extends TestCase {
    public void testSize() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(3);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        assertEquals(3, list.size());
    }

    public void testIsEmpty() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(5);
        assertTrue(list.isEmpty());
    }

    public void testContains() throws Exception {
    }

    public void testIterator() throws Exception {
    }

    public void testToArray() throws Exception {
    }

    public void testToArray1() throws Exception {
    }

    public void testAdd() throws Exception {
    }

    public void testRemove() throws Exception {
    }

    public void testContainsAll() throws Exception {
    }

    public void testAddAll() throws Exception {
    }

    public void testAddAll1() throws Exception {
    }

    public void testRemoveAll() throws Exception {
    }

    public void testRetainAll() throws Exception {
    }

    public void testClear() throws Exception {
    }

    public void testGet() throws Exception {
    }

    public void testSet() throws Exception {
    }

    public void testAdd1() throws Exception {
    }

    public void testRemove1() throws Exception {
    }

    public void testIndexOf() throws Exception {
    }

    public void testLastIndexOf() throws Exception {
    }

    public void testListIterator() throws Exception {
    }

    public void testListIterator1() throws Exception {
    }

    public void testSubList() throws Exception {
    }

    public void testSort() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(3);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        Collections.sort(list, (o1, o2) -> -o1.compareToIgnoreCase(o2));
        assertEquals("beta",list.get(1));
    }

    public void testCopy() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(3);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        List<String> target = new ArrayList<>(3);
        target.add("dummy");
        target.add("dummy");
        target.add("dummy");
        Collections.copy(target, list);
        assertEquals(list.get(0),target.get(0));
        assertEquals(list.get(1),target.get(1));
        assertEquals(list.get(2),target.get(2));
    }
}
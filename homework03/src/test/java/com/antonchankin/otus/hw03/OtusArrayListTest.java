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
        OtusArrayList<String> list = new OtusArrayList<>(3);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        assertTrue(list.contains("gamma"));
    }

    public void testAdd() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(2);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        assertEquals("beta", list.get(2));
    }

    public void testRemove() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(2);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        assertTrue(list.remove("gamma"));
        assertEquals("alpha", list.get(0));
        assertEquals("beta", list.get(1));
    }

    public void testCollectionsAddAll() throws Exception {
        OtusArrayList<String> list = new OtusArrayList<>(3);
        list.add("alpha");
        list.add("gamma");
        list.add("beta");
        Collections.addAll(list, "delta","epsilon");
        assertEquals("alpha", list.get(0));
        assertEquals("gamma", list.get(1));
        assertEquals("beta", list.get(2));
        assertEquals("delta", list.get(3));
        assertEquals("epsilon", list.get(4));
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
package com.mycode.compiler.automate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyTransformTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        String[][] nfa = {
                {"a1"},
                {"a1", "b2", "c4", "c3", "d1"},
                {"e3", "f4"},
                {},
                {}
        };

        NFA n = NFA.build(nfa);
        assertEquals(1, (int) n.getMoveTable(0).get("a").get(0));
        assertEquals(1, (int) n.getMoveTable(1).get("a").get(0));
        assertEquals(2, (int) n.getMoveTable(1).get("b").get(0));
        assertEquals(4, (int) n.getMoveTable(1).get("c").get(0));
        assertEquals(3, (int) n.getMoveTable(1).get("c").get(1));
        assertEquals(1, (int) n.getMoveTable(1).get("d").get(0));
        assertEquals(3, (int) n.getMoveTable(2).get("e").get(0));
        assertEquals(4, (int) n.getMoveTable(2).get("f").get(0));
    }
}
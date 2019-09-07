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
                {"g4"},
                {}
        };

        NFA n = NFA.build(nfa);
        assertTrue(n.moveToNextState(0, "a").contains(1));
        assertTrue(n.moveToNextState(1, "a").contains(1));
        assertTrue(n.moveToNextState(1, "b").contains(2));
        assertTrue(n.moveToNextState(1, "c").contains(4));
        assertTrue(n.moveToNextState(1, "c").contains(3));
        assertTrue(n.moveToNextState(1, "d").contains(1));
        assertTrue(n.moveToNextState(2, "e").contains(3));
        assertTrue(n.moveToNextState(2, "f").contains(4));
        assertTrue(n.moveToNextState(4, "f").contains(5));
        assertTrue(n.moveToNextState(4, "g").contains(5));
    }

    @Test
    public void testNFAOrNFA() {
        String[][] nfa1 = {
                {"a1"},
                {"a0", "c1", "c2"},
                {"e0", "f2"},
        };

        String[][] nfa2 = {
                {"a2"},
                {"b0", "c1"},
                {},
        };

        NFA n1 = NFA.build(nfa1);
        NFA n2 = NFA.build(nfa2);

        NFA nfa = NFATransform.NFAOrNFA(n1, n2);
        assertTrue(nfa.moveToNextState(0, "ε").contains(1));
        assertTrue(nfa.moveToNextState(0, "ε").contains(4));
        assertTrue(nfa.moveToNextState(1, "a").contains(2));
        assertTrue(nfa.moveToNextState(2, "c").contains(2));
        assertTrue(nfa.moveToNextState(5, "b").contains(4));
        assertTrue(nfa.moveToNextState(6, "ε").contains(7));
        assertTrue(nfa.moveToNextState(7, "r").contains(8));
        assertTrue(nfa.moveToNextState(7, "f").contains(8));
    }

    @Test
    public void testNFAUnionNFA() {
        String[][] nfa1 = {
                {"a1"},
                {"a0", "c1", "c2"},
                {"e0", "f2"},
                {}
        };

        String[][] nfa2 = {
                {"a2"},
                {"b0", "c1"},
                {},
        };

        String[][] nfas = {
                {"a1"},
                {"a0", "c1", "c2"},
                {"e0", "f2"},
                {"a5"},
                {"b3", "c4"},
                {}
        };

        NFA n1 = NFA.build(nfa1);
        NFA n2 = NFA.build(nfa2);

        NFA nfa = NFATransform.NFAUnionNFA(n1, n2);
        assertTrue(nfa.moveToNextState(0, "a").contains(1));
        assertTrue(nfa.moveToNextState(1, "a").contains(0));
        assertTrue(nfa.moveToNextState(2, "e").contains(0));
        assertTrue(nfa.moveToNextState(3, "a").contains(5));
        assertTrue(nfa.moveToNextState(5, "f").contains(6));
        assertTrue(nfa.moveToNextState(5, "d").contains(6));
    }

    @Test
    public void testNFAStar() {
        String[][] nfa1 = {
                {"a1"},
                {"a0", "c1", "c2"},
                {"e0", "f2"},
                {}
        };

        String[][] result = {
                {"ε1", "ε5"},
                {"a2"},
                {"a1", "c2", "c3"},
                {"e1", "f3"},
                {"ε5", "ε1"},
                {"*6"}
        };

        NFA n1 = NFA.build(nfa1);

        NFA nfa = NFATransform.NFAStar(n1);
        assertTrue(nfa.moveToNextState(0, "ε").contains(1));
        assertTrue(nfa.moveToNextState(0, "ε").contains(5));
        assertTrue(nfa.moveToNextState(2, "a").contains(1));
        assertTrue(nfa.moveToNextState(2, "c").contains(2));
        assertTrue(nfa.moveToNextState(3, "e").contains(1));
        assertTrue(nfa.moveToNextState(4, "ε").contains(5));
    }
}
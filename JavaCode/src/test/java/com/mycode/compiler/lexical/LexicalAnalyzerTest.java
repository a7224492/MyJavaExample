package com.mycode.compiler.lexical;

import com.mycode.compiler.lexical.statemachine.Defines.TokenType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexicalAnalyzerTest
{
	private LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

	@Test
	public void parseToken() throws IOException {
		String test = "aabnm21312r12q";
		lexicalAnalyzer.init(new ByteArrayInputStream(test.getBytes()));

		Token token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.ID, token.getType());
		assertEquals(test, token.buildName());
	}

	@Test
	public void parseToken2() throws IOException {
		String test = "int x = 100";
		lexicalAnalyzer.init(new ByteArrayInputStream(test.getBytes()));
		Token token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.ID, token.getType());
		assertEquals("int", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.ID, token.getType());
		assertEquals("x", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.RELOP, token.getType());
		assertEquals("=", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.DIGIT, token.getType());
		assertEquals("100", token.buildName());
	}

	@Test
	public void parseToken3() throws IOException {
		String test = "if x > 10 x = 15 else x = 5";
		lexicalAnalyzer.init(new ByteArrayInputStream(test.getBytes()));
		Token token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.IF, token.getType());
		assertEquals("if", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.ID, token.getType());
		assertEquals("x", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.RELOP, token.getType());
		assertEquals(">", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.DIGIT, token.getType());
		assertEquals("10", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.ID, token.getType());
		assertEquals("x", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.RELOP, token.getType());
		assertEquals("=", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.DIGIT, token.getType());
		assertEquals("15", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.WHITE, token.getType());
		assertEquals(" ", token.buildName());
		token = lexicalAnalyzer.parseToken();
		assertEquals(TokenType.ELSE, token.getType());
		assertEquals("else", token.buildName());
	}
}
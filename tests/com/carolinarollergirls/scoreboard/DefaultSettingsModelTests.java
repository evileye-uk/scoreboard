package com.carolinarollergirls.scoreboard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.carolinarollergirls.scoreboard.defaults.*;
import com.carolinarollergirls.scoreboard.event.*;
import com.carolinarollergirls.scoreboard.model.*;

public class DefaultSettingsModelTests {

	private DefaultScoreBoardModel sbModelMock;
	private Ruleset ruleMock;
	private DefaultScoreBoardEventProvider listenMock;
	private DefaultSettingsModel settingsModel;
	
	@Before
	public void setup() {
		sbModelMock = Mockito.mock(DefaultScoreBoardModel.class);
		ruleMock = Mockito.mock(Ruleset.class);
		listenMock = Mockito.mock(DefaultScoreBoardEventProvider.class);

		Mockito
		.when(sbModelMock._getRuleset())
		.thenReturn(ruleMock);
		
		settingsModel = new DefaultSettingsModel(sbModelMock, listenMock);
	}
	
	@Test
	public void apply_unmapped_rule() {
		settingsModel.applyRule("Example", "ABC");
		
		assertSame("ABC", settingsModel.get("Example"));
	}
	
	@Test
	public void apply_mapped_rule() {
		settingsModel.addRuleMapping("Example", new String[] {"Example.Team1","Example.Team2"});
		settingsModel.applyRule("Example", "ABC");
		
		assertSame("ABC", settingsModel.get("Example.Team1"));
		assertSame("ABC", settingsModel.get("Example.Team2"));
		assertNull(settingsModel.get("Example"));
	}
	
	@Test
	public void reset_rules() {
		settingsModel.addRuleMapping("Example", new String[] {"Example.Team1","Example.Team2"});
		settingsModel.applyRule("Example", "ABC");
		settingsModel.applyRule("Example2", "BBB");
		
		settingsModel.reset();
		
		assertNull(settingsModel.get("Example.Team1"));
		assertNull(settingsModel.get("Example"));
	}

}

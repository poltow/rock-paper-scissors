package com.rps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.rps.controller.ClientControllerTest;
import com.rps.controller.GameControllerTest;
import com.rps.exception.GameExceptionHandlerTest;
import com.rps.model.game.GameFactoryTest;
import com.rps.model.game.GameTest;
import com.rps.model.game.PlayRulesTest;
import com.rps.model.game.RoundTest;
import com.rps.model.player.PlayerTest;
import com.rps.service.GameServiceTest;
import com.rps.service.UsersHistoryServiceTest;
import com.rps.view.TotalsTest;

@RunWith(Suite.class)
@SuiteClasses({ ClientControllerTest.class, //
		GameControllerTest.class, //
		GameExceptionHandlerTest.class, //
		GameFactoryTest.class, //
		GameTest.class, //
		PlayRulesTest.class, //
		RoundTest.class, //
		PlayerTest.class, //
		GameServiceTest.class, //
		UsersHistoryServiceTest.class, //
		TotalsTest.class//
})
public class AllTests {

}

package com.ciklum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ciklum.controller.ClientControllerTest;
import com.ciklum.controller.GameControllerTest;
import com.ciklum.exception.GameExceptionHandlerTest;
import com.ciklum.model.game.GameFactoryTest;
import com.ciklum.model.game.GameTest;
import com.ciklum.model.game.PlayRulesTest;
import com.ciklum.model.game.RoundTest;
import com.ciklum.model.player.PlayerTest;
import com.ciklum.model.user.UsersHistoryTest;
import com.ciklum.service.GameServiceTest;
import com.ciklum.service.UsersHistoryServiceTest;
import com.ciklum.view.TotalsTest;

@RunWith(Suite.class)
@SuiteClasses({ ClientControllerTest.class, //
		GameControllerTest.class, //
		GameExceptionHandlerTest.class, //
		GameFactoryTest.class, //
		GameTest.class, //
		PlayRulesTest.class, //
		RoundTest.class, //
		PlayerTest.class, //
		UsersHistoryTest.class, //
		GameServiceTest.class, //
		UsersHistoryServiceTest.class, //
		TotalsTest.class//
})
public class AllTests {

}

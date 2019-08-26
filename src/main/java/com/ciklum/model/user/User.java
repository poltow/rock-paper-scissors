package com.ciklum.model.user;

import com.ciklum.model.player.Strategy;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

	private String name;
	private Strategy strategy;

}
